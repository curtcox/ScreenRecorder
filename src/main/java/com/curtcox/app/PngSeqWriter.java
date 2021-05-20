package com.curtcox.app;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.ByteChannel;
import java.util.zip.CRC32;

public final class PngSeqWriter
        implements Closeable
{

    private int frameCount = 0;
    private int sequenceNumber;
    private boolean closed = false;

    private final Encoder encoder;
    private final int max;
    private final ByteChannel out;
    private static final int fpsNum = 1;
    private static final int fpsDen = 10;

    public PngSeqWriter(File f, Encoder encoder, int max) throws FileNotFoundException {
        this.encoder = encoder;
        this.max = max;
        out = new RandomAccessFile(f, "rw").getChannel();
    }

    private void ensureOpen() throws IOException {
        if (closed) {
            throw new IOException("Stream closed");
        }
    }

    void writeImage(BufferedImage img) throws IOException {
        ensureOpen();
        writeImage(img, fpsNum, fpsDen);
    }

    //http://www.w3.org/TR/PNG/#11IHDR
    private ByteBuffer makeIHDRChunk(Dimension d, byte numPlanes, byte bitsPerPlane) {
        ByteBuffer bb = ByteBuffer.allocate(Consts.IHDR_TOTAL_LEN);
        bb.putInt(Consts.IHDR_DATA_LEN);
        bb.putInt(Consts.IHDR_SIG);
        bb.putInt(d.width);
        bb.putInt(d.height);
        bb.put(bitsPerPlane);
        bb.put(type(numPlanes));
        bb.put(Consts.ZERO); //compression
        bb.put(Consts.ZERO); //filter
        bb.put(Consts.ZERO); //interlace

        addChunkCRC(bb);

        bb.flip();

        return bb;
    }

    private byte type(byte numPlanes) {
        byte type = 0;
        switch (numPlanes) {      //rgb = 0x2, alpha = 0x4
            case 4:
                type |= 0x4;//falls
            case 3:
                type |= 0x2;
                break;
            case 2:
                type |= 0x4;
            default:
                break;
        }
        return type;
    }

    private int crc(byte[] buf) {
        return crc(buf, 0, buf.length);
    }

    private int crc(byte[] buf, int off, int len) {
        CRC32 crc = new CRC32();
        crc.update(buf, off, len);
        return (int) crc.getValue();
    }

    private ByteBuffer makeFCTL(Rectangle r, int fpsNum, int fpsDen, boolean succ) {
        ByteBuffer bb = ByteBuffer.allocate(Consts.fcTL_TOTAL_LEN);

        bb.putInt(Consts.fcTL_DATA_LEN);
        bb.putInt(Consts.fcTL_SIG);

        byte one = 0x1;

        bb.putInt(sequenceNumber++);
        bb.putInt(r.width);
        bb.putInt(r.height);
        bb.putInt(r.x);
        bb.putInt(r.y);
        bb.putShort((short) fpsNum);
        bb.putShort((short) fpsDen);
        bb.put(Consts.ZERO);    	        //dispose 1:clear, 0: do nothing, 2: revert
        bb.put(succ ? one : Consts.ZERO);	//blend   1:blend, 0: source

        addChunkCRC(bb);

        bb.flip();

        return bb;
    }

    private ByteBuffer makeDAT(int sig, ByteBuffer buffer) {
        ByteBuffer compressed = ByteBufferCompressor.compress(buffer);

        boolean needSeqNum = sig == Consts.fdAT_SIG;

        int size = compressed.remaining();

        if (needSeqNum)
            size +=4;

        ByteBuffer bb = ByteBuffer.allocate(size + Consts.CHUNK_DELTA);

        bb.putInt(size);
        bb.putInt(sig);
        if (needSeqNum) {
            bb.putInt(sequenceNumber++);
        }
        bb.put(compressed);

        addChunkCRC(bb);

        bb.flip();
        return bb;
    }

    private void addChunkCRC(ByteBuffer chunkBuffer) {
        if (chunkBuffer.remaining() != 4)			//CRC32 size 4
            throw new IllegalArgumentException();

        int size = chunkBuffer.position() - 4;

        if (size <= 0)
            throw new IllegalArgumentException();

        chunkBuffer.position(4);			 //size not covered by CRC
        byte[] bytes = new byte[size];     // CRC covers only this
        chunkBuffer.get(bytes);
        chunkBuffer.putInt(crc(bytes));
    }

    private ByteBuffer getPixelBytes(BufferedImage image) {
        return new BufferedImageSerializer(image,encoder).encode();
    }

    static Dimension dim(BufferedImage image) {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    private void writeImage(BufferedImage img, int fpsNum, int fpsDen) throws IOException {
        ensureOpen();

        if (frameCount == 0) {
            writeImageHeader(img);
        }

        out.write(makeFCTL(rectangle(img), fpsNum, fpsDen, frameCount != 0));
        out.write(makeDAT(frameCount == 0 ? Consts.IDAT_SIG : Consts.fdAT_SIG, getPixelBytes(img)));
        frameCount++;
    }

    private Rectangle rectangle(BufferedImage img) {
        Dimension dim = dim(img);
        return new Rectangle(dim);
    }

    private void writeImageHeader(BufferedImage value) throws IOException {
        out.write(ByteBuffer.wrap(Consts.getPNGSIGArr()));
        byte bitsPerPlane = 8;
        out.write(makeIHDRChunk(rectangle(value).getSize(), numPlanes(value), bitsPerPlane));

        out.write(make_acTLChunk(max, 0));
    }

    public void close() throws IOException {
        //IEND
        out.write(ByteBuffer.wrap(Consts.getIENDArr()));

        closed = true;
        frameCount = 0;
        out.close();
    }

    private ByteBuffer make_acTLChunk(int frameCount, int loopCount) {
        ByteBuffer bb = ByteBuffer.wrap(Consts.getacTLArr());
        bb.position(8);
        bb.putInt(frameCount);
        bb.putInt(loopCount);
        addChunkCRC(bb);
        bb.flip();
        return bb;
    }

    private byte numPlanes(BufferedImage value) {
        return (byte) value.getRaster().getNumBands();
    }

}
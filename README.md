# ScreenRecorder

This app is for recording a series of screenshots to disk.
Right now that means to an animated [PNG](https://www.w3.org/TR/PNG/) file or to the custom format defined
by SimpleImageSequenceWriter. 

The custom format uses a sequence of xor(b).rgb().trim() and is something like 5 times as efficient as the 
animated PNG in terms of space. See the commits for an indicator of algorithm variations that have been tried.

The comparison to animated PNG is only for those generated using the code in this repo.
The PNG spec contains all sorts of optional techniques that could potentially reduce file sizes.
I don't know how much effort they are to implement or how much impact they have.

## Goals

The ultimate goal of this project is to produce a toolchain allowing people to search their past activities.
This consists of several related steps. The goals are to eventually implement all of the parts and provide
mechanisms for interoperation so that other tools can also be used to perform the same tasks.

- Capture - record screen contents
- Rules - a mechanism of recording some times and not others without always needing to manually start and stop 
- Archive - save the captured information for a long time
- Delete - delete any confidential information (passwords, etc..) that were accidently recorded
- Annotate - optionally markup the images, make collections, etc...
- Extract - get searchable text and images out of the archives
- Index - index the extracted information
- Search - actively query the index
- Suggest - use the currently captured screen and archived information to suggest actions
- Share - actively send others bits of your archive or let them access part of your archive for searches and suggestions

The intent is to provide an open solution that can interoperate with things like 
- [PiKVM](https://pikvm.org/) [TinyPilot](https://tinypilotkvm.com/)
- Cloud storage
- Lucene, Elasticsearch
- Alfred, [searchcode](https://github.com/boyter/searchcode-server)
- [Tesseract](https://github.com/tesseract-ocr/tesseract/blob/master/doc/tesseract.1.asc) [VNRecognizeTextRequest](https://developer.apple.com/documentation/vision/recognizing_text_in_images)
- others

## Security

The elephant in the room is security. A searchable trove of your data is obviously something you want to guard closely.
Right now, I'm focused on making this project useful. If you have security suggestions, please file an issue.

## MVP
[This directory](MVP/README.md) has some hastily written shell scripts that you can
use on a Mac.

## See Also
This code is ultimately a fork of the PNG writer, although you won't see much evidence of that at this point.
See [here](https://github.com/curtcox/apng-writer/tree/delete_unneeded_stuff) for where it came from.
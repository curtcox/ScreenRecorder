# ScreenRecorder

This app is for recording a series of screenshots to disk.
Right now that means to an animated [PNG](https://www.w3.org/TR/PNG/) file or to the custom format defined
by SimpleImageSequenceWriter. 

The custom format uses a sequence of xor(b).rgb().trim() and is something like 5 times as efficient as the 
animated PNG in terms of space. See the commits for an indicator of algorithm variations that have been tried.

This code is ultimately a fork of the PNG writer.
See [here](https://github.com/curtcox/apng-writer/tree/delete_unneeded_stuff) for where it came from.
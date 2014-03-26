#MIDI files

I do Java development for a living, so I think it’s appropriate to use Java in this project.

Since I have never worked with sound production, I spent last week researching for an appropriate way of generating sound from scratch. 

There are two ways to work with audio in Java: sampled audio and MIDI.

Sampled audio is used to record and play sound from many sources like a microphone. It uses a stream of bytes and a predetermined format to translate that stream in sound. That format is used by audio software to know how to interpret the content of the stream or a file. 

MIDI is a standard used to imitate the sound of music instruments. It relays on a stored instrument data bank, and the pitch and length of the sound is later defined by the user or developer.

I wanted to use the sampled audio for this project, since MIDI tends to sound a little artificial; also MIDI relays on the databank that might or might not be included in the installation of Java’s Runtime Environment (JRE). Unfortunately, sampled audio is more complex and harder to create from scratch. 
I worked on some examples to generate sampled audio and I figured out that I wasn’t going to finish before the deadline.

So I will use MIDI to generate the audio for the song and if later I’ll try to adapt sampled sound.

Doing my research I found the beautifully constructed library of [JFugue] (http://www.jfugue.org/) which allows to easily compose MIDI music files from text.

I have created a Java project using the framework Maven and a basic handler of JFugue methods. 

![JFugue](http://www.jfugue.org/jfugue.gif)



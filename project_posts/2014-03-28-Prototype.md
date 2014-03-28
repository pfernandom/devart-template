#The prototype is ready!

So the prototype of the project is ready. It's far from being completed though. It lacks a lot of functionality that I wished to add from the start.
The result is a simple song, with no big changes in its structure, and its duration increases as the user adds more data.

But I know that this will be an ongoing project, and as the time goes by, I'll add more features.

![First interface](../project_images/screenshot1.jpg?raw=true "First Interface")

The user interface is not pretty at all. But I created it in a couple of hours. There will be probably a few bugs around there, so please send me an email if you feel so.

In my last post I defined two things missing: the duration of the notes and the rests. What I figured out is that, if the duration of the notes vary but they have a metric, the rests are tacitly created.
In order to define the duration of one note, I once again thought of a real piano. If you play one note close to the past note played, the probability of having a small rest between them increases. So I did the same with the bytes; if the next byte was close to the past one, the duration will decrease. If there is a big jump between the values, the duration is of one full time.

You can find the compile package in [here](../project_code/440hz/target/440hz-0.0.1-SNAPSHOT-jar-with-dependencies.zip).

Just unzip it and make sure you have Java installed in your machine before running it (who doesn't?).

##What is next

Now I have to upgrade the functionality: 

-I want to add different rhythms and music genres, as well as more customization in the selection of instruments based on the user's input.
-will modify the way the sets of notes are created, to have more concentrated parts in the song, with silences and/or other instruments playing between those parts.
-Improve the user interface. Maybe create some images with textures for the application.
-Add complexity to the way trends affect the song. If I can find a way to relate the terms in the trends with moods, I could select a better rhythm or tempo.


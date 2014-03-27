#More progress and the Unit Scale of Feelings

At this point, the application translating bytes to music notes. The process in this moment is pretty simple, at least the first part. The information provided by the user is converted to bytes using native functions like getBytes() and added to an array (it’s actually a stream). The user’s data considered is name, age, a set of images provided by the user. 
This this information we add the “Hot Topics” from [Google Trends](http://www.google.com/trends/hottrends/atom/hourly). In this way, we end up with an array of bytes that was created with the user’s personal information and a part of his/her social context.
Oh, and the user information (name and age) is encrypted using a one-way algorithm to produce a non-reversible hash (SHA-512) to guarantee that no personal information is being stored in “plain text”. 

The method that does the conversion is getProfile(), from UserProfile.java.

```
public byte[] getProfile() throws IOException, NoSuchAlgorithmException {
		if (profile == null) {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			
			outputStream.write(Util.getHash("SHA-512", name.getBytes()));
			outputStream.write(Util.getHash("SHA-512", new byte[]{(byte) age}));
			
	
			if (words != null) {
				for (String word : words) {
					outputStream.write(word.getBytes());
				}
			}
			if (files != null) {
				for (File file : files) {
					ByteArrayOutputStream strm = new ByteArrayOutputStream();
					if (file.canRead()) {
						FileInputStream fis = new FileInputStream(file);
						byte[] dataBytes = new byte[1024];

						int nread = 0;
						while ((nread = fis.read(dataBytes)) != -1) {
							strm.write(dataBytes, 0, nread);
						}
						fis.close();
						outputStream.write( strm.toByteArray());
					}
					strm.close();
				}
			}

			profile = outputStream.toByteArray();
			outputStream.close();
		}
		return profile;
	}
```
After that, we have to convert the bytes to musical notes. And that is the core part.
For this prototype, I will stick to just one genre and the same instruments (piano, b ass and percussions). I wrote my code in way that it would allow to include more genres and different instruments.
But I would not be able to code that part of the functionality and test it before tomorrow (deadline!).
So, based in the user’s bytes (the user data converted to bytes) we do have to choose at least three things in order to create something worth of being called song: the notes, the duration of the notes, and the rests. And the metric of the song must be taken into consideration.
Let’s see how to select the note.

##The Unit Scale of Feelings

Let’s imagine that we have a piano in front of us and only a few keys are playable, since they are part of the scale. We have to fit our thoughts and feelings in those key.
We could say that one single unit of our feelings (let’s call it Sad#1) will go to a defined key or keys (let’s say Dmin). In that context, our level of sadness will be composed of a defined quantity of Sad#1 units. But we are not monochromatic beings, so we have Sad#1 to Sad#15, depending on how sad we feel. And we don’t have only sadness but maybe a mix with anger (Angry#1-10) or even happiness (Happy#1-20) in different values like Sad#3Angry#4 (which would not simply be mix Sad#3 + Angry#4 but more something that can’t be separated, a compound).  In this way we categorize the Unit Scale of Feelings.
We have to fit that feeling scale into the keys. So we take a delimited set of feelings and associate it to a note. For example, Sad#1 to Sad#10 will go to D and Sad#11 to Sad#3Angry#4 will go to D#. This will allow us to have a way to convert unit feelings to notes.

Once we want to create the song, we will have a set of feeling units that can be measured using the Unit Scale of Feelings, and then we use the conversion of unit feelings to notes. 

Obviously a C or a G can represent sadness as well as a Dmin depending on the context, but that means that every time you play a note you reprocess all your bytes to decide the following note. It’s something more human, but more complex and we will have to leave that for the next version.

Now, instead of using feelings and a Unit Scale of Feelings, we use bytes. Bytes, like feelings, have a range of values (-128 to 127). We have the user bytes and we need to fit them to keys in the scale. Let’s take a scale composed of 12 notes. We do the same that what we did with the feelings: each byte will be a note, and the selected note depends of the value of the value and its position in the scale. 

![Conversion Table](../project_images/table1.jpg?raw=true "Conversion table")

For this example, if the byte to convert to a note has the value of **-75** and the selected scale is the **Cmaj**, then that byte would be converted to **E5**.

I just need to deduce a way to calculate the duration of the notes and the number of the rests, add a nice graphic interface and I will finish.
Yeah, just that.

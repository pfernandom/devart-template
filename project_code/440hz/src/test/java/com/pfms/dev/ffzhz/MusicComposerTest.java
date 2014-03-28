package com.pfms.dev.ffzhz;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.jfugue.MusicStringParser;
import org.jfugue.Note;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.pfms.dev.ffzhz.api.UserProfileProvider;
import com.pfms.dev.ffzhz.common.UserProfile;
import com.pfms.dev.ffzhz.impl.MusicComposerImpl;
import com.pfms.dev.ffzhz.impl.UserProfileProviderImpl;
import com.pfms.dev.ffzhz.jfugue.Scale;

public class MusicComposerTest {
	MusicComposerImpl composer;

	@Before
	public void setUp() {
		composer = new MusicComposerImpl();
	}

	@Ignore
	@Test
	public void createSongTest() {
		UserProfileProvider provider = new UserProfileProviderImpl();
		UserProfile profile = provider.getNewUserProfile();
		
		profile.setId("ID001");
		profile.setName("Pedro Marquez");
		profile.setAge(25);
		
		File song = composer.createSong(profile,"song.mid");
		assertNotNull("The song is null", song);
		assertTrue("The song can't be read", song.canRead());
	}
	
	/*
	@Test
	public void composeNotes(){
		Note note = MusicStringParser.getNote("C5");
		Scale scale = new Scale(note, Scale.Type.MAJOR);
		System.out.println(composer.createNotesString(scale, 24));
	}
	*/
	
	@Test
	public void getNextNoteTest(){
		Note note = MusicStringParser.getNote("C5");
		Note note2 = MusicStringParser.getNote("E5");
		Scale scale = new Scale(note, Scale.Type.MAJOR);
		System.out.println(composer.getNextRandomNote(note2, scale).getMusicString());
	}
	
	@Test
	public void values(){
		System.err.println("Max:"+Byte.MAX_VALUE);
		System.err.println("Min:"+Byte.MIN_VALUE);
	}

}

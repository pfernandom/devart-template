package com.pfms.dev.ffzhz;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.jfugue.Instrument;
import org.jfugue.MusicStringParser;
import org.jfugue.Note;
import org.jfugue.Pattern;
import org.jfugue.Player;
import org.jfugue.Tempo;
import org.junit.Test;

import com.pfms.dev.ffzhz.common.Composition;
import com.pfms.dev.ffzhz.jfugue.InstrumentChannel;
import com.pfms.dev.ffzhz.jfugue.PercussionChannel;
import com.pfms.dev.ffzhz.jfugue.Scale;

public class CompositionTest {

	@Test
	public void test() {
		Composition util = new Composition();
		InstrumentChannel channel1 = new InstrumentChannel(Tempo.ALLEGRO, Instrument.BIRD_TWEET, 0);
		InstrumentChannel channel2 = new InstrumentChannel(Tempo.ALLEGRO, Instrument.PIANO, 1);
		InstrumentChannel channel3 = new InstrumentChannel(Tempo.ALLEGRO, Instrument.MARIMBA, 2);

		channel1.setNotes(getTestNotes());
		channel2.setNotes(getTestNotes1());
		channel3.setNotes(getTestNotes2());

		util.addChannel(channel1);
		util.addChannel(channel2);
		util.addChannel(channel3);
		
		PercussionChannel percussion = new PercussionChannel();
		percussion.setDefaultRythm();
		
		util.setPercussion(percussion);
		
		Pattern pattern = util.getPattern();
		

		System.err.println(pattern.getMusicString());

		Player player = new Player();
		player.play(pattern);

		assertFalse(pattern.getMusicString().isEmpty());
	}

	@Test
	public void getScaleTest(){
		Note note = MusicStringParser.getNote("C5");
		List<Note> cScale = Scale.getScale(note, Scale.Type.BLUES);
	
		Pattern pattern = new Pattern();
		for(Note n : cScale){
			pattern.add(n.getMusicString());
			System.out.println(n.getMusicString()+"-"+n.getDuration());
		}
		
		Player player = new Player();
		player.play(pattern);
	}
	
	@Test
	public void getScaleTestWLength(){
		Note note = MusicStringParser.getNote("C5");
		List<Note> cScale = Scale.getScale(note, Scale.Type.MAJOR, 20);
	
		Pattern pattern = new Pattern();
		for(Note n : cScale){
			pattern.add(n.getMusicString());
			System.out.println(n.getMusicString()+"-"+n.getDuration());
		}
		
		Player player = new Player();
		player.play(pattern);
	}
	
	@Test
	public void testAddValueToGetNextNote(){
		Note note = MusicStringParser.getNote("C5w");
		
		Note next = MusicStringParser.getNote(note.getMusicString());
		next.setValue((byte) (note.getValue()+1));
		
				
		System.out.println(note.getMusicString());
		System.out.println(next.getMusicString());
		
	}
	
	public List<Note> getTestNotes() {

		ArrayList<Note> notes = new ArrayList<Note>();

		for (int x = 0; x < 4; x++) {
			Note note = MusicStringParser.getNote("Cmajq");
			notes.add(note);
		}

		return notes;
	}

	public List<Note> getTestNotes1() {

		ArrayList<Note> notes = new ArrayList<Note>();

		for (int x = 0; x < 4; x++) {
			Note note = MusicStringParser.getNote("Dmajq");
			notes.add(note);
		}

		return notes;
	}

	public List<Note> getTestNotes2() {

		ArrayList<Note> notes = new ArrayList<Note>();

		for (int x = 0; x < 20; x++) {
			Note note = MusicStringParser.getNote("Emajq");
			notes.add(note);
		}

		return notes;
	}
}

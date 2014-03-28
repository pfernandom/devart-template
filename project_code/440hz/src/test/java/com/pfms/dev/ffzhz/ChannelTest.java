package com.pfms.dev.ffzhz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.jfugue.Instrument;
import org.jfugue.MusicStringParser;
import org.jfugue.Note;
import org.jfugue.Pattern;
import org.jfugue.Player;
import org.jfugue.Tempo;
import org.junit.Ignore;
import org.junit.Test;

import com.pfms.dev.ffzhz.jfugue.InstrumentChannel;
import com.pfms.dev.ffzhz.jfugue.PercussionChannel;

public class ChannelTest {

	@Test
	public void testGetPattern() {
		InstrumentChannel channel = new InstrumentChannel(Tempo.ALLEGRO,
				Instrument.ACCORDIAN, 0);

		ArrayList<Note> notes = new ArrayList<Note>();

		for (int x = 0; x < 4; x++) {
			Note note = MusicStringParser.getNote("Cmajq");
			notes.add(note);
		}

		channel.setNotes(notes);

		Pattern pattern = channel.getPattern();
		assertFalse(pattern.getMusicString().isEmpty());
	}

	@Test
	public void testPercussionChannel() {
		Player player = new Player();

		PercussionChannel percussion = new PercussionChannel();
		percussion.setDefaultRythm();
		percussion.setTempo(Tempo.LENTO);

		Pattern song = new Pattern();
		song.add(percussion.getPattern());

		song.repeat(4);

		player.play(song);
	}

	@Ignore
	@Test
	public void InstrumentChannelQuarterSizeTest() {
		InstrumentChannel channel1 = new InstrumentChannel(Tempo.ALLEGRO,
				Instrument.BIRD_TWEET, 0);

		ArrayList<Note> notes = new ArrayList<Note>();

		for (int x = 0; x < 4; x++) {
			Note note = MusicStringParser.getNote("Cmajq");
			notes.add(note);
		}

		channel1.setNotes(notes);
		assertEquals(120, channel1.getChannelSize());

	}

	@Ignore
	@Test
	public void InstrumentChannelWholeSizeTest() {
		InstrumentChannel channel1 = new InstrumentChannel(Tempo.ALLEGRO,
				Instrument.BIRD_TWEET, 0);

		ArrayList<Note> notes = new ArrayList<Note>();

		for (int x = 0; x < 4; x++) {
			Note note = MusicStringParser.getNote("Cmajw");
			notes.add(note);
		}

		channel1.setNotes(notes);
		assertEquals(480, channel1.getChannelSize());

	}

	@Ignore
	@Test
	public void PercussionChannelSizeTest() {
		PercussionChannel percussion = new PercussionChannel();
		percussion.setDefaultRythm();

		assertEquals(1440, percussion.getChannelSize());
	}

	@Test
	public void noteTranslationTest() {
		Note note = MusicStringParser.getNote("C");
		Note secondNote = new Note((byte) (note.getValue() + 2));
		assertEquals(MusicStringParser.getNote("D").getValue(),
				secondNote.getValue());
	}

	public List<Note> getTestNotes() {
		ArrayList<Note> notes = new ArrayList<Note>();

		for (int x = 0; x < 4; x++) {
			Note note = MusicStringParser.getNote("Cmajq");
			notes.add(note);
		}

		return notes;
	}

}

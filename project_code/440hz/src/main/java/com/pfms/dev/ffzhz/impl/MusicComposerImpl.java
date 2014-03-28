package com.pfms.dev.ffzhz.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.jfugue.Instrument;
import org.jfugue.MusicStringParser;
import org.jfugue.Note;
import org.jfugue.Pattern;
import org.jfugue.Player;

import com.pfms.dev.ffzhz.api.MusicComposer;
import com.pfms.dev.ffzhz.common.Composition;
import com.pfms.dev.ffzhz.common.Genre;
import com.pfms.dev.ffzhz.common.UserProfile;
import com.pfms.dev.ffzhz.common.Util;
import com.pfms.dev.ffzhz.jfugue.InstrumentChannel;
import com.pfms.dev.ffzhz.jfugue.PercussionChannel;
import com.pfms.dev.ffzhz.jfugue.Scale;

public class MusicComposerImpl implements MusicComposer {
	private int DEFAULT_LENGTH = 100;

	public File createSong(UserProfile profile, String path) {
		File song = new File(path);
		Note key = selectKey(profile);
		Note bassKey = selectBassKey(profile);
		Genre genre = Genre.JAZZ;
		Scale scale = selectScale(profile, key, genre);
		Scale bassScale = selectScale(profile, bassKey, genre);

		Composition util = new Composition();
		int tempo = selectTempo(profile, genre);

		System.out.println("Selected tempo:" + tempo);

		InstrumentChannel channel1 = selectMainInstrument(profile, genre, tempo);
		InstrumentChannel channel2 = selectBassInstrument(profile, genre, tempo);

		channel1.setNotes(createNotes(profile, scale));
		channel2.setNotes(createNotes(profile, bassScale));

		util.addChannel(channel1);
		util.addChannel(channel2);

		PercussionChannel percussion = new PercussionChannel();
		// percussion.setDefaultRythm();
		percussion.setRythm(genre);

		util.setPercussion(percussion);
		
		//util.addDelay(4);

		Pattern pattern = util.getPattern();
		pattern.add("R");
		System.err.println(pattern.getMusicString());
		Player player = new Player();

		//player.play(pattern);
		try {
			player.saveMidi(pattern, song);
		} catch (IOException e) {
			// The midi file couldn't be saved
			song = null;
		}

		return song;
	}

	protected List<Note> createNotes(UserProfile profile, Scale scale) {
		List<Note> notes = new ArrayList<Note>();
		int first = Util.random(0, scale.getScale().size() - 1);
		Note note = scale.getScale().get(first);
		notes.add(note);

		int length = DEFAULT_LENGTH;

		byte[] prof = new byte[0];
		try {
			prof = profile.getProfile();
			length = prof.length;
			byte firstByte = prof[0];
			double compas = 0;

			for (int x = 0; x < length; x++) {
				note = getNextNote(prof[x], firstByte, scale);
				double duration = note.getDecimalDuration();
				compas += duration;
				// adjust to always hay blocks of 4 times
				while (compas > 4 && compas != 4) {
					compas -= duration;
					duration /= 2;
					compas += duration;
				}
				note.setDecimalDuration(duration);
				System.out.println("Add note:" + note.getMusicString()+" and compass is "+compas+"/4");
				if (compas == 4) {
					compas = 0;
				}
				notes.add(note);
			}
		} catch (Exception e) {
			System.err
					.println("The user profile is empty. We will create random notes");
			for (int x = 0; x < length; x++) {
				note = getNextRandomNote(note, scale);
				notes.add(note);
			}
		}
		return notes;
	}

	public String createNotesString(UserProfile profile, Scale scale, int length) {
		StringBuilder sb = new StringBuilder();
		for (Note n : createNotes(profile, scale)) {
			sb.append(n.getMusicString() + " ");
		}

		return sb.toString();
	}

	/*
	 * protected Note addDuration(Note note, byte nextByte, byte pastByte) {
	 * 
	 * }
	 */

	public Note getNextRandomNote(Note note, Scale scale) {
		// int index = scale.getIndexOfNote(note);
		int next = Util.random(0, scale.getScale().size() + 2);
		if (next >= scale.getScale().size()) {
			return MusicStringParser.getNote("R");
		}

		return scale.getScale().get(next);
	}

	public Note getNextNote(byte nextByte, byte pastByte, Scale scale) {
		Note result = MusicStringParser.getNote("R");

		byte[] array = Util.getLimits(scale.getScale().size());

		int index = Util.indexOf(nextByte, array);
		result = scale.getScale().get(index);
		float duration = getDuration(nextByte, pastByte, scale);

		result.setDecimalDuration(duration);

		return result;
	}

	private float getDuration(byte nextByte, byte pastByte, Scale scale) {
		byte[] array = Util.getLimits(scale.getScale().size());
		int nextIndex = Util.indexOf(nextByte, array);
		int pastIndex = Util.indexOf(pastByte, array);

		float duration = 1f;
		if (!Util.hasSameSign(pastByte, nextByte)) {
			duration = 1/4f;
		}
		if(nextIndex==pastIndex){
			duration = 1/8f;
		}
		if(Util.isInRange(nextByte, pastByte, 10, true)){
			duration = 1/16f;
		}
		return duration;
	}

	private Note selectKey(UserProfile profile) {
		// TODO Add user personalization
		Note note = MusicStringParser.getNote("C5");
		return note;
	}

	private Note selectBassKey(UserProfile profile) {
		// TODO Add user personalization
		Note note = MusicStringParser.getNote("C3");
		return note;
	}

	private Scale selectScale(UserProfile profile, Note key, Genre genre) {
		// TODO Add user personalization
		Scale scale;
		switch (genre) {
		case JAZZ:
			scale = new Scale(key, Scale.Type.MAJOR);
			break;
		default:
			scale = new Scale(key, Scale.Type.MAJOR);
		}

		return scale;
	}

	private int selectTempo(UserProfile profile, Genre genre) {
		int tempo = (int) ((profile.getAge() * 120) / 30);
		if(tempo > genre.getMax()){
			tempo =  genre.getMax();
		}
		else if(tempo < genre.getMin()){
			tempo =  genre.getMin();
		}
		return tempo;
		//return genre.getRandomTempo();
	}

	private InstrumentChannel selectMainInstrument(UserProfile profile,
			Genre genre, int tempo) {
		byte instrument;
		switch (genre) {
		case JAZZ:
			instrument = Instrument.PIANO;
			break;
		case ROCK:
			instrument = Instrument.ELECTRIC_JAZZ_GUITAR;
			break;
		default:
			instrument = Instrument.PIANO;
		}
		InstrumentChannel channel = new InstrumentChannel(tempo, instrument, 0);
		return channel;
	}

	private InstrumentChannel selectBassInstrument(UserProfile profile,
			Genre genre, int tempo) {
		byte instrument;
		switch (genre) {
		case JAZZ:
			instrument = Instrument.ACOUSTIC_BASS;
			break;
		case ROCK:
			instrument = Instrument.ELECTRIC_BASS_PICK;
			break;
		default:
			instrument = Instrument.ACOUSTIC_BASS;
		}
		InstrumentChannel channel = new InstrumentChannel(tempo, instrument, 1);
		return channel;
	}
}

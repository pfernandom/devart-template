package com.pfms.dev.ffzhz.jfugue;

import org.jfugue.MusicStringParser;
import org.jfugue.Note;
import org.jfugue.Pattern;
import org.jfugue.Rhythm;
import org.jfugue.Tempo;

public class PercussionChannel {
	private String bassDrum;
	private String snare;
	private String pedalHiHat;
	private String crashCymbal;
	private Tempo tempo;
	private int repeat;
	private Note snareNote;

	public PercussionChannel() {
		repeat = 1;
		snareNote = MusicStringParser.getNote("[ACOUSTIC_SNARE]i");
		this.tempo = new Tempo(Tempo.ALLEGRO);
	}

	public Pattern getPattern() {
		Rhythm rhythm = new Rhythm();
		rhythm.setLayer(1, bassDrum);
		rhythm.setLayer(2, snare);
		rhythm.setLayer(3, pedalHiHat);
		rhythm.setLayer(4, crashCymbal);

		rhythm.addSubstitution('O', "[BASS_DRUM]i");
		rhythm.addSubstitution('o', "[BASS_DRUM]s");
		rhythm.addSubstitution('*', "[ACOUSTIC_SNARE]i");
		rhythm.addSubstitution('^', "[PEDAL_HI_HAT]i");
		rhythm.addSubstitution('!', "[CRASH_CYMBAL_1]i");
		rhythm.addSubstitution('.', "Ri");

		Pattern pattern = new Pattern();
		pattern.add(tempo.getMusicString());
		pattern.add(rhythm.getPattern());
		pattern.repeat(repeat);

		System.err.println(pattern);

		return pattern;
	}

	public void setDefaultRythm() {
		bassDrum = "O...oo...O...";
		snare = "..*...*...*.";
		pedalHiHat = "^^^^^^^^^^^^";
		crashCymbal = "...........!";
	}

	public String getBassDrum() {
		return bassDrum;
	}

	public void setBassDrum(String bassDrum) {
		this.bassDrum = bassDrum;
	}

	public String getSnare() {
		return snare;
	}

	public void setSnare(String snare) {
		this.snare = snare;
	}

	public String getPedalHiHat() {
		return pedalHiHat;
	}

	public void setPedalHiHat(String pedalHiHat) {
		this.pedalHiHat = pedalHiHat;
	}

	public String getCrashCymbal() {
		return crashCymbal;
	}

	public void setCrashCymbal(String crashCymbal) {
		this.crashCymbal = crashCymbal;
	}

	public Tempo getTempo() {
		return tempo;
	}

	public void setTempo(Tempo tempo) {
		this.tempo = tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = new Tempo(tempo);
	}

	public void adaptTime(int length) {
		while (getChannelSize() < length) {
			repeat++;
		}
	}
	
	public int getChannelSize(){
		return (int) (snare.length() * snareNote.getDuration() * repeat);
	}
}

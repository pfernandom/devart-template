package com.pfms.dev.ffzhz.jfugue;

import org.jfugue.MusicStringParser;
import org.jfugue.Note;
import org.jfugue.Pattern;
import org.jfugue.Rhythm;
import org.jfugue.Tempo;

import com.pfms.dev.ffzhz.common.Genre;
import com.pfms.dev.ffzhz.common.Util;

public class PercussionChannel {
	private String bassDrum;
	private String snare;
	private String pedalHiHat;
	private String crashCymbal;
	private Tempo tempo;
	private int repeat;
	private Note hiHatNote;
	private int percussionDelay;


	public PercussionChannel() {
		repeat = 1;
		hiHatNote = MusicStringParser.getNote("[PEDAL_HI_HAT]i");
		this.tempo = new Tempo(Tempo.ALLEGRO);
	}

	public Pattern getPattern() {
		Rhythm rhythm = new Rhythm();
		repeat--;
		System.out.println("Repeating " +repeat);
		rhythm.setLayer(1, Util.multiplyString(bassDrum, repeat));
		rhythm.setLayer(2, Util.multiplyString(snare, repeat));
		rhythm.setLayer(3, Util.multiplyString(pedalHiHat, repeat));
		rhythm.setLayer(4, Util.multiplyString(crashCymbal, repeat)+".");

		rhythm.addSubstitution('O', "[BASS_DRUM]i");
		rhythm.addSubstitution('o', "[BASS_DRUM]s");
		rhythm.addSubstitution('*', "[ACOUSTIC_SNARE]i");
		rhythm.addSubstitution('^', "[PEDAL_HI_HAT]i");
		rhythm.addSubstitution('!', "[CRASH_CYMBAL_1]i");
		rhythm.addSubstitution('.', "Ri");
		
		Pattern pattern = new Pattern();
		pattern.add(tempo.getMusicString());
		pattern.add(rhythm.getPattern());
	
		System.err.println("Drums: "+pattern);

		return pattern;
	}

	public void setDefaultRythm() {
		bassDrum = "O...oo...O...";
		snare = "..*...*...*.";
		pedalHiHat = "^^^^^^^^^^^^";
		crashCymbal = "...........!";
	}

	public void setRythm(Genre genre) {
		switch (genre) {
		case JAZZ:
			bassDrum = "O.O.O.O.O.O.O.O.O.O.O.O.";
			snare = ".....*...........*......";
			pedalHiHat = ".^.^.^.^.^.^.^.^.^.^.^.^";
			crashCymbal = ".......................!";
			break;
		case ROCK:
			bassDrum 	= "O.O.O.O.O.O.";
			snare 		= ".*.*.*.*.*.*";
			pedalHiHat 	= "^^^^^^^^^^^^";
			crashCymbal = "...........!";
			break;
		default:
			setDefaultRythm();
		}
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

	public void adaptTime(float length) {
		while (getChannelSize() < length) {
			repeat++;
		}
		System.out.println("Adjust time to "+getChannelSize());
	}

	public float getChannelSize() {
		return  (float) (pedalHiHat.length() * hiHatNote.getDecimalDuration() * repeat);
	}
	
	public void addDelay(int delay){
		percussionDelay = delay;
	}

}

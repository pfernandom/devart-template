package com.pfms.dev.ffzhz.common;

import org.jfugue.Pattern;
import org.jfugue.Rhythm;

public class AudioUtil {
	public Pattern getSound(byte[] pattern, int tempo){
		Rhythm rhythm = new Rhythm();
		rhythm.setLayer(1, "O..oO...O..oOO..");
		//rhythm.setLayer(2, "..*...*...*...*.");
		//rhythm.setLayer(3, "^^^^^^^^^^^^^^^^");
		//rhythm.setLayer(4, "...............!");

		// rhythm.setLayer(5,
		// "C5q D5q E5q C5q C5q D5q E5q C5q E5q F5q G5h E5q F5q G5h G5i A5i G5i F5i E5q C5q G5i A5i G5i F5i E5q C5q");

		rhythm.addSubstitution('O', "[BASS_DRUM]i");
		rhythm.addSubstitution('o', "Rs [BASS_DRUM]s");
		rhythm.addSubstitution('*', "[ACOUSTIC_SNARE]i");
		rhythm.addSubstitution('^', "[PEDAL_HI_HAT]s Rs");
		rhythm.addSubstitution('!', "[CRASH_CYMBAL_1]s Rs");
		rhythm.addSubstitution('.', "Ri");
		return rhythm.getPattern();
	}
}

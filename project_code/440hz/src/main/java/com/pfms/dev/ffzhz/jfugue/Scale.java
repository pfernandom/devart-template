package com.pfms.dev.ffzhz.jfugue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jfugue.MusicStringParser;
import org.jfugue.Note;

public class Scale {
	public enum Type {
		MAJOR, NATURAL_MINOR, MELODIC_MINOR, HARMONIC_MINOR, DIMINISHED, WHOLE_TONE, BLUES, MINOR_PENTATONIC, MAJOR_PENTATONIC, HUNGARIAN_MINOR, PERSIAN, HIROJOSHI, ARABIAN, SCTOTTISH
	}
	
	private Type scaleType;
	private Note scaleNote;
	private List<Note> scaleValue;
	
	
	public Scale(Note note, Type type){
		scaleNote = note;
		scaleType = type;
		scaleValue = getScale(scaleNote, scaleType);
	}
	
	public List<Note> getScale(){
		return this.scaleValue;
	}
	
	public int getIndexOfNote(Note note){
		int index = -1;
		int cont = 0;
		for(Note n : scaleValue){
			if(n.getValue() == note.getValue()){
				index = cont;
			}
			cont++;
		}
		return index;
	}
	
	/**
	 * Returns a scale
	 * @param mainNote Root note
	 * @param type Name of the scale
	 * @param length The number of notes on that scale after the root note
	 * @return
	 */
	public static List<Note> getScale(Note mainNote, Type type, int length) {
		List<Note> scaleList = new ArrayList<Note>();
		List<Integer> steps = getIntervals(type);
		
		scaleList.add(mainNote);
		Note note = mainNote;
		for (int x=0, y=0; x<length; x++,y++) {
			if(y==steps.size()){
				y=0;
			}
			note = getNoteFrom(note, steps.get(y));
			scaleList.add(note);
		}

		return scaleList;
	}
	
	/**
	 * Returns a scale
	 * @param mainNote Root note of the scale
	 * @param type Type of the scale
	 * @return
	 */
	public static List<Note> getScale(Note mainNote, Type type) {
		List<Note> scaleList = new ArrayList<Note>();
		List<Integer> steps = getIntervals(type);
		System.out.println("Main note length: "+mainNote.getDuration());
		scaleList.add(mainNote);
		Note note = mainNote;
		for (int i : steps) {
			note = getNoteFrom(note, i);
			scaleList.add(note);
		}

		return scaleList;
	}
	
	/**
	 * Returns a list of intervals that conforms the different scales
	 * @param type
	 * @return
	 */
	public static List<Integer> getIntervals(Type type) {
		List<Integer> intervals = new ArrayList<Integer>();
		Integer[] list;
		switch (type) {
		case MAJOR:
			list = new Integer[] { 2, 2, 1, 2, 2, 2, 1 };
			break;
		case NATURAL_MINOR:
			list = new Integer[] { 2, 1, 2, 2, 1, 2, 2 };
			break;
		case MELODIC_MINOR:
			list = new Integer[] { 2, 1, 2, 2, 2, 2, 1 };
			break;
		case HARMONIC_MINOR:
			list = new Integer[] { 2, 1, 2, 2, 1, 3, 1 };
			break;
		case DIMINISHED:
			list = new Integer[] { 2, 1, 2, 1, 2, 1, 2, 1 };
			break;
		case WHOLE_TONE:
			list = new Integer[] { 2, 2, 2, 2, 2, 2 };
			break;
		case BLUES:
			list = new Integer[] { 3, 2, 1, 1, 3, 2 };
			break;
		case MINOR_PENTATONIC:
			list = new Integer[] { 3, 2, 2, 3, 2 };
			break;
		case MAJOR_PENTATONIC:
			list = new Integer[] { 2, 2, 3, 2, 3 };
			break;
		case HUNGARIAN_MINOR:
			list = new Integer[] { 2, 1, 3, 1, 1, 3, 1 };
			break;
		case PERSIAN:
			list = new Integer[] { 1, 3, 1, 1, 2, 3, 1 };
			break;
		case HIROJOSHI:
			list = new Integer[] { 2, 1, 4, 1, 4 };
			break;
		case ARABIAN:
			list = new Integer[] { 2, 2, 1, 1, 2, 2, 2 };
			break;
		case SCTOTTISH:
			list = new Integer[] { 2, 3, 2, 2, 3 };
			break;
		default:
			list = new Integer[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		}

		intervals = Arrays.asList(list);
		return intervals;
	}
	
	/**
	 * Gets the following note that is at the provided distance 
	 * @param note
	 * @param distance
	 * @return
	 */
	private static Note getNoteFrom(Note note, int distance){
		Note next = MusicStringParser.getNote(note.getMusicString());
		next.setValue((byte) (note.getValue()+distance));
		return next;
	}
}

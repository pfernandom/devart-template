package com.pfms.dev.ffzhz.jfugue;

import java.util.ArrayList;
import java.util.List;

import org.jfugue.Instrument;
import org.jfugue.MusicStringParser;
import org.jfugue.Note;
import org.jfugue.Pattern;
import org.jfugue.Tempo;
import org.jfugue.Voice;

public class InstrumentChannel {
	private List<Note> notes;
	private Tempo tempo;
	private Voice voice;
	private Instrument instrument;

	public InstrumentChannel(){
		this.tempo = new Tempo(Tempo.ALLEGRO);
		this.instrument = new Instrument(Instrument.PIANO);
		this.voice = new Voice((byte)0);
		this.notes = new ArrayList<Note>();
	}
	
	public InstrumentChannel(int tempo, byte instrument, int voice){
		this.tempo = new Tempo(tempo);
		this.instrument = new Instrument(instrument);
		this.voice = new Voice((byte)voice);
		this.notes = new ArrayList<Note>();
	}
	
	public Pattern getPattern() {
		Pattern pattern = new Pattern();
		pattern.add(voice.getMusicString());
		pattern.add(tempo.getMusicString());
		pattern.add(instrument.getMusicString());
		
		

		for (Note note : notes) {
			pattern.add(note.getMusicString());
		}

		return pattern;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	
	public void setNotes(String notes) {
		String[] notesString = notes.split(" ");
		for(String note : notesString){
			this.notes.add(MusicStringParser.getNote(note));
		}
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

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	
	public void setInstrument(byte instrument) {
		this.instrument = new Instrument(instrument);
	}

	public float getChannelSize(){
		System.out.println("This channel longs:");
		float size = 0;
		for(Note note : notes){
			size += note.getDecimalDuration();
			System.out.println("Adding to size"+size);
		}
		return size;
	}
}

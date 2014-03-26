package com.pfms.dev.ffzhz.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jfugue.Instrument;
import org.jfugue.MusicStringParser;
import org.jfugue.Note;
import org.jfugue.Pattern;
import org.jfugue.Player;
import org.jfugue.Tempo;

import com.pfms.dev.ffzhz.api.MusicComposer;
import com.pfms.dev.ffzhz.common.Composition;
import com.pfms.dev.ffzhz.common.UserProfile;
import com.pfms.dev.ffzhz.jfugue.InstrumentChannel;
import com.pfms.dev.ffzhz.jfugue.PercussionChannel;
import com.pfms.dev.ffzhz.jfugue.Scale;

public class MusicComposerImpl implements MusicComposer {
	private List<Float> probabilities;
	
	public File createSong(UserProfile profile, String path) {
		File song = new File(path);
		Note key = selectKey(profile);
		Note bassKey = selectBassKey(profile);
		Scale scale = selectScale(profile, key);
		Scale bassScale = selectScale(profile, bassKey);
		
		Composition util = new Composition();
		int tempo = selectTempo(profile);
		InstrumentChannel channel1 = new InstrumentChannel(tempo, Instrument.PIANO, 0);
		InstrumentChannel channel2 = new InstrumentChannel(tempo, Instrument.ACOUSTIC_BASS, 1);

		channel1.setNotes(createNotes(scale,50));
		channel2.setNotes(createNotes(bassScale,50));
	
		util.addChannel(channel1);
		util.addChannel(channel2);
		
		PercussionChannel percussion = new PercussionChannel();
		percussion.setDefaultRythm();
		percussion.setTempo(60);
		util.setPercussion(percussion);
		
		
		Pattern pattern = util.getPattern();
		
		System.err.println(pattern.getMusicString());
		Player player = new Player();
		
		player.play(pattern);
		try {
			player.saveMidi(pattern, song);
		} catch (IOException e) {
			//The midi file couldn't be saved
			song = null;
		}
		
		return song;
	}
	
	protected List<Note> createNotes(Scale scale, int length){
		List<Note> notes = new ArrayList<Note>();
		int first = random(0,scale.getScale().size()-1);
		
		Note note = scale.getScale().get(first);
		notes.add(note);
		
		for(int x=0; x<length; x++){
			note = getNextNote(note,scale);
			notes.add(note);
		}
		return notes;
	}
	
	public String createNotesString(Scale scale, int length){
		StringBuilder sb = new StringBuilder();
		for(Note n : createNotes(scale, length)){
			sb.append(n.getMusicString()+" ");
		}
		
		return sb.toString();
	}
	
	 public Note getNextNote(Note note, Scale scale){
		 //int index = scale.getIndexOfNote(note);
		 int next = random(0, scale.getScale().size()+2);
		 if(next >= scale.getScale().size()){
			 return MusicStringParser.getNote("R");
		 }
		
		 return scale.getScale().get(next);
	 }
	 
	 private void updateProbabilities(Note lastPlayed){
		 
	 }
	 
	 private Note selectKey(UserProfile profile){
		 //TODO Add user personalization
		 Note note = MusicStringParser.getNote("C5");
		 return note;
	 }
	 
	 private Note selectBassKey(UserProfile profile){
		 //TODO Add user personalization
		 Note note = MusicStringParser.getNote("C3");
		 return note;
	 }
	 
	 private Scale selectScale(UserProfile profile, Note key){
		//TODO Add user personalization
		 Scale scale = new Scale(key, Scale.Type.MAJOR);
		 return scale;
	 }
	 
	 private int selectTempo(UserProfile profile){
		//TODO Add user personalization
		 return Tempo.ALLEGRO;
	 }
	 
	 private int random(int min, int max){
		return min + (int)(Math.random() * ((max - min) + 1));
	 }
}

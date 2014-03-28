package com.pfms.dev.ffzhz;

import static org.junit.Assert.*;

import org.jfugue.MusicStringParser;
import org.jfugue.Note;
import org.jfugue.Player;
import org.junit.Test;

import com.pfms.dev.ffzhz.common.Util;

public class FunctionalityTest {
	
	
	@Test
	public void testGetLimitsArray(){
		byte[] array = Util.getLimits(12);
		for(byte b :array){
			System.out.println(b);
		}
		assertEquals(13, array.length);
	}
	
	@Test 
	public void testLimitIndex(){
		byte[] array = Util.getLimits(12);
		byte value1 = -128;
		byte value2 = 127;
		byte value3 = -18;
		assertEquals(0,Util.indexOf(value1, array));
		assertEquals(11,Util.indexOf(value2, array));
		assertEquals(5,Util.indexOf(value3, array));
	}
	
	@Test
	public void convertNote(){
		Note note = MusicStringParser.getNote("C5w");
		int distance=1;
		float duration = 1/5f;
		
		Note next = MusicStringParser.getNote(note.getMusicString());
		next.setValue((byte) (note.getValue()+distance));
		next.setDecimalDuration(duration);
		
		System.err.println(next.getMusicString());
	
	}
	
	@Test
	public void testIsInRange(){
		boolean result = Util.isInRange(4.5f, 1f, 10f, true);
		boolean result2 = Util.isInRange(4.5f, 1f, 3f, true);
		
		assertTrue(result);
		assertFalse(result2);
	}
}

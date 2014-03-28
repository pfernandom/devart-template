package com.pfms.dev.ffzhz.common;

import java.util.ArrayList;
import java.util.List;

import org.jfugue.Pattern;

import com.pfms.dev.ffzhz.jfugue.InstrumentChannel;
import com.pfms.dev.ffzhz.jfugue.PercussionChannel;

public class Composition {
	private List<InstrumentChannel> channels;
	private PercussionChannel percussion;
	private int percussionDelay;

	public Composition() {
		channels = new ArrayList<InstrumentChannel>();
		percussionDelay = 0;
	}

	public void addChannel(InstrumentChannel channel) {
		channels.add(channel);
	}

	public Pattern getPattern() {
		Pattern pattern = new Pattern();
		for (InstrumentChannel channel : channels) {
			pattern.add(channel.getPattern());
		}
		if (percussion != null) {
			float length = getMixLength();
			System.out.println("Length of the mix: "+length);
			percussion.adaptTime(length);
			percussion.addDelay(percussionDelay);
			pattern.add(percussion.getPattern());
		}
		return pattern;
	}

	public PercussionChannel getPercussion() {
		return percussion;
	}

	public void setPercussion(PercussionChannel percussion) {
		this.percussion = percussion;
	}
	
	public List<InstrumentChannel> getChannels() {
		return channels;
	}

	public void setChannels(List<InstrumentChannel> channels) {
		this.channels = channels;
	}

	public float getMixLength() {
		float max = 0;
		for (InstrumentChannel channel : channels) {
			if (channel.getChannelSize() > max) {
				max = channel.getChannelSize();
			}
		}
		return max;
	}
		
}

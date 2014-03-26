package com.pfms.dev.ffzhz.common;

import java.util.ArrayList;
import java.util.List;

import org.jfugue.Pattern;

import com.pfms.dev.ffzhz.jfugue.InstrumentChannel;
import com.pfms.dev.ffzhz.jfugue.PercussionChannel;

public class Composition {

	List<InstrumentChannel> channels;
	PercussionChannel percussion;

	public Composition() {
		channels = new ArrayList<InstrumentChannel>();
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
			percussion.adaptTime(getMixLength());
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

	public int getMixLength() {
		int max = 0;
		for (InstrumentChannel channel : channels) {
			if (channel.getChannelSize() > max) {
				max = channel.getChannelSize();
			}
		}
		return max;
	}


		
}

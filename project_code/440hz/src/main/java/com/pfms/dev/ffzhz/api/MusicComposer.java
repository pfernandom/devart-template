package com.pfms.dev.ffzhz.api;

import java.io.File;

import com.pfms.dev.ffzhz.common.UserProfile;

public interface MusicComposer {
	public File createSong(UserProfile profile, String path);
}

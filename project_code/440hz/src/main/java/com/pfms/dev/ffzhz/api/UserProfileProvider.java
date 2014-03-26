package com.pfms.dev.ffzhz.api;

import com.pfms.dev.ffzhz.common.UserProfile;

public interface UserProfileProvider {
	public UserProfile getNewUserProfile();
	public UserProfile getUserProfile(String id);
}

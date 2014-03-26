package com.pfms.dev.ffzhz.impl;

import com.pfms.dev.ffzhz.api.UserProfileProvider;
import com.pfms.dev.ffzhz.common.UserProfile;

public class UserProfileProviderImpl implements UserProfileProvider {

	public UserProfile getNewUserProfile() {
		UserProfile profile = new UserProfile();
		return profile;
	}

	public UserProfile getUserProfile(String id) {
		// TODO Auto-generated method stub
		UserProfile profile = new UserProfile();
		
		profile.setId(id);
		return profile;
	}

}

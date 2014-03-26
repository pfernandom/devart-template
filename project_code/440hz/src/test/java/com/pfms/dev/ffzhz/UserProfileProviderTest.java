package com.pfms.dev.ffzhz;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.pfms.dev.ffzhz.api.UserProfileProvider;
import com.pfms.dev.ffzhz.common.UserProfile;
import com.pfms.dev.ffzhz.impl.UserProfileProviderImpl;

public class UserProfileProviderTest {
	UserProfileProvider provider;

	@Before
	public void setUp() {
		provider = new UserProfileProviderImpl();
	}

	@Test
	public void getNewUserProfileTest() {
		UserProfile profile = provider.getNewUserProfile();
		assertNotNull("The profile was not created", profile);
	}

	@Test
	public void getUserProfileTest() {
		UserProfile profile = provider.getUserProfile("ID001");
		
		assertNotNull("The profile was not created", profile);
		assertEquals("The id is empty","ID001",profile.getId());
	}
}

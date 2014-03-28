package com.pfms.dev.ffzhz;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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
	public void getNewUserProfileToStringTest() {
		UserProfile profile = provider.getNewUserProfile();
		profile.updateTrends();
		System.out.println(profile.toString());
		assertNotNull("The profile was not created", profile);
	}
	
	@Test
	public void getUserProfileBytesTest() throws NoSuchAlgorithmException, IOException {
		UserProfile profile = provider.getNewUserProfile();
		profile.setName("Pedro Marquez");
		profile.setAge(25);
		profile.setId("ID001");
		profile.updateTrends();
		System.out.println(profile.getProfile().length);
		for(byte b: profile.getProfile()){
			if(b>=0)
				System.out.print("+"+b);
			else
				System.out.print(b);
		}
		assertNotNull("The profile was not created", profile.getProfile());
	}

	@Test
	public void getUserProfileTest() {
		UserProfile profile = provider.getUserProfile("ID001");
		
		assertNotNull("The profile was not created", profile);
		assertEquals("The id is empty","ID001",profile.getId());
	}
}

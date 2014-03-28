package com.pfms.dev.ffzhz.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserProfile {
	private String id;
	private String name;
	private float age;
	private byte[] profile;
	private List<File> images;
	private List<String> words;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getAge() {
		return age;
	}

	public void setAge(float age) {
		this.age = age;
	}

	public void updateTrends() {
		if (words == null) {
			words = new ArrayList<String>();
		}
		try {
			words.addAll(Util.getGoogleTrends());
		} catch (Exception e) {
			System.err.println("Could not retrieve the Google Trends: "
					+ e.getMessage());
		}
	}

	public byte[] getProfile() throws IOException, NoSuchAlgorithmException {
		if (profile == null) {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			outputStream.write(Util.getHash("SHA-512", name.getBytes()));
			outputStream.write(Util.getHash("SHA-512",
					new byte[] { (byte) age }));

			if (words != null) {
				for (String word : words) {
					outputStream.write(word.getBytes());
				}
			}
			if (images != null) {
				for (File file : images) {
					System.out.println("Adding image " + file.getPath());
					ByteArrayOutputStream strm = new ByteArrayOutputStream();
					if (file.canRead()) {
						FileInputStream fis = new FileInputStream(file);
						byte[] dataBytes = new byte[1024];

						int nread = 0;
						while ((nread = fis.read(dataBytes)) != -1) {
							strm.write(dataBytes, 0, nread);
						}
						fis.close();
						outputStream.write(Util.getHash("SHA-512",
								strm.toByteArray()));
					}
					strm.close();
				}
			}

			profile = outputStream.toByteArray();
			outputStream.close();
		}
		return profile;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ID:" + id + "\n");
		sb.append("Name:" + name + "\n");
		sb.append("Age:" + age + "\n");
		if (images != null) {
			for (File f : images) {
				sb.append("File-" + f.getName() + "\n");
			}
		}
		if (words != null) {
			for (String word : words) {
				sb.append("Word-" + word + "\n");
			}
		}
		return sb.toString();
	}

	public List<File> getImages() {
		return images;
	}

	public void setImages(List<File> images) {
		this.images = images;
	}

	public void addImages(List<File> imagesIn) {
		if (images == null) {
			images = new ArrayList<File>();
		}
		images.addAll(imagesIn);
	}
}

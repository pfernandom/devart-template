package com.pfms.dev.ffzhz.common;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class Util {
	public static byte[] getHash(String algorithm, byte[] data)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.update(data);
		byte[] mdbytes = md.digest();
		return mdbytes;
	}

	public static byte[] getHash(String algorithm, StringBuilder toEncrypt)
			throws NoSuchAlgorithmException {
		return getHash(algorithm, toEncrypt.toString().getBytes());
	}

	public static List<String> getGoogleTrends() throws Exception {
		URL url = new URL("http://www.google.com/trends/hottrends/atom/hourly");
		List<String> trends = new ArrayList<String>();
		HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
		// Reading the feed
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(httpcon));
		List entries = feed.getEntries();
		Iterator itEntries = entries.iterator();

		while (itEntries.hasNext()) {
			SyndEntry entry = (SyndEntry) itEntries.next();
			SyndContentImpl content = (SyndContentImpl) entry.getContents()
					.get(0);

			Document doc = loadXMLFromString(content.getValue());
			doc.normalize();

			NodeList name = doc.getElementsByTagName("span");
			for (int x = 0; x < name.getLength(); x++) {
				NamedNodeMap map = name.item(x).getAttributes();
				// System.err.println(map.getNamedItem("class"));
				trends.add(name.item(x).getTextContent());
			}
		}
		return trends;
	}

	public static Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

	public static int random(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	public static byte[] getLimits(int arrayLength) {
		float limit = 254 / arrayLength;
		int cont = 0;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(-128);
		byte[] all = new byte[254];
		byte y = -128;
		for (int x = 0; x < 254; x++) {
			all[x] = y;
			y++;
			cont++;
			if (cont > limit) {
				outputStream.write(y);
				cont = 0;
			}
		}
		outputStream.write(127);
		return outputStream.toByteArray();
	}

	public static int indexOf(byte value, byte[] limits) {
		int index = -1;
		for (int x = 0; x < limits.length - 1; x++) {
			if (value >= limits[x] && value <= limits[x + 1]) {
				index = x;
			}
		}
		return index;
	}

	public static boolean hasSameSign(int n1, int n2) {
		boolean result = false;
		if (n1 > 0 && n2 < 0) {
			result = true;
		} else if (n2 > 0 && n1 < 0) {
			result = true;
		}

		return result;
	}

	public static boolean isInRange(float value, float from, float rangeSize,
			boolean bidirectional) {
		boolean result = false;
		float min = from;
		float max = rangeSize;

		if (bidirectional) {
			min = from - (rangeSize / 2);
			max = from + (rangeSize / 2);
		}

		for (float x = min; x <= max; x++) {
			if (x == value || between(value, x, x + 1)) {
				result = true;
			}
		}
		return result;
	}

	public static boolean between(float value, float start, float end) {
		if ((value > start) && (value < end)) {
			return true;
		} else {
			return false;
		}
	}

	public static String multiplyString(String s, int times) {
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < times; x++) {
			sb.append(s);
		}
		return sb.toString();
	}

	public static Image resize(BufferedImage img, double width, double height) {
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		while (w > width || h > height) {
			w *= 0.99;
			h *= 0.99;
		}
		return img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
	}
}

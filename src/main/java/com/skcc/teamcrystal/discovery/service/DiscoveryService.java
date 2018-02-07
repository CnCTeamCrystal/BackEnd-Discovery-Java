package com.skcc.teamcrystal.discovery.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DiscoveryService {
	private static final Logger logger = LoggerFactory.getLogger(DiscoveryService.class);

	public JSONObject json;

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	@SuppressWarnings("unused")
	public JSONObject ReadJsonFromURL(String company) throws IOException, JSONException {
		String furl = "https://gateway.watsonplatform.net/discovery/api/v1/environments/system/collections/news-ko/query?version=2017-11-07&aggregation=nested%28enriched_text.keywords%29.filter%28enriched_text.keywords.text%3A%22";
		String burl = "%22%29.term%28enriched_text.keywords.text%2Ccount%3A10%29&highlight=true&passages.count=5&query=";

		StringBuilder url_b = new StringBuilder();
		url_b.append(furl);
		url_b.append(company);
		url_b.append(burl);
		URL url = new URL(url_b.toString());

		URLConnection urlConnection = url.openConnection();
		String auth = "fa6ca473-2c4a-4222-828a-066ae905c664:UNLBvDbEbE1S";

		if (auth != null) {
			byte[] authEncBytes = Base64.encodeBase64(auth.getBytes());
			String authStringEnc = new String(authEncBytes);
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

			InputStream is = urlConnection.getInputStream();
			logger.info(url_b.toString());

			try {
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				String jsonText = readAll(rd);
				json = new JSONObject(jsonText);
				return json;
			} finally {
				is.close();
			}
		}
		// InputStream is = new URL(url_b.toString()).openStream();
		/*
		 * System.out.println(4); logger.info(url_b.toString());
		 * 
		 * try { BufferedReader rd = new BufferedReader(new InputStreamReader(is,
		 * Charset.forName("UTF-8"))); String jsonText = readAll(rd); JSONObject json =
		 * new JSONObject(jsonText); return json; } finally { is.close(); }
		 */
		return json;
	}

}

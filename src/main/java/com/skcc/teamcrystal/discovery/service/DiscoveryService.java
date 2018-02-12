package com.skcc.teamcrystal.discovery.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
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
	public JSONObject RealTimeKeyword(String company) throws IOException, JSONException {
		String furl = "https://gateway.watsonplatform.net/discovery/api/v1/environments/system/collections/news-ko/query?version=2017-11-07&filter=text%3A%22";
		String murl1 = "%22%2Ccrawl_date%3A%3A%22";
		String murl2 = "%22%2Cenriched_text.keywords.text%3A%22";
		String burl = "%22&deduplicate=false&highlight=true&passages=true&passages.count=5&query=";

		Date date = new Date();
		SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(dateF.format(date));

		StringBuilder url_b = new StringBuilder();
		url_b.append(furl);
		url_b.append(company);
		url_b.append(murl1);
		url_b.append(dateF.format(date));
		url_b.append(murl2);
		url_b.append(company);
		url_b.append(burl);
		URL url = new URL(url_b.toString());

		URLConnection urlConnection = url.openConnection();
		String auth = "360d8c17-afed-46f3-9899-c9dfad06f309:2D13wfeTYVUK";

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

		return json;
	}

	@SuppressWarnings("unused")
	public JSONArray PositiveKeyword(String company) throws IOException, JSONException {
		String furl = "https://gateway.watsonplatform.net/discovery/api/v1/environments/system/collections/news-ko/query?version=2017-11-07&aggregation=nested%28enriched_title.entities%29.filter%28enriched_title.entities.type%3A%3A%22Company%22%29.term%28enriched_title.entities.text%2Ccount%3A5%29&filter=enriched_text.sentiment.document.score%3E0.5&deduplicate=false&highlight=true&passages=true&passages.count=5&natural_language_query=";

		StringBuilder url_b = new StringBuilder();
		url_b.append(furl);
		url_b.append(company);
		URL url = new URL(url_b.toString());

		URLConnection urlConnection = url.openConnection();
		String auth = "360d8c17-afed-46f3-9899-c9dfad06f309:2D13wfeTYVUK";

		JSONArray jarray = new JSONArray();
		if (auth != null) {
			byte[] authEncBytes = Base64.encodeBase64(auth.getBytes());
			String authStringEnc = new String(authEncBytes);
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

			InputStream is = urlConnection.getInputStream();
			logger.info(url_b.toString());

			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			json = new JSONObject(jsonText);

			ArrayList<String> key_list = new ArrayList<String>();
			key_list = Parser_Sentiment(json);

			jarray = new JSONArray(key_list);

			return jarray;
		}
		return jarray;
	}

	@SuppressWarnings("unused")
	public JSONArray NegativeKeyword(String company) throws IOException, JSONException {
		String furl = "\r\n"
				+ "https://gateway.watsonplatform.net/discovery/api/v1/environments/system/collections/news-ko/query?version=2017-11-07&aggregation=nested%28enriched_title.entities%29.filter%28enriched_title.entities.type%3A%3A%22Company%22%29.term%28enriched_title.entities.text%2Ccount%3A5%29&filter=enriched_text.sentiment.document.score%3C-0.5&deduplicate=false&highlight=true&passages=true&passages.count=5&natural_language_query=";

		StringBuilder url_b = new StringBuilder();
		url_b.append(furl);
		url_b.append(company);
		URL url = new URL(url_b.toString());

		URLConnection urlConnection = url.openConnection();
		String auth = "360d8c17-afed-46f3-9899-c9dfad06f309:2D13wfeTYVUK";

		JSONArray jarray = new JSONArray();
		if (auth != null) {
			byte[] authEncBytes = Base64.encodeBase64(auth.getBytes());
			String authStringEnc = new String(authEncBytes);
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

			InputStream is = urlConnection.getInputStream();
			logger.info(url_b.toString());

			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			json = new JSONObject(jsonText);

			ArrayList<String> key_list = new ArrayList<String>();
			key_list = Parser_Sentiment(json);

			jarray = new JSONArray(key_list);

			return jarray;
		}
		return jarray;
	}

	@SuppressWarnings("unused")
	public JSONObject PeriodSearch(String company, String sDate, String dDate) throws IOException, JSONException {
		String furl = "https://gateway.watsonplatform.net/discovery/api/v1/environments/system/collections/news-ko/query?version=2017-11-07&filter=crawl_date%3E";
		String murl1 = "T12%3A00%3A00%2B0900%2Ccrawl_date%3C";
		String murl2 = "T12%3A00%3A00%2B0900%2C%28text%3A%22";
		String murl3 = "%22%2Cenriched_text.keywords.text%3A%22";
		String burl = "%22%29&deduplicate=false&highlight=true&passages=true&passages.count=5&query=";

		Date date = new Date();
		SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(dateF.format(date));

		StringBuilder url_b = new StringBuilder();
		url_b.append(furl);
		url_b.append(sDate);
		url_b.append(murl1);
		url_b.append(dDate);
		url_b.append(murl2);
		url_b.append(company);
		url_b.append(murl3);
		url_b.append(company);
		url_b.append(burl);
		URL url = new URL(url_b.toString());

		URLConnection urlConnection = url.openConnection();
		String auth = "360d8c17-afed-46f3-9899-c9dfad06f309:2D13wfeTYVUK";

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
		return json;
	}

	public ArrayList<String> Parser_Sentiment(JSONObject obj) throws JSONException {
		JSONArray jobj = new JSONArray();
		jobj = obj.getJSONArray("aggregations");

		JSONObject jobj2 = new JSONObject();
		jobj2 = (jobj.getJSONObject(0));

		System.out.println(jobj2);

		JSONArray jobj3 = new JSONArray();
		jobj3 = jobj2.getJSONArray("aggregations");

		System.out.println(jobj3);

		JSONObject jobj4 = new JSONObject();
		jobj4 = (jobj3.getJSONObject(0));

		JSONArray jobj5 = new JSONArray();
		jobj5 = jobj4.getJSONArray("aggregations");

		System.out.println(jobj5);

		JSONObject jobj6 = new JSONObject();
		jobj6 = (jobj5.getJSONObject(0));

		System.out.println(jobj6);

		JSONArray jobj7 = new JSONArray();
		jobj7 = jobj6.getJSONArray("results");

		System.out.println(jobj7);
		/*
		 * JSONArray jobj3 = new JSONArray(); jobj3 = jobj2.getJSONArray("results");
		 */

		ArrayList<String> keyword = new ArrayList<>();

		for (int i = 0; i < jobj7.length(); i++) {
			keyword.add(jobj7.getJSONObject(i).getString("key"));
		}

		System.out.println(keyword);

		return keyword;
	}
}

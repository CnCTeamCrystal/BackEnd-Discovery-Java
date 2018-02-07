package com.skcc.teamcrystal.discovery.controller;

import java.io.IOException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.teamcrystal.discovery.service.DiscoveryService;

@RestController
public class DiscoveryController {

	@Autowired
	DiscoveryService discoveryservice;

	@CrossOrigin
	@RequestMapping(value = "/discovery/realtime/{company}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String Search(@PathVariable String company) throws IOException, JSONException {
		String name = URLEncoder.encode(company, "UTF-8");
		System.out.println(name);

		company = "%EC%82%BC%EC%84%B1";
		// System.out.println(company);
		JSONObject json = discoveryservice.RealTimeKeyword(name);

		return json.toString();
	}

	@RequestMapping(value = "/discovery/positive/{company}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String PositivelyRelevant(@PathVariable String company) throws IOException, JSONException {
		String name = URLEncoder.encode(company, "UTF-8");
		System.out.println(name);

		JSONObject json = discoveryservice.PositiveKeyword(name);

		return json.toString();
	}

	@RequestMapping(value = "/discovery/negative/{company}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String NegativelyRelevant(@PathVariable String company) throws IOException, JSONException {
		String name = URLEncoder.encode(company, "UTF-8");
		System.out.println(name);

		JSONObject json = discoveryservice.NegativeKeyword(name);

		return json.toString();
	}

	@RequestMapping(value = "/discovery/period/{company}/{sDate}/{dDate}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String PeriodSearch(@PathVariable String company, @PathVariable String sDate, @PathVariable String dDate)
			throws IOException, JSONException {
		String name = URLEncoder.encode(company, "UTF-8");
		System.out.println(name);

		// System.out.println(company);
		JSONObject json = discoveryservice.PeriodSearch(name, sDate, dDate);

		return json.toString();
	}
}

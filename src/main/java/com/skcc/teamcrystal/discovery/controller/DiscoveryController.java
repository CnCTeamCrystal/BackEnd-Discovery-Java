package com.skcc.teamcrystal.discovery.controller;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.skcc.teamcrystal.discovery.service.DiscoveryService;

@RestController
public class DiscoveryController {

	@Autowired
	DiscoveryService discoveryservice;

	@RequestMapping(value = "/discovery/{company}", method = RequestMethod.GET, 
			produces={MediaType.APPLICATION_JSON_VALUE})
	public String Search(@PathVariable String company) throws IOException, JSONException {
		company="%EC%82%BC%EC%84%B1";
		System.out.println(company);
		JSONObject json = discoveryservice.ReadJsonFromURL(company);


		return json.toString();
	}

}

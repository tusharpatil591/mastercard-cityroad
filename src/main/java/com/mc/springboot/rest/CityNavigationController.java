package com.mc.springboot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mc.springboot.error.ApplicationExceptionHandler;
import com.mc.springboot.service.CityNavigationService;

/**
 * This class is a rest endpoint controller
 * @author tushar.patil
 *
 */
@RestController
@RequestMapping("/")
public class CityNavigationController {
	
	@Autowired
	CityNavigationService cityNavigationService;
	
	@GetMapping("/connected")
	public String citiesAreConnected(
			@RequestParam(value="origin", defaultValue="") String origin,
			@RequestParam(value="destination", defaultValue="") String destination) throws Exception {

		boolean areTwoCitiesConnected = false; 
		try {
			if (StringUtils.isEmpty(origin) || StringUtils.isEmpty(destination)) {
				throw new ApplicationExceptionHandler(HttpStatus.BAD_REQUEST, "Bad Request: Provide valid Origin and Destination");
			}
			
			areTwoCitiesConnected =  cityNavigationService.isConnected(origin, destination);
		} catch (Exception e) {
			throw e;
		}
		return areTwoCitiesConnected ? "yes" :"no";
	}
}

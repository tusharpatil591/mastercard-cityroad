package com.mc.springboot.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mc.springboot.service.CityNavigationService;

/**
 * This class is an implementation of service class
 * @author tushar.patil
 *
 */
@Component
public class CityNavigationServiceImpl implements CityNavigationService {

	private static final Logger logger = LoggerFactory.getLogger(CityNavigationServiceImpl.class);

	@Autowired
	ResourceLoader resourceLoader;

	@Value("${city.connections.file.path}")
	private String cityConnectionsFilePath;

	private Map<String, LinkedList<String>> appLevelCityCache = new ConcurrentHashMap<String, LinkedList<String>>();

	/**
	 * This method is load cities from txt file and put into appLevelCityCache Map
	 * @throws Exception
	 */
	@Override
	public void load() throws Exception {
		Resource resource = resourceLoader.getResource(cityConnectionsFilePath);
		InputStream is = null;
		Scanner scanner = null;
		try {
			//safe check incase if we are using file path instead of path in the classpath
			if (!resource.exists()) {
				logger.error("the file mentioned in the given path doesn't exist");
				return;
			}
			is = resource.getInputStream();
			scanner = new Scanner(is);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				//Ignore if an empty line is encountered
				if (StringUtils.isEmpty(line)) {
					continue;
				}
				String[] parts = line.split(",");
				//store all city Names by making it UpperCase
				String first = parts[0]==null?"":parts[0].trim().toUpperCase();
				String second = parts[1]==null?"":parts[1].trim().toUpperCase();

				//check if both the cities are same and not empty
				if (!StringUtils.isEmpty(first) && !StringUtils.isEmpty(second) && !first.equals(second)) {
					LinkedList<String> connectedCitiesForCityA = appLevelCityCache.getOrDefault(first, new LinkedList<String>());
					LinkedList<String> connectedCitiesForCityB = appLevelCityCache.getOrDefault(second, new LinkedList<String>());

					//road connection is 2 way so we need to connect cities from both the directions
					connectedCitiesForCityA.add(second);
					connectedCitiesForCityB.add(first);
					
					//update or insert in the map
					appLevelCityCache.put(first, connectedCitiesForCityA);
					appLevelCityCache.put(second, connectedCitiesForCityB);
				}
			}

		} catch (IOException ex) {
			logger.error("IOException occured while reading the input file data", ex);
			throw ex;
		} finally {
			if (scanner != null) {
				scanner.close();
			}
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * This method evaluates if two cities are connected based on given origin and destination
	 * In case if both the cities are not present in the initial data load then it returns false
	 * 
	 * @param origin
	 * @param destination
	 * @throws Exception
	 * @return boolean
	 * 
	 */
	@Override
	public boolean isConnected(String origin, String destination) throws Exception {
		//either one of the null or empty return false
		if (StringUtils.isEmpty(origin) || StringUtils.isEmpty(destination)) {
			return false;
		}
		//there isn't enough information to ascertain if 2 cities are connected
		if (appLevelCityCache.size() == 0) {
			logger.debug("not enough road data to decide if cities are connected");
			return false;
		}
		origin = origin.trim().toUpperCase();
		destination = destination.trim().toUpperCase();

		//same city and valid city given list of cities. It eliminates comparison to a string value which may not be a city
		if (origin.equalsIgnoreCase(destination) && appLevelCityCache.containsKey(origin)){
			logger.debug("if a Valid City is given as origin and destination then they are connected");
			return true;
		}
		if (!appLevelCityCache.containsKey(origin) && !appLevelCityCache.containsKey(destination)) {
			logger.debug("both given cities are not present in the initially loaded data, can't determine if they are connected");
			return false;
		}
		//to keep track of visited nodes
		Set<String> visited = new HashSet<String>();
		//use BFS traversal technique to look for connected nodes. use a queue to traverse cities one by one
		Queue<String> queue = new LinkedList<String>();
		queue.offer(origin);
		while(!queue.isEmpty()) {
			String queueEntry = queue.poll();
			visited.add(queueEntry);
			if (queueEntry.equals(destination)) {
				return true;
			}
			//lookup in the map if destination exists
			LinkedList<String> linkedCities = appLevelCityCache.get(queueEntry);
			if (linkedCities != null) {
				for (String linkedCity : linkedCities) {
					if (!visited.contains(linkedCity)) {
						queue.offer(linkedCity);
					}
				}
			}
			
		}
		return false;
	}

}
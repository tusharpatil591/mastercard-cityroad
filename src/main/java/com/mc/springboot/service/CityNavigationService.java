package com.mc.springboot.service;

/**
 * 
 * @author tushar.patil
 *
 */
public interface CityNavigationService {
	
	/**
	 * this method is used to load initial data from flat file in the classpath on the application startup
	 * 
	 * @throws Exception
	 */
	public void load() throws Exception;
	/**
	 *  This method will determine if 2 cities are connected if origin and destination are provided
	 *  
	 * @param origin
	 * @param destination
	 * @return
	 * @throws Exception
	 */
	public boolean isConnected(String origin, String destination) throws Exception;
}

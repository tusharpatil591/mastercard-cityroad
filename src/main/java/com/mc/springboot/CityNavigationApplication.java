package com.mc.springboot;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mc.springboot.service.CityNavigationService;

/**
 * This class is a main application class and loaded cities text file by commandLineRunner
 * @author tushar.patil
 *
 */

@SpringBootApplication
public class CityNavigationApplication implements CommandLineRunner  {

	private static final Logger logger = LoggerFactory.getLogger(CityNavigationApplication.class);

	@Autowired
	CityNavigationService navigationService;
	
	public static void main(String[] args) {
		SpringApplication.run(CityNavigationApplication.class, args);

	}
	
	/**
	 * This method loads the initial data when the application is started
	 */
	@Override
	public void run(String... args) throws Exception {
		boolean isInitializationError = false;
		try {
			//load the data on startup
			logger.trace("loading the initialization data from classpath file");
			navigationService.load();
		} catch (IOException ex){
			isInitializationError = true;
			logger.error("IOException occured while reading the input file data", ex);
			throw ex;
		} finally {
			if (isInitializationError) {
				logger.error("Error occured while loading the initialization data");
			}
		}
	}

}

package com.mc.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.mc.springboot.rest.CityNavigationController;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
class CityNavigationApplicationTests {

	@LocalServerPort
	int randomServerPort;
	
	@Autowired
	private CityNavigationController controller;

	@Autowired
	private TestRestTemplate restTemplate;

	//Checking that controller is not null during contextLoads
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
	
	//Negative scenario for bad request
	@Test
	public void testBadRequest() throws URISyntaxException 
	{
		final String baseUrl = "http://localhost:"+randomServerPort+"/connected";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		assertThat(result.getStatusCodeValue()).isEqualTo(500); 
	}

	//Test for lowercase origin and destination
	@Test
	public void testLowerCase() throws URISyntaxException 
	{
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("origin", "boston");
		queryParams.put("destination", "newark");
        String response = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, queryParams);
		assertThat(response.equals("yes")); 
	}

	//Zigzag case verifying response
	@Test
	public void testZigzagCase() throws URISyntaxException 
	{
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("origin", "PHILAdelphIA");
		queryParams.put("destination", "NEWark");
        String response = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, queryParams);
		assertThat(response.equals("yes")); 
	}

	//Cities are connected
	@Test
	public void testConnectedCities() throws URISyntaxException 
	{
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("origin", "Boston");
		queryParams.put("destination", "Newark");
        String response = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, queryParams);
		assertThat(response.equals("yes")); 
	}
	
	//Cities are not connected
	@Test
	public void testUnConnectedCities() throws URISyntaxException 
	{
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("origin", "Boston");
		queryParams.put("destination", "Albany");
        String response = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, queryParams);
		assertThat(response.equals("no")); 
	}

	//Cities are invalid
	@Test
	public void testInvalidCities() throws URISyntaxException 
	{
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("origin", "AA");
		queryParams.put("destination", "BB");
        String response = restTemplate.getForObject("/connected?origin={origin}&destination={destination}", String.class, queryParams);
		assertThat(response.equals("no")); 
	}

}
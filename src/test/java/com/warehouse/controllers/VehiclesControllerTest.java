package com.warehouse.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VehiclesControllerTest {

    @Autowired
    TestRestTemplate restTemplate;
    HttpHeaders headers = new HttpHeaders();
    @LocalServerPort
    private int port;

    @Test
    public void testRetrieveAllVehicles() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/vehicles"), HttpMethod.GET, entity, String.class);
        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testRetrieveVehicleByValidVehicleId() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/vehicles/55"), HttpMethod.GET, entity, String.class);
        String expected = "{\"_id\":\"55\",\"make\":\"Volvo\",\"model\":\"850\",\"price\":25762.08,\"licensed\":true,\"year_model\":\"1995\",\"date_added\":\"2017-10-03T00:00:00.000+0000\"}";
        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
        JSONAssert.assertEquals(expected, response.getBody(), true);
    }

    @Test
    public void testRetrieveDetailsByValidVehicleId() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/vehicles/80/details"), HttpMethod.GET, entity, String.class);
        String expected = "{\"warehouseId\":\"4\",\"warehouseName\":\"Warehouse D\",\"warehouseLocationLatitude\":-70.84354,\"warehouseLocationLongitude\":132.22345,\"carLocation\":\"Suid wing\"}";
        JSONAssert.assertEquals(expected, response.getBody(), true);
    }

    @Test
    public void testRetrieveVehicleByInvalidVehicleId() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/vehicles/800"), HttpMethod.GET, entity, String.class);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    public void testRetrieveDetailsByInvalidVehicleId() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/vehicles/800/details"), HttpMethod.GET, entity, String.class);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @TestConfiguration
    static class TestRestTemplateAuthenticationConfiguration {
        @Value("${spring.security.user.name}")
        private String userName;

        @Value("${spring.security.user.password}")
        private String password;

        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthorization(userName, password);
        }
    }

}

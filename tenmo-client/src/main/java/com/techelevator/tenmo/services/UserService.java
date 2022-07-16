package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import okhttp3.internal.http2.Header;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class UserService {

    public static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }


    public List<User> listOfUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity entity = new HttpEntity<>(headers);


        User[] users = null;


        try {
            ResponseEntity<User[]> result = restTemplate.exchange(API_BASE_URL + "users", HttpMethod.GET, entity, User[].class);
            users = result.getBody();
        }
        catch(ResourceAccessException | RestClientResponseException ex) {
            BasicLogger.log(ex.getMessage());

        }

        return Arrays.asList(users);

    }

    //handling auth
    private HttpEntity<Void> makeAuthEntity( ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }




}

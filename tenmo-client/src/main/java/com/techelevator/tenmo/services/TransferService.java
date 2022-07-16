package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class TransferService {
    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Transfer addTransfer(Transfer newTransfer) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Transfer> entity = new HttpEntity<>(newTransfer, header);
        Transfer returnedTransfer = null;
        try {

            returnedTransfer = restTemplate.postForObject(API_BASE_URL + "transfers", entity, Transfer.class);
        } catch (ResourceAccessException | RestClientResponseException ex) {
            BasicLogger.log(ex.getMessage());
        }
        return returnedTransfer;
    }

    public List<Transfer> returnListOfTransfers() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setBearerAuth(authToken);
        HttpEntity entity = new HttpEntity<>(header);

        Transfer[] transfers = null;

        try {
            ResponseEntity<Transfer[]> result = restTemplate.exchange(API_BASE_URL + "transfers",
                    HttpMethod.GET, entity, Transfer[].class);
            transfers = result.getBody();

        } catch (ResourceAccessException | RestClientResponseException ex) {
            BasicLogger.log(ex.getMessage());
        }

        return Arrays.asList(transfers);

    }
}

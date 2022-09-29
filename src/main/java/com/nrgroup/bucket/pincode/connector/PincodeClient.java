package com.nrgroup.bucket.pincode.connector;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.nrgroup.bucket.exception.ApiException;
import com.nrgroup.bucket.pincode.model.PincodeResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PincodeClient {

    @Autowired
    PincodeClientConfiguration clientConfiguration;

    WebClient client;

    @PostConstruct
    private void postConstruct() {
        this.client = clientConfiguration.configure();
    }

    public PincodeResponse getPincodeAddress(String path) {
        PincodeResponse[] pincodeResponses = null;
        try {
            pincodeResponses = client.get()
                    .uri(path)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(PincodeResponse[].class)
                    .block();
        } catch (WebClientResponseException cre) {
            log.error("RestClientResponseException in PincodeClient getPincodeAddress - {}", cre.getMessage());
            throw new ApiException(cre.getStatusText());
        } catch (WebClientException ce) {
            log.error("RestClientException in PincodeClient getPincodeAddress - {}", ce.getMessage());
            throw new ApiException("RestClientException in PincodeClient getPincodeAddress");
        } catch (Exception e) {
            log.error("Exception in PincodeClient getPincodeAddress - {}", e.getMessage());
            throw new ApiException(e.getClass() + "::" + e.getMessage());
        }
        return pincodeResponses[0];
    }
}
package com.nrgroup.bucket.pincode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nrgroup.bucket.config.SecurityRule;
import com.nrgroup.bucket.pincode.model.AddressDetails;
import com.nrgroup.bucket.pincode.service.PincodeService;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api")
public class PincodeController {

    @Autowired
    PincodeService pincodeService;

    @GetMapping("/v1/pincode")
    @PreAuthorize(SecurityRule.PERMIT_ALL)
    public Mono<ResponseEntity<AddressDetails>> test(@RequestParam("pincode") Integer pincode)
            throws JsonProcessingException {
        return Mono.fromCallable(() -> {
            AddressDetails response = pincodeService.getAddressByPincode(pincode);
            return ResponseEntity.ok(response);
        }).subscribeOn(Schedulers.boundedElastic());
    }
}

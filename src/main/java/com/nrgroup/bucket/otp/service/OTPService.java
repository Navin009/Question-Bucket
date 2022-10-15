package com.nrgroup.bucket.otp.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OTPService {

    public String generateOTP(String uniqueId) {
        Random random = new Random();
        random.setSeed(generateSeed(uniqueId));
        StringBuffer otp = new StringBuffer(4);
        for (int i = 0; i < 4; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    public long generateSeed(String uniqueId) {
        return uniqueId.hashCode() + System.currentTimeMillis();
    }

    public boolean validateOTP(String userOTP, String generatedOTP) {
        return userOTP.equals(generatedOTP);
    }
}

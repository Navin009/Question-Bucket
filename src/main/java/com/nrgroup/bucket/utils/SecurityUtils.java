package com.nrgroup.bucket.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import com.nrgroup.bucket.entity.User;

public class SecurityUtils {

    public static String getUserNo() {
        return null;
    }

    public static User getUser() {
        return null;
    }

    public static String generateMD5Hash(String uniqueValue) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] hash = messageDigest.digest(uniqueValue.getBytes());
            return DatatypeConverter.printHexBinary(hash);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}

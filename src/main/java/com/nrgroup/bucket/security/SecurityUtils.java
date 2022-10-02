package com.nrgroup.bucket.security;

import java.nio.file.attribute.UserPrincipal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nrgroup.bucket.entity.User;

public class SecurityUtils {

    public static String getUserNo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
            return userDetails.getName();
        }
        return null;
    }

    public static User getUser() {
        Authentication authentication = null;
        // ReactiveSecurityContextHolder.getContext().map(context -> context.getAuthentication().getPrincipal()).cast();
        if (authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
            // return userDetails.getUser();
            return null;
        }
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

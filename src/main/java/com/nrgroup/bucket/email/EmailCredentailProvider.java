package com.nrgroup.bucket.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

@Service
public class EmailCredentailProvider implements AwsCredentialsProvider {

    @Value("${mail.access-id}")
    private String accessKeyId;

    @Value("${mail.secret-key}")
    private String secretKey;

    @Override
    public AwsCredentials resolveCredentials() {

        return new AwsCredentials() {

            @Override
            public String accessKeyId() {
                return accessKeyId;
            }

            @Override
            public String secretAccessKey() {
                return secretKey;
            }
        };
    }

}

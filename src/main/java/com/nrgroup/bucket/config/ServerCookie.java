package com.nrgroup.bucket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "server.cookie")
public class ServerCookie {
    private String name;
    private String path;
    private String sameSite;
    private Boolean httpOnly;
    private Boolean secure;
}

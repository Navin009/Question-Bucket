package com.nrgroup.bucket.pincode.connector;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Setter;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Setter
@Component
@ConfigurationProperties(prefix = PincodeClientConfiguration.PREFIX)
public class PincodeClientConfiguration {

    public static final String PREFIX = "pincode.config";

    String url;

    Integer maxTotal;

    Integer connTimeout;

    private ReactorClientHttpConnector clientConfigure() {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("PincodeClientConfiguration")
                .maxConnections(maxTotal)
                .build();

        HttpClient httpClient = HttpClient.create(connectionProvider)
                .responseTimeout(Duration.ofMillis(connTimeout));

        return new ReactorClientHttpConnector(httpClient);
    }

    public WebClient configure() {
        return WebClient.builder()
                .baseUrl(url)
                .clientConnector(clientConfigure())
                .build();
    }
}

package com.nrgroup.bucket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Autowired
    SecurityAnnotationFilter securityAnnotationFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // @formatter:off
        http.authorizeExchange()
                .anyExchange().permitAll()
            .and()
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .addFilterBefore(securityAnnotationFilter, SecurityWebFiltersOrder.FIRST);
        // @formatter:on
        return http.build();
    }
}

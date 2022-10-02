package com.nrgroup.bucket.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.nrgroup.bucket.utils.JwtUtils;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = jwtUtils.getUsernameFromToken(authToken);
        return Mono.just(jwtUtils.validateToken(authToken))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.empty())
                .map(valid -> {
                    Claims claims = jwtUtils.getAllClaimsFromToken(authToken);
                    List<String> roleMap = claims.get("role", List.class);
                    return new UsernamePasswordAuthenticationToken(username, null,
                            roleMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                });
    }

}

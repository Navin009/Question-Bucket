package com.nrgroup.bucket.security;

import java.util.List;

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
        System.out.println("AuthenticationManager authenticate");
        String authToken = authentication.getCredentials().toString();
        String username = jwtUtils.getUsernameFromToken(authToken);
        Boolean validate = jwtUtils.validateToken(authToken);
        if (validate) {
            Claims claims = jwtUtils.getAllClaimsFromToken(authToken);
            String role = claims.get("role", String.class);
            List<SimpleGrantedAuthority> roleMap = List.of(new SimpleGrantedAuthority(role));
            return Mono.just(new UsernamePasswordAuthenticationToken(username, null, roleMap));
        }
        return Mono.empty();
    }

}

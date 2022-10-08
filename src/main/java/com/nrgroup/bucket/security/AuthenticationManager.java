package com.nrgroup.bucket.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
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
                    Role role = claims.get("role", Role.class);
                    List<SimpleGrantedAuthority> roleMap = List.of(new SimpleGrantedAuthority(role.name()));
                    return new UsernamePasswordAuthenticationToken(username, null, roleMap);
                });
    }

}

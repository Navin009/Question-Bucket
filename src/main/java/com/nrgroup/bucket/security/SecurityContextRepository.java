package com.nrgroup.bucket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        System.out.println("SecurityContextRepository load");
        HttpCookie cookie = exchange.getRequest().getCookies().getFirst("auth");
        if (cookie != null) {
            String authToken = cookie.getValue();
            Authentication authentication = new UsernamePasswordAuthenticationToken(authToken, authToken);
            return authenticationManager.authenticate(authentication).map(SecurityContextImpl::new);
        }
        return Mono.empty();
    }

}

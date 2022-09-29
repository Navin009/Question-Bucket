package com.nrgroup.bucket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class SecurityAnnotationFilter implements WebFilter {

    @Autowired
    RequestMappingHandlerMapping handlerMapping;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HandlerMethod handlerMethod = (HandlerMethod) this.handlerMapping.getHandler(exchange).share().block();
        
        PreAuthorize classAnnotation = handlerMethod.getMethod().getDeclaringClass().getAnnotation(PreAuthorize.class);
        PreAuthorize methodAnnotation = handlerMethod.getMethodAnnotation(PreAuthorize.class);
        if (classAnnotation != null || methodAnnotation != null) {
            return chain.filter(exchange);
        }
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return Mono.empty();
    }
}

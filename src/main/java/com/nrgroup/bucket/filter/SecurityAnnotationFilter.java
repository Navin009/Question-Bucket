package com.nrgroup.bucket.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityAnnotationFilter implements WebFilter {

    @Autowired
    RequestMappingHandlerMapping handlerMapping;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("SecurityAnnotationFilter filter");
        HandlerMethod handlerMethod = this.handlerMapping.getHandler(exchange)
                .cast(HandlerMethod.class)
                .share().block();

        if (handlerMethod == null)
            return chain.filter(exchange);

        PreAuthorize methodAnnotation = handlerMethod.getMethodAnnotation(PreAuthorize.class);
        PreAuthorize classAnnotation = handlerMethod.getMethod().getDeclaringClass()
                .getAnnotation(PreAuthorize.class);
        if (classAnnotation != null || methodAnnotation != null) {
            return chain.filter(exchange);
        }

        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return Mono.empty();
    }
}

package com.nrgroup.bucket.filter;

import java.util.Map;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class LogIntercepterFilter implements WebFilter {
    static {
        Schedulers.onScheduleHook("mdc", runnable -> {
            Map<String, String> map = MDC.getCopyOfContextMap();
            return () -> {
                if (map != null) {
                    MDC.setContextMap(map);
                }
                try {
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            };
        });
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).doFinally((signal) -> {
            log.info("{} {} - {}", exchange.getRequest().getMethod(), exchange.getRequest().getPath(),
                    exchange.getResponse().getStatusCode());
        });
    }
}

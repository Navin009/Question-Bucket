package com.nrgroup.bucket;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.nrgroup.bucket.config.SecurityRule;

import reactor.core.publisher.Mono;

@Controller
@PreAuthorize(SecurityRule.PERMIT_ALL)
public class MainController {

    @GetMapping("/")
    public Mono<String> index(Model model) {
        return Mono.just("index");
    }

    @GetMapping("/ping")
    public Mono<ServerResponse> ping() {
        return ServerResponse.ok().build();
    }

    @GetMapping("/about")
    public Mono<String> about() {
        return Mono.just("about");
    }

    @GetMapping("/faq")
    public Mono<String> faq() {
        return Mono.just("faq");
    }

    @GetMapping("/contactus")
    public Mono<String> contactus() {
        return Mono.just("contactus");
    }

    @GetMapping("/privacy-policy")
    public Mono<String> privacyPolicy() {
        return Mono.just("privacy-policy");
    }

    @GetMapping("/terms-conditions")
    public Mono<String> termsConditions() {
        return Mono.just("terms-conditions");
    }
}

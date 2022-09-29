package com.nrgroup.bucket.config.robotstxt;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nrgroup.bucket.config.SecurityRule;

@RestController
@PreAuthorize(SecurityRule.PERMIT_ALL)
public class RobotsController {

    @GetMapping(path = "/robots.txt", produces = "text/plain")
    public String robots() {
        String robots = "User-agent: *\n" +
                "Sitemap: https://questionbucket.com/sitemap.xml\n";
        return robots;
    }
}

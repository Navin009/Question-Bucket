package com.nrgroup.bucket.sitemap.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SiteMapMod {

    private String url;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String lastmod;

    public SiteMapMod(String url, Date lastmod) {
        this.url = url;
        this.lastmod = new SimpleDateFormat("yyyy-MM-dd").format(lastmod);
    }

}

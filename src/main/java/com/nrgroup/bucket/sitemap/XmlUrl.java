package com.nrgroup.bucket.sitemap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@XmlRootElement(name = "url")
@XmlAccessorType(value = XmlAccessType.NONE)
public class XmlUrl {

    @XmlElement
    private String loc;

    @XmlElement
    private String lastmod;

    public XmlUrl() {
    }

    public XmlUrl(String loc, String lastmod) {
        this.loc = loc;
        this.lastmod = lastmod;
    }
}
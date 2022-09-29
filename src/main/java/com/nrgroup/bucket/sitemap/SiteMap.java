package com.nrgroup.bucket.sitemap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sitemap")
@XmlAccessorType(value = XmlAccessType.NONE)
public class SiteMap {

    @XmlElement
    private String loc;

    public SiteMap() {
    }

    public SiteMap(String loc) {
        this.loc = loc;
    }

}

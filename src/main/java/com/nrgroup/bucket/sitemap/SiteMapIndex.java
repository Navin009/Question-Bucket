package com.nrgroup.bucket.sitemap;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sitemapindex")
@XmlAccessorType(value = XmlAccessType.NONE)
public class SiteMapIndex {

    @XmlElements({ @XmlElement(name = "sitemap", type = SiteMap.class) })
    private Collection<SiteMap> siteMaps = new ArrayList<SiteMap>();

    public void addSiteMap(SiteMap siteMap) {
        siteMaps.add(siteMap);
    }

    public Collection<SiteMap> getSiteMaps() {
        return siteMaps;
    }
}

package com.nrgroup.bucket.sitemap;

import java.util.List;

import com.nrgroup.bucket.config.SecurityRule;
import com.nrgroup.bucket.config.Constants;
import com.nrgroup.bucket.sitemap.model.SiteMapMod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize(SecurityRule.PERMIT_ALL)
public class SiteMapController {

    @Autowired
    private SiteMapRepository siteMapRepository;

    @GetMapping(value = "/sitemap.xml", produces = "application/xml")
    public SiteMapIndex siteMapIndex() {
        List<String> siteMapLocations = siteMapRepository.getSiteMaps();
        SiteMapIndex siteMapIndex = new SiteMapIndex();
        for (String siteMapLocation : siteMapLocations) {
            siteMapIndex.addSiteMap(new SiteMap(Constants.DOMAIN + siteMapLocation + "/sitemap.xml"));
        }
        return siteMapIndex;
    }

    @GetMapping(value = "/{year}/{month}/sitemap.xml", produces = "application/xml")
    public XmlUrlSet siteMap(@PathVariable("year") String year, @PathVariable(name = "month") String month) {
        String indexString = "/" + year + "/" + month;
        List<SiteMapMod> siteMapModList = siteMapRepository.getSiteUrls(indexString);
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        for (SiteMapMod siteMapMod : siteMapModList) {
            xmlUrlSet.addUrl(new XmlUrl(Constants.DOMAIN + siteMapMod.getUrl(), siteMapMod.getLastmod()));
        }
        return xmlUrlSet;
    }
}
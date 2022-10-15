package com.nrgroup.bucket.sitemap;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SiteMapService {

    @Autowired
    private SiteMapRepository siteMapRepository;

    public Boolean createSiteMapEntry(String url, String siteMapIdentifier) {
        Optional<Integer> siteMapId = siteMapRepository.getSiteMapId(siteMapIdentifier);
        if (siteMapId.isEmpty()) {
            siteMapId = siteMapRepository.createSiteMapIndex(siteMapIdentifier);
            if (siteMapId.isEmpty())
                return false;
        }
        siteMapRepository.insertSiteMapEntry(siteMapId, url);
        return true;
    }
}

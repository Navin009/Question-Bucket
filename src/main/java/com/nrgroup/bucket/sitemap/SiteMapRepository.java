package com.nrgroup.bucket.sitemap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nrgroup.bucket.sitemap.model.SiteMapMod;
import com.nrgroup.bucket.sitemap.model.mapper.SiteMapModMapper;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.result.ResultBearing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class SiteMapRepository {

    @Autowired
    private Jdbi defaultJdbi;

    public List<String> getSiteMaps() {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createQuery("select sitemap_url_identifier from sitemap_index")
                    .mapTo(String.class)
                    .list();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getUrls() {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createQuery("select url from urls").mapTo(String.class).list();
        }
    }

    public List<SiteMapMod> getSiteUrls(String siteMapString) {
        try (Handle handle = defaultJdbi.open()) {
            Optional<Integer> siteMapId = handle
                    .createQuery("select id from sitemap_index where sitemap_url_identifier = :sitemap_url_identifier")
                    .bind("sitemap_url_identifier", siteMapString)
                    .mapTo(Integer.class)
                    .findFirst();

            if (siteMapId.isPresent()) {

                return handle.createQuery("select url,last_mod from sitemap where sitemap_id = :sitemap_id")
                        .bind("sitemap_id", siteMapId)
                        .map(new SiteMapModMapper())
                        .list();
            }
        } catch (Exception e) {
            log.error("Error getting site urls - {} - {} ", e.getClass(), e.getMessage());
        }
        return new ArrayList<>();
    }

    public Optional<Integer> getSiteMapId(String sitemapIdentifier) {
        try (Handle handle = defaultJdbi.open()) {
            return handle
                    .createQuery("select id from sitemap_index where sitemap_url_identifier = :sitemap_url_identifier")
                    .bind("sitemap_url_identifier", sitemapIdentifier)
                    .mapTo(Integer.class)
                    .findFirst();
        } catch (Exception e) {
            log.error("Error getting site Id - {} - {}", e.getClass(), e.getMessage());
        }
        return Optional.empty();
    }

    public Boolean insertSiteMapEntry(Optional<Integer> siteMapId, String url) {
        try (Handle handle = defaultJdbi.open()) {
            handle.createUpdate("insert into sitemap(sitemap_id , url) values(?,?)")
                    .bind(0, siteMapId)
                    .bind(1, url)
                    .execute();
            return true;
        } catch (Exception e) {
            log.error("Error in insertSiteMapEntry - {} - {}", e.getClass(), e.getMessage());
        }
        return false;
    }

    public Optional<Integer> createSiteMapIndex(String siteMapIdentifier) {
        try (Handle handle = defaultJdbi.open()) {
            ResultBearing result = handle
                    .createUpdate("insert into sitemap_index(sitemap_url_identifier) values(:sitemap_url_identifier)")
                    .bind("sitemap_url_identifier", siteMapIdentifier)
                    .executeAndReturnGeneratedKeys("id");
            return result.mapTo(Integer.class).findOne();
        } catch (Exception e) {
            log.error("Error creating SiteMap Index - {} - {}", e.getClass(), e.getMessage());
        }
        return Optional.empty();
    }
}

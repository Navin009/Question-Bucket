package com.nrgroup.bucket.sitemap.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.nrgroup.bucket.sitemap.model.SiteMapMod;

public class SiteMapModMapper implements RowMapper<SiteMapMod> {

    @Override
    public SiteMapMod map(ResultSet rs, final StatementContext ctx) throws SQLException {
        return new SiteMapMod(rs.getString("url"), rs.getDate("last_mod"));
    }

}

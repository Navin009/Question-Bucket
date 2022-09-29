package com.nrgroup.bucket.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.nrgroup.bucket.entity.Subject;

public class SubjectMapper implements RowMapper<Subject> {

    @Override
    public Subject map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Subject(rs.getLong("id"), rs.getString("subject"));
    }

}

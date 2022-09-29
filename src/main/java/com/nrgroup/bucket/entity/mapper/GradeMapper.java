package com.nrgroup.bucket.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.nrgroup.bucket.entity.Grade;

public class GradeMapper implements RowMapper<Grade> {

    @Override
    public Grade map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Grade(rs.getLong("id"), rs.getString("grade"));
    }

}

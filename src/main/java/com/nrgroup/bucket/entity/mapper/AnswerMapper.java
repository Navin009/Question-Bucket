package com.nrgroup.bucket.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.nrgroup.bucket.entity.Answer;

public class AnswerMapper implements RowMapper<Answer> {

    @Override
    public Answer map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Answer(rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("question_id"),
                rs.getString("answer"),
                rs.getDate("created_at"),
                rs.getDate("updated_at"),
                rs.getInt("votes"));
    }

}

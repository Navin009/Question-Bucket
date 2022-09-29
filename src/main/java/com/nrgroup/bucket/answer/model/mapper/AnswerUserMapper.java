package com.nrgroup.bucket.answer.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.nrgroup.bucket.answer.model.AnswerUser;

public class AnswerUserMapper implements RowMapper<AnswerUser> {

    @Override
    public AnswerUser map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new AnswerUser(rs.getLong("id"),
                rs.getLong("question_id"),
                rs.getString("answer"),
                rs.getTimestamp("updated_at"),
                rs.getInt("votes"),
                rs.getLong("user_id"),
                rs.getString("user_first_name"),
                rs.getString("user_last_name"));
    }

}

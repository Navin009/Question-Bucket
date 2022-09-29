package com.nrgroup.bucket.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.nrgroup.bucket.entity.Question;

public class QuestionMapper implements RowMapper<Question> {

    @Override
    public Question map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Question(rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("question"),
                rs.getString("question_title"),
                rs.getDate("created_at"),
                rs.getDate("updated_at"),
                rs.getInt("votes"),
                rs.getInt("no_of_answers"));
    }

}

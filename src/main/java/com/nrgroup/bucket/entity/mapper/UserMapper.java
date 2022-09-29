package com.nrgroup.bucket.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.nrgroup.bucket.entity.User;
import com.nrgroup.bucket.entity.enumeration.Gender;
import com.nrgroup.bucket.entity.enumeration.Role;

public class UserMapper implements RowMapper<User> {

    @Override
    public User map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new User(rs.getLong("id"),
                rs.getString("user_no"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                Gender.index[rs.getInt("gender")],
                rs.getString("mobile_no"),
                rs.getString("password"),
                Role.index[rs.getInt("role")],
                rs.getDate("created_at"),
                rs.getDate("updated_at"),
                rs.getBoolean("deleted"));
    }

}

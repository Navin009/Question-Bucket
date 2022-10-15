package com.nrgroup.bucket.user.repository;

import java.util.Optional;

import com.nrgroup.bucket.entity.User;
import com.nrgroup.bucket.entity.mapper.UserMapper;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.result.ResultBearing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserRepository {

    private static final String CREATE_USER_SQL = "insert into users(first_name, last_name, email, password, role, gender, user_no) values(?,?,?,?,?,?,get_user_no())";
    @Autowired
    private Jdbi defaultJdbi;

    public String createUser(User user) {
        try (Handle handle = defaultJdbi.open()) {
            ResultBearing resultBearing = handle.createUpdate(CREATE_USER_SQL)
                    .bind(0, user.getFirstName())
                    .bind(1, user.getLastName())
                    .bind(2, user.getEmail().toLowerCase().trim())
                    .bind(3, user.getPassword())
                    .bind(4, user.getRole().ordinal())
                    .bind(5, user.getGender().ordinal())
                    .executeAndReturnGeneratedKeys("user_no");
            return resultBearing.mapTo(String.class).one();
        } catch (Exception e) {
            log.error("Exception Registering User - {} - {}", e.getClass(), e.getMessage());
            return null;
        }
    }

    public Optional<User> findByEmail(String email) {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createQuery("select * from users where email = :email")
                    .bind("email", email)
                    .map(new UserMapper())
                    .findFirst();
        } catch (Exception e) {
            log.error("Error in getting User from Eamil : {}", e.getMessage());
        }
        return null;
    }

    public Boolean updateUserPassword(User user) {
        try (Handle handle = defaultJdbi.open()) {
            handle.createUpdate("update users set password = :password where id = :id")
                    .bind("password", user.getPassword())
                    .bind("id", user.getId())
                    .execute();
            return true;
        } catch (Exception e) {
            log.error("Exception in UserRepository updateUserPassword - {} - {}", e.getClass(), e.getMessage());
        }
        return false;
    }

}

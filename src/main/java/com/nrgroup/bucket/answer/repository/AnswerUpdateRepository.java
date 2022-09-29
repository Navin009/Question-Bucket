package com.nrgroup.bucket.answer.repository;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nrgroup.bucket.entity.AnswerUpdate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class AnswerUpdateRepository {

    @Autowired
    private Jdbi defaultJdbi;

    public Long saveAnswerUpdate(AnswerUpdate answerUpdate) {
        try(Handle handle = defaultJdbi.open()) {
            return handle.createUpdate(
                    "insert into answer_updates (answer_id, user_id, updated_answer, created_at, updated_at) values (?,?,?,?,?)")
                    .bind(0, answerUpdate.getAnswerId())
                    .bind(1, answerUpdate.getUserId())
                    .bind(2, answerUpdate.getUpdatedAnswer())
                    .bind(3, answerUpdate.getCreatedAt())
                    .bind(4, answerUpdate.getUpdatedAt())
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(Long.class)
                    .one();
        } catch (Exception e) {
            log.error("Exception in AnswerUpdateRepository saveAnswerUpdate - {} - {}", e.getClass(), e.getMessage());
        }
        return null;
    }

}

package com.nrgroup.bucket.question.repository;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nrgroup.bucket.entity.QuestionUpdate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class QuestionUpdateRepository {

    @Autowired
    private Jdbi defaultJdbi;

    public Long createQuestionUpdate(QuestionUpdate questionUpdate) {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createUpdate(
                    "insert into question_update (question_id, user_id, updated_question, created_at, updated_at) values (?,?,?,?,?)")
                    .bind(0, questionUpdate.getQuestionId())
                    .bind(1, questionUpdate.getUserId())
                    .bind(2, questionUpdate.getUpdatedQuestion())
                    .bind(3, questionUpdate.getCreatedAt())
                    .bind(4, questionUpdate.getUpdatedAt())
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(Long.class)
                    .one();
        } catch (Exception e) {
            log.error("Exception in QuestionUpdateRepository createQuestionUpdate - {} - {}", e.getClass(),
                    e.getMessage());
        }
        return null;
    }

}
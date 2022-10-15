package com.nrgroup.bucket.answer.repository;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nrgroup.bucket.answer.model.AnswerUser;
import com.nrgroup.bucket.answer.model.mapper.AnswerUserMapper;
import com.nrgroup.bucket.entity.Answer;
import com.nrgroup.bucket.entity.mapper.AnswerMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class AnswerRepository {

    private static final String ANSWER_USER_BY_QUESTION_ID_SQL = "select answer.id, question_id, answer, answer.updated_at, answer.votes, user_id, first_name as user_first_name, last_name as user_last_name  from answer left join users on users.id = answer.user_id where question_id = :questionId";

    @Autowired
    private Jdbi defaultJdbi;

    public String getAnswerString(Long answerId) {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createQuery("select answer from answer where id = :id")
                    .bind("id", answerId)
                    .mapTo(String.class)
                    .one();
        } catch (Exception e) {
            log.error("Exception in AnswerRepository getAnswerString - {} - {}", e.getClass(), e.getMessage());
        }
        return null;
    }

    public Long createAnswer(Answer newAnswer) {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createUpdate(
                    "insert into answer (answer, user_id, question_id) values (?,?,?)")
                    .bind(0, newAnswer.getAnswer())
                    .bind(1, newAnswer.getUserId())
                    .bind(2, newAnswer.getQuestionId())
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(Long.class)
                    .one();
        } catch (Exception e) {
            log.error("Exception in AnswerRepository createAnswer - {} - {}", e.getClass(), e.getMessage());
        }
        return null;
    }

    public Optional<Answer> findById(Long answerId) {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createQuery("select * from answer where id = :id")
                    .bind("id", answerId)
                    .map(new AnswerMapper())
                    .findFirst();
        } catch (Exception e) {
            log.error("Exception in AnswerRepository findById for Answer - {} - {}", e.getClass(), e.getMessage());
        }
        return Optional.empty();
    }

    public Boolean deleteAnswer(Answer answer) {
        try (Handle handle = defaultJdbi.open()) {
            handle.createUpdate("delete from answers where id = :id")
                    .bind("id", answer.getId())
                    .execute();
            return true;
        } catch (Exception e) {
            log.error("Exception in AnswerRepository deleteAnswer - {} - {}", e.getClass(), e.getMessage());
        }
        return false;
    }

    public List<AnswerUser> getAnswersByQuestionId(Long questionId) {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createQuery(ANSWER_USER_BY_QUESTION_ID_SQL)
                    .bind("questionId", questionId)
                    .map(new AnswerUserMapper())
                    .list();
        } catch (Exception e) {
            log.error("Exception in AnswerRepository getAnswersByQuestionId - {} - {}", e.getClass(), e.getMessage());
        }
        return null;
    }

    public Boolean editAnswer(Long id, String answer) {
        try (Handle handle = defaultJdbi.open()) {
            handle.createUpdate("update answer set answer = :answer, updated_at = now() where id = :id")
                    .bind("id", id)
                    .bind("answer", answer)
                    .execute();
            return true;
        } catch (Exception e) {
            log.error("Exception in AnswerRepository editAnswer - {} - {}", e.getClass(), e.getMessage());
        }
        return false;
    }

}

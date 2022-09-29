package com.nrgroup.bucket.question.repository;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nrgroup.bucket.config.ActionStatus;
import com.nrgroup.bucket.entity.Grade;
import com.nrgroup.bucket.entity.Question;
import com.nrgroup.bucket.entity.Subject;
import com.nrgroup.bucket.entity.mapper.GradeMapper;
import com.nrgroup.bucket.entity.mapper.QuestionMapper;
import com.nrgroup.bucket.entity.mapper.SubjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class QuestionRepository {

    private static final String FIND_QUESTION_BY_QUERY_SQL = "select * from question  where lower(question) like concat('%',:query,'%')";

    @Autowired
    private Jdbi defaultJdbi;

    public List<Question> findByQuestionContaining(String query) {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createQuery(FIND_QUESTION_BY_QUERY_SQL)
                    .bind("query", query)
                    .map(new QuestionMapper())
                    .list();
        } catch (Exception e) {
            log.error("Exception in QuestionRepository findByQuestionContaining - {} - {}", e.getClass(),
                    e.getMessage());
        }
        return null;
    }

    public List<Question> getTopQuestionByVote() {
        return null;
    }

    public Integer getQuestionAnswersCount(Long questionId) {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createQuery("select no_of_answers from Question where id = :id")
                    .bind("id", questionId)
                    .mapTo(Integer.class)
                    .one();
        } catch (Exception e) {
            log.error("Error while getting question answers count - {} - {}", e.getClass(), e.getMessage());
        }
        return -1;
    };

    public String getQuestionString(Long questionId) {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createQuery("select question from Question where id = :id")
                    .bind("id", questionId)
                    .mapTo(String.class)
                    .one();
        } catch (Exception e) {
            log.error("Exception in QuestionRepository getQuestionString - {} - {}", e.getClass(), e.getMessage());
        }
        return null;
    }

    public Boolean editQuestion(Long id, String question) {
        try (Handle handle = defaultJdbi.open()) {
            handle.createUpdate("update question set question = :question where id = :id")
                    .bind("id", id)
                    .bind("question", question)
                    .execute();
            return true;
        } catch (Exception e) {
            log.error("Exception in QuestionRepository editQuestion - {} - {}", e.getClass(), e.getMessage());
        }
        return null;
    }

    public List<Question> findAll() {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createQuery("select * from Question")
                    .map(new QuestionMapper())
                    .list();
        } catch (Exception e) {
            log.error("Exception in QuestionRepository findAll - {} - {}", e.getClass(), e.getMessage());
        }
        return null;
    }

    public Optional<Question> findById(Long questionId) {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createQuery("select * from question where id = :id")
                    .bind("id", questionId)
                    .map(new QuestionMapper())
                    .findFirst();
        } catch (Exception e) {
            log.error("Exception in QuestionRepository findById Question - {} - {}", e.getClass(), e.getMessage());
        }
        return null;
    }

    public Long saveQuestion(String question, String questionTitle, Integer noOfAnswers, Long userId) {
        try (Handle handle = defaultJdbi.open()) {
            return handle
                    .createUpdate(
                            "insert into question (question, question_title, no_of_answers, user_id) values (?,?,?,?)")
                    .bind(0, question)
                    .bind(1, questionTitle)
                    .bind(2, noOfAnswers)
                    .bind(3, userId)
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(Long.class)
                    .one();
        } catch (Exception e) {
            log.error("Exception in QuestionRepository saveQuestion - {} - {}", e.getClass(), e.getMessage());
        }
        return null;
    }

    public Boolean deleteById(Long questionId) {
        try (Handle handle = defaultJdbi.open()) {
            handle.createUpdate("delete from question where id = :id")
                    .bind("id", questionId)
                    .execute();
        } catch (Exception e) {
            log.error("Exception in QuestionRepository deleteById Question - {} - {}", e.getClass(), e.getMessage());
            return false;
        }
        return true;
    }

    public Boolean editQuestion(Long id, Question question) {
        try (Handle handle = defaultJdbi.open()) {
            handle.createUpdate("update question set question = :question where id = :id")
                    .bind("id", id)
                    .bind("question", question.getQuestion())
                    .execute();
            return true;
        } catch (Exception e) {
            log.error("Exception in QuestionRepository editQuestion - {} - {}", e.getClass(), e.getMessage());
        }
        return false;
    }

    public List<Grade> getGradeList() {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createQuery("select * from grade")
                    .map(new GradeMapper())
                    .list();
        } catch (Exception e) {
            log.error("Error while getting grade list - {} - {}", e.getClass(), e.getMessage());
        }
        return null;
    }

    public List<Subject> getSubjectList() {
        try (Handle handle = defaultJdbi.open()) {
            return handle.createQuery("select * from subject")
                    .map(new SubjectMapper())
                    .list();
        } catch (Exception e) {
            log.error("Error while getting subject list - {} - {}", e.getClass(), e.getMessage());
        }
        return null;
    }

    public ActionStatus updateNoOfAnswers(Long questionId, int noOfAnswers) {
        try (Handle handle = defaultJdbi.open()) {
            int update = handle.createUpdate("update question set no_of_answers = :noOfAnswers where id = :id")
                    .bind("id", questionId)
                    .bind("noOfAnswers", noOfAnswers)
                    .execute();
            if (update == 0) {
                return ActionStatus.FAILURE;
            }
            return ActionStatus.SUCCESS;
        } catch (Exception e) {
            log.error("Error while updating no of answers - {} - {}", e.getClass(), e.getMessage());
            return ActionStatus.ERROR;
        }
    }
}

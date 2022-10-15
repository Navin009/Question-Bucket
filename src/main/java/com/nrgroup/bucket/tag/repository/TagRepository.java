package com.nrgroup.bucket.tag.repository;

import java.util.ArrayList;
import java.util.List;

import com.nrgroup.bucket.entity.Grade;
import com.nrgroup.bucket.entity.Subject;
import com.nrgroup.bucket.question.model.request.IdTagRequest;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.result.ResultBearing;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TagRepository {

    @Autowired
    private Jdbi defaultJdbi;

    public List<IdTagRequest> createTagId(List<IdTagRequest> tags) {
        try (Handle handle = defaultJdbi.open()) {
            List<IdTagRequest> resposne = new ArrayList<>();
            PreparedBatch batch = handle.prepareBatch("insert into tag(tag) values(?)");
            for (IdTagRequest idTagRequest : tags) {
                if (idTagRequest.getId() == null) {
                    batch.bind(0, idTagRequest.getTag()).add();
                } else {
                    resposne.add(idTagRequest);
                }

            }
            ResultBearing result = batch.executePreparedBatch("id", "tag");
            result.mapToBean(IdTagRequest.class).forEach(resposne::add);
            return resposne;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean linkTagWithQuestion(Long questionId, List<IdTagRequest> tags) {
        try (Handle handle = defaultJdbi.open()) {
            PreparedBatch batch = handle.prepareBatch("insert into question_tag(question_id, tag_id) values(?, ?)");
            for (IdTagRequest idTagRequest : tags) {
                batch.bind(0, questionId)
                        .bind(1, idTagRequest.getId())
                        .add();
            }
            return batch.execute().length == tags.size() ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean saveGardeAndSubject(Long questionId, Grade grade, Subject subject) {
        try (Handle handle = defaultJdbi.open()) {
            handle.createUpdate("insert into question_grade(question_id, grade_id) values(?, ?)")
                    .bind(0, questionId)
                    .bind(1, grade.getId())
                    .execute();
            handle.createUpdate("insert into question_subject(question_id, subject_id) values(?, ?)")
                    .bind(0, questionId)
                    .bind(1, subject.getId())
                    .execute();
            return true;
        } catch (Exception e) {
            log.error("Error while saving grade and subject - {} - {}", e.getClass(), e.getMessage());
        }
        return false;
    }

}

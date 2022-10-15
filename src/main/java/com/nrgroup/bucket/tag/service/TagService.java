package com.nrgroup.bucket.tag.service;

import java.util.List;

import com.nrgroup.bucket.entity.Grade;
import com.nrgroup.bucket.entity.Subject;
import com.nrgroup.bucket.question.model.request.IdTagRequest;
import com.nrgroup.bucket.tag.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<IdTagRequest> createTag(Long questionId, List<IdTagRequest> tags) {
        return tagRepository.createTagId(tags);

    }

    public Boolean linkTagWithQuestion(Long questionId, List<IdTagRequest> tags) {
        return tagRepository.linkTagWithQuestion(questionId, tags);
    }

    public Boolean saveGardeAndSubject(Long questionId, Grade grade, Subject subject) {
        return tagRepository.saveGardeAndSubject(questionId, grade, subject);
    }

}

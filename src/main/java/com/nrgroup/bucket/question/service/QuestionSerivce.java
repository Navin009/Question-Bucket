package com.nrgroup.bucket.question.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nrgroup.bucket.entity.Grade;
import com.nrgroup.bucket.entity.Question;
import com.nrgroup.bucket.entity.QuestionUpdate;
import com.nrgroup.bucket.entity.Subject;
import com.nrgroup.bucket.entity.User;
import com.nrgroup.bucket.question.repository.QuestionRepository;
import com.nrgroup.bucket.question.repository.QuestionUpdateRepository;
import com.nrgroup.bucket.utils.SecurityUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class QuestionSerivce {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionUpdateRepository questionUpdateRepository;

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionById(Long questionId) {
        return questionRepository.findById(questionId);
    }

    public Long saveQuestion(String question, String questionTitle) {
        User user = SecurityUtils.getUser();
        if (user == null) {
            log.info("Anonymous User Request Denied!");
            return null;
        }
        return questionRepository.saveQuestion(question, questionTitle, 0, user.getId());
    }

    public List<Question> getTopQuestionsByVote() {
        List<Question> topQuestions = questionRepository.getTopQuestionByVote();
        return topQuestions;
    }

    public Long updateQuestion(Long questionId, String updateQuestion) {
        QuestionUpdate questionUpdate = new QuestionUpdate();
        Question question = questionRepository.findById(questionId).get();
        User user = SecurityUtils.getUser();
        questionUpdate.setUserId(user.getId());
        questionUpdate.setUpdatedQuestion(updateQuestion.toString());
        questionUpdate.setQuestionId(question.getId());
        questionUpdate.setCreatedAt(new Date());
        questionUpdate.setUpdatedAt(new Date());
        return questionUpdateRepository.createQuestionUpdate(questionUpdate);
    }

    public List<Question> getRelatedQuestions(Long questionId) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Question> getTrendingQuestions() {
        // TODO Auto-generated method stub
        return null;
    }

    public Integer getQuestionAnswersCount(Long questionId) {
        return questionRepository.getQuestionAnswersCount(questionId);
    }

    public HttpStatus deleteQuestion(Long questionId) {
        User user = SecurityUtils.getUser();
        Question question = questionRepository.findById(questionId).get();
        if (question == null) {
            return HttpStatus.NOT_FOUND;
        }
        if (question.getUserId() == user.getId()) {
            questionRepository.deleteById(questionId);
            return HttpStatus.OK;
        }
        return HttpStatus.UNAUTHORIZED;
    }

    public String getQuestionString(Long questionId) {
        return questionRepository.getQuestionString(questionId);
    }

    public boolean editQuestion(Long id, String questionString) {
        User user = SecurityUtils.getUser();
        Question question = questionRepository.findById(id).get();
        if (question.getUserId() == user.getId()) {
            question.setQuestion(questionString);
            questionRepository.editQuestion(id, question);
            return true;
        }
        return false;
    }

    public List<Grade> getGradeList() {
        List<Grade> gradeList = questionRepository.getGradeList();
        if (gradeList == null) {
            return new ArrayList<>();
        }
        return gradeList;
    }

    public List<Subject> getSubjectList() {
        List<Subject> subjectList = questionRepository.getSubjectList();
        if (subjectList == null) {
            return new ArrayList<>();
        }
        return subjectList;
    }

}
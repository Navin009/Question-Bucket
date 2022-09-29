package com.nrgroup.bucket.answer.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nrgroup.bucket.answer.model.AnswerUser;
import com.nrgroup.bucket.answer.model.request.AnswerUpdateRequest;
import com.nrgroup.bucket.answer.model.request.CreateAnswerRequest;
import com.nrgroup.bucket.answer.repository.AnswerRepository;
import com.nrgroup.bucket.answer.repository.AnswerUpdateRepository;
import com.nrgroup.bucket.entity.Answer;
import com.nrgroup.bucket.entity.AnswerUpdate;
import com.nrgroup.bucket.entity.User;
import com.nrgroup.bucket.question.repository.QuestionRepository;
import com.nrgroup.bucket.security.SecurityUtils;

@Service
public class AnswerService {

    @Autowired
    private AnswerUpdateRepository answerUpdateRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Long updateAnswer(Long answerId, String updateAnswer) {
        AnswerUpdate answerUpdate = new AnswerUpdate();
        Answer answer = answerRepository.findById(answerId).get();
        User user = SecurityUtils.getUser();
        answerUpdate.setUserId(user.getId());
        answerUpdate.setUpdatedAnswer(updateAnswer);
        answerUpdate.setAnswerId(answer.getId());
        answerUpdate.setCreatedAt(new Date());
        answerUpdate.setUpdatedAt(new Date());
        return answerUpdateRepository.saveAnswerUpdate(answerUpdate);
    }

    public Long createAnswer(CreateAnswerRequest newRequest, Integer questionAnswersCount) {
        Answer newAnswer = new Answer();
        User user = SecurityUtils.getUser();
        newAnswer.setUserId(user.getId());
        newAnswer.setAnswer(newRequest.getAnswer());
        newAnswer.setQuestionId(newRequest.getQuestionId());
        Long answerId = answerRepository.createAnswer(newAnswer);
        if (answerId != null) {
            questionRepository.updateNoOfAnswers(newRequest.getQuestionId(), questionAnswersCount + 1);
        }
        return answerId;
    }

    public HttpStatus deleteAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId).get();
        if (answer == null) {
            return HttpStatus.NOT_FOUND;
        }
        User user = SecurityUtils.getUser();
        if (answer.getUserId().equals(user.getId())) {
            answerRepository.deleteAnswer(answer);
            return HttpStatus.OK;
        }
        return HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus editAnswer(AnswerUpdateRequest editRequest) {
        Answer answer = answerRepository.findById(editRequest.getAnswerId()).get();
        if (answer == null) {
            return HttpStatus.NOT_FOUND;
        }
        User user = SecurityUtils.getUser();
        if (answer.getUserId().equals(user.getId())) {
            answer.setAnswer(editRequest.getAnswer());
            answer.setUpdatedAt(new Date());
            answerRepository.editAnswer(answer.getId(), editRequest.getAnswer());
            return HttpStatus.OK;
        }
        return HttpStatus.UNAUTHORIZED;
    }

    public String getAnswerString(Long answerId) {
        return answerRepository.getAnswerString(answerId);
    }

    public List<AnswerUser> getAnswersByQuestionId(Long questionId) {
        return answerRepository.getAnswersByQuestionId(questionId);
    }

}

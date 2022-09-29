package com.nrgroup.bucket.answer.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nrgroup.bucket.answer.model.request.AnswerUpdateRequest;
import com.nrgroup.bucket.answer.model.request.CreateAnswerRequest;
import com.nrgroup.bucket.answer.service.AnswerService;
import com.nrgroup.bucket.config.SecurityRule;
import com.nrgroup.bucket.exception.ErrorResponse;
import com.nrgroup.bucket.question.service.QuestionSerivce;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@PreAuthorize(SecurityRule.IS_AUTHENTICATED)
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionSerivce questionService;

    @PostMapping("/v1/answer/create")
    public ResponseEntity<?> createAnswer(@RequestBody CreateAnswerRequest newRequest) {
        Integer questionAnswersCount = questionService.getQuestionAnswersCount(newRequest.getQuestionId());
        ErrorResponse errorResponse = new ErrorResponse();
        if (questionAnswersCount == -1) {
            errorResponse.setError("Question not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        if (questionAnswersCount < 3) {
            Long answerId = answerService.createAnswer(newRequest, questionAnswersCount);
            return ResponseEntity.ok(Collections.singletonMap("answerId", answerId));
        } else {
            errorResponse.setError("Question Reached Max Answers");
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
        }
    }

    @PostMapping("/v1/answer/update")
    public ResponseEntity<?> updateAnswer(@RequestBody @Valid AnswerUpdateRequest updateRequest) {
        answerService.updateAnswer(updateRequest.getAnswerId(), updateRequest.getAnswer());
        return ResponseEntity.ok(Collections.singletonMap("answerId", updateRequest.getAnswerId()));
    }

    @PatchMapping("/v1/answer/edit")
    public ResponseEntity<?> editAnswer(@RequestBody @Valid AnswerUpdateRequest editRequest) {
        HttpStatus answerEditStatus = answerService.editAnswer(editRequest);
        return ResponseEntity.status(answerEditStatus).build();
    }

    @DeleteMapping(value = "/v1/answer/delete", params = { "id" })
    public ResponseEntity<?> deleteAnswer(@RequestBody Long answerId) {
        HttpStatus answerDeleteStatus = answerService.deleteAnswer(answerId);
        return ResponseEntity.status(answerDeleteStatus).build();
    }
}

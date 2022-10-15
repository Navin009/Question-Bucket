package com.nrgroup.bucket.answer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nrgroup.bucket.answer.service.AnswerService;
import com.nrgroup.bucket.config.SecurityRule;

@Controller
@PreAuthorize(SecurityRule.AUTHENTICATED)
public class AnswerViewController {

    @Autowired
    private AnswerService answerService;

    @GetMapping("/answer/{answer_id}/edit")
    public String editQuestionAnswer(@PathVariable("answer_id") Long answerId, Model model) {
        String answerString = answerService.getAnswerString(answerId);
        model.addAttribute("answerString", answerString);
        return "edit-answer";
    }

    @GetMapping("/answer/{question_id}/{answer_id}")
    public String getQuestionAnswer(@PathVariable("question_id") Long questionId,
            @PathVariable("answer_id") Long answerId) {
        return "question-answer";
    }
}

package com.nrgroup.bucket.question.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nrgroup.bucket.answer.model.AnswerUser;
import com.nrgroup.bucket.answer.service.AnswerService;
import com.nrgroup.bucket.config.SecurityRule;
import com.nrgroup.bucket.entity.Grade;
import com.nrgroup.bucket.entity.Question;
import com.nrgroup.bucket.entity.Subject;
import com.nrgroup.bucket.question.service.QuestionSerivce;

@Controller
@PreAuthorize(SecurityRule.PERMIT_ALL)
public class QuestionViewController {

    @Autowired
    private QuestionSerivce questionService;

    @Autowired
    private AnswerService answerService;

    @GetMapping("question/{question_id}")
    public String question(@PathVariable("question_id") Long questionId, Model model) {
        Optional<Question> question = questionService.getQuestionById(questionId);
        if (question.isPresent()) {
            return "redirect:/question/" + questionId + "/" + question.get().getQuestionTitle();
        } else {
            return "error";
        }
    }

    @GetMapping("question/{question_id}/{question_title}")
    public String questionWithTitleUrl(@PathVariable("question_id") Long questionId,
            @PathVariable("question_title") String questionTitle, Model model) {
        Optional<Question> question = questionService.getQuestionById(questionId);
        List<AnswerUser> answerUsers = answerService.getAnswersByQuestionId(questionId);
        if (question.isPresent()) {
            List<Question> relatedQuestions = questionService.getRelatedQuestions(questionId);
            model.addAttribute("relatedQuestions", relatedQuestions);
            model.addAttribute("answers", answerUsers);
            model.addAttribute("question", question.get());
            return "question";
        } else {
            return "error";
        }
    }

    @GetMapping("/create-question-answer")
    @PreAuthorize(SecurityRule.IS_AUTHENTICATED)
    public String createQuestion(Model model) {
        List<Grade> grades = questionService.getGradeList();
        List<Subject> subjects = questionService.getSubjectList();
        model.addAttribute("grades", grades);
        model.addAttribute("subjects", subjects);
        return "create-question-answer";
    }

    @GetMapping("/edit-question/{question_id}")
    @PreAuthorize(SecurityRule.IS_AUTHENTICATED)
    public String editQuestion(@PathVariable("question_id") Long questionId, Model model) {
        String questionString = questionService.getQuestionString(questionId);
        model.addAttribute("questionString", questionString);
        return "edit-question";
    }

    @PreAuthorize(SecurityRule.IS_AUTHENTICATED)
    @GetMapping("/question/{question_id}/create-answer")
    public String createQuestionAnswer(@PathVariable("question_id") Long questionId) {
        return "create-answer";
    }
}

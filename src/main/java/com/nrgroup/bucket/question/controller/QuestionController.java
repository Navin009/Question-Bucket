package com.nrgroup.bucket.question.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nrgroup.bucket.answer.model.request.CreateAnswerRequest;
import com.nrgroup.bucket.answer.service.AnswerService;
import com.nrgroup.bucket.config.SecurityRule;
import com.nrgroup.bucket.entity.Question;
import com.nrgroup.bucket.question.model.request.QuestionCreateRequest;
import com.nrgroup.bucket.question.model.request.QuestionUpdateRequest;
import com.nrgroup.bucket.question.service.QuestionSerivce;
import com.nrgroup.bucket.sitemap.SiteMapService;
import com.nrgroup.bucket.tag.service.TagService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@PreAuthorize(SecurityRule.IS_AUTHENTICATED)
public class QuestionController {

    @Autowired
    private QuestionSerivce questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private SiteMapService SiteMapService;

    @Autowired
    private TagService tagService;

    @PreAuthorize(SecurityRule.IS_ADMIN)
    @GetMapping("/v1/question/{question_id}")
    public ResponseEntity<Question> getQuestion(@ModelAttribute("question_id") Long questionId) {
        Optional<Question> question = questionService.getQuestionById(questionId);
        if (question.isPresent()) {
            return ResponseEntity.ok(question.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/v1/question/create")
    public ResponseEntity<?> createQuestion(@RequestBody @Validated QuestionCreateRequest request) {
        boolean validateTitle = validateUpdateTitle(request);
        if (validateTitle == false) {
            return ResponseEntity.badRequest().build();
        }
        Long questionId = questionService.saveQuestion(request.getQuestion(), request.getQuestionTitle());
        tagService.saveGardeAndSubject(questionId, request.getGrade(), request.getSubject());
        if (!request.getAnswer().isEmpty()) {
            CreateAnswerRequest answerRequest = new CreateAnswerRequest();
            answerRequest.setQuestionId(questionId);
            answerRequest.setAnswer(request.getAnswer());
            answerService.createAnswer(answerRequest, 0);
        }
        String siteIndexIdentifier = new SimpleDateFormat("/yyyy/MM").format(new Date());
        SiteMapService.createSiteMapEntry("/question/" + questionId + "/" + request.getQuestionTitle(),
                siteIndexIdentifier);
        return ResponseEntity.ok(Collections.singletonMap("questionId", questionId));
    }

    private boolean validateUpdateTitle(QuestionCreateRequest request) {
        if (request.getQuestionTitle() == null) {
            return false;
        }
        return true;
    }

    // TODO Change url to parameters
    @GetMapping("/topquestions/vote")
    public List<Question> topQuestions(Model model) {
        List<Question> topQuestions = questionService.getTopQuestionsByVote();
        return topQuestions;
    }

    @PostMapping("/v1/question/update")
    public ResponseEntity<?> updateQuestion(@RequestBody @Valid QuestionUpdateRequest updateRequest) {
        questionService.updateQuestion(updateRequest.getId(), updateRequest.getQuestion());
        return ResponseEntity.ok(Collections.singletonMap("questionId", updateRequest.getId()));
    }

    @PatchMapping("/v1/question/edit")
    public ResponseEntity<?> editQuestion(@RequestBody @Valid QuestionUpdateRequest editRequest) {
        boolean isQuestionEdited = questionService.editQuestion(editRequest.getId(), editRequest.getQuestion());
        if (isQuestionEdited) {
            return ResponseEntity.ok(Collections.singletonMap("questionId", editRequest.getId()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // TODO Change path variable to params
    @GetMapping("/relatedquestions/{questionId}")
    public List<Question> getRelatedQuestions(@ModelAttribute("questionId") Long questionId) {
        List<Question> relatedQuestions = questionService.getRelatedQuestions(questionId);
        return relatedQuestions;
    }

    @GetMapping("/v1/question/trending")
    public List<Question> getTrendingQuestions() {
        List<Question> trendingQuestions = questionService.getTrendingQuestions();
        return trendingQuestions;
    }

    @DeleteMapping(value = "/v1/question/delete", params = "questionId")
    public ResponseEntity<?> deleteQuestion(@ModelAttribute("questionId") Long questionId) {
        HttpStatus questionDeleteStatus = questionService.deleteQuestion(questionId);
        return ResponseEntity.status(questionDeleteStatus).build();
    }

}

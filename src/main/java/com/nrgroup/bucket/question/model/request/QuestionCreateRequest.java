package com.nrgroup.bucket.question.model.request;

import java.util.List;

import com.nrgroup.bucket.entity.Grade;
import com.nrgroup.bucket.entity.Subject;
import com.nrgroup.bucket.entity.Tag;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionCreateRequest {
    @NotBlank(message = "Question cannot be empty")
    private String question;
    private String questionTitle;
    private String answer;
    private Grade grade;
    private Subject subject;
    private List<Tag> tags;
}

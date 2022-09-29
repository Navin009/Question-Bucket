package com.nrgroup.bucket.question.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionUpdateRequest {
    @NotNull(message = "Question ID is required")
    private Long id;
    @NotBlank(message = "Question is required")
    private String question;
}

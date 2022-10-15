package com.nrgroup.bucket.answer.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAnswerRequest {
    @NotNull(message = "Answer ID is required")
    private Long questionId;
    @NotBlank(message = "Answer cannot be empty")
    private String answer;
}

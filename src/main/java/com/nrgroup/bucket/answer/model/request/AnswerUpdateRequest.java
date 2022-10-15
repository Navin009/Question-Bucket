package com.nrgroup.bucket.answer.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnswerUpdateRequest {
    @NotNull(message = "Answer ID is required")
    private Long answerId;

    @NotBlank(message = "Answer is required")
    private String answer;
}

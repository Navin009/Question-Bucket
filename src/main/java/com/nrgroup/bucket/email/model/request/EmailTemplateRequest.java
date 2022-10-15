package com.nrgroup.bucket.email.model.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailTemplateRequest {
    @NotBlank(message = "Template Name is required")
    String templateName;
    @NotBlank(message = "Template Subject is required")
    String templateSubject;
    MultipartFile templateHtmlPart;
    MultipartFile templateTextPart;
}

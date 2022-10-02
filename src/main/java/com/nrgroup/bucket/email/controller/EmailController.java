package com.nrgroup.bucket.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nrgroup.bucket.config.SecurityRule;
import com.nrgroup.bucket.email.model.request.EmailTemplateRequest;
import com.nrgroup.bucket.email.model.response.EmailTemplateResponse;
import com.nrgroup.bucket.email.service.EmailService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/v1/email/create-template")
    @PreAuthorize(SecurityRule.ROLE_ADMIN)
    public ResponseEntity<?> createTemplate(@ModelAttribute @Valid EmailTemplateRequest request) {
        EmailTemplateResponse response = emailService.createTemplate(request);
        if (response.getResponseCode() != HttpStatus.OK) {
            return ResponseEntity.status(response.getResponseCode()).body(response.getErrorMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/v1/email/update-template")
    @PreAuthorize(SecurityRule.ROLE_ADMIN)
    public ResponseEntity<?> updateTemplate(@ModelAttribute @Valid EmailTemplateRequest request) {
        EmailTemplateResponse response = emailService.updateTemplate(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/v1/email/delete-template")
    @PreAuthorize(SecurityRule.ROLE_ADMIN)
    public ResponseEntity<?> deleteTemplate(@ModelAttribute("templateName") @NotBlank String deleteTemplateName) {
        EmailTemplateResponse response = emailService.deleteTemplate(deleteTemplateName);
        if (response.getResponseCode() != HttpStatus.OK) {
            return ResponseEntity.status(response.getResponseCode()).body(response.getErrorMessage());
        }
        return ResponseEntity.ok(response.getResponseData());
    }

    @GetMapping("/v1/email/list-template")
    @PreAuthorize(SecurityRule.ROLE_ADMIN)
    public ResponseEntity<?> listTemplates() {
        EmailTemplateResponse response = emailService.listTemplates();
        if (response.getResponseCode() != HttpStatus.OK) {
            return ResponseEntity.status(response.getResponseCode()).body(response.getErrorMessage());
        }
        return ResponseEntity.ok(response.getResponseData());
    }

}

package com.nrgroup.bucket.email.model.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailTemplateResponse {
    private HttpStatus responseCode;
    private String errorMessage;
    private Object responseData;

    public void setResponseCode(int statusCode) {
        try {
            this.responseCode = HttpStatus.valueOf(statusCode);
        } catch (Exception e) {
            this.responseCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    public void setResponseCode(HttpStatus httpStatus) {
        this.responseCode = httpStatus;
    }

}

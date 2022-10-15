package com.nrgroup.bucket.user.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ForgotRequest {
    private String email;
    private String otp;
    private String password;
    private String confirmPassword;
    private String csrf;
}

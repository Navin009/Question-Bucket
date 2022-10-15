package com.nrgroup.bucket.user.model.request;

import com.nrgroup.bucket.user.model.UserRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest implements UserRequest {
    @NotBlank(message = "Username is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}

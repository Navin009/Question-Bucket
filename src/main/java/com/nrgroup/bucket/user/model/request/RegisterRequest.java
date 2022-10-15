package com.nrgroup.bucket.user.model.request;

import com.nrgroup.bucket.entity.enumeration.Gender;
import com.nrgroup.bucket.user.model.UserRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest implements UserRequest {
    @NotBlank(message = "firstName is required")
    @Size(min = 2, max = 26, message = "firstName must be between 2 and 50 characters")
    private String firstName;
    @NotBlank(message = "lastName is required")
    @Size(min = 2, max = 26, message = "lastName must be between 2 and 50 characters")
    private String lastName;
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 8, max = 26, message = "password must be between 8 and 26 characters")
    private String password;
    @NotNull(message = "gender is reqired (M/F)")
    private Gender gender;
    private String csrf;
}

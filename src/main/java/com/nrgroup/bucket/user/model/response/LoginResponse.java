package com.nrgroup.bucket.user.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {
    private String username;
    private String token;
}

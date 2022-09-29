package com.nrgroup.bucket.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDetail {
    private Long id;
    private Long userId;
    private Date dob;
    private String pan;
    private Date createdAt;
    private Date updatedAt;
}

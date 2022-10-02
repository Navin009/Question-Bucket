package com.nrgroup.bucket.entity;

import java.util.Date;

import com.nrgroup.bucket.entity.enumeration.Gender;
import com.nrgroup.bucket.entity.enumeration.Role;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private Long id;
    private String userNo;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private String mobileNo;
    private String password;
    private Role role;
    private Date createdAt;
    private Date updatedAt;
    private Boolean deleted;

    public User(Long id, String userNo, String firstName, String lastName, String email, Gender gender, String mobileNo,
            String password, Role role, Date createdAt, Date updatedAt, Boolean deleted) {
        this.id = id;
        this.userNo = userNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.mobileNo = mobileNo;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deleted = deleted;
    }

    public User() {
    }

    public User(Gender gender) {
        this.gender = gender;
    }

    public User(String userNo, String email, String password,Role role) {
        this.userNo = userNo;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}

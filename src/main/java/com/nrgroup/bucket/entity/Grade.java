package com.nrgroup.bucket.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Grade {
    private Long id;
    private String grade;

    public Grade() {
    }

    public Grade(long id, String grade) {
        this.id = id;
        this.grade = grade;
    }
}

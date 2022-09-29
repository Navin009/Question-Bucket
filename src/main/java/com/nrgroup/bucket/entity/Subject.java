package com.nrgroup.bucket.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Subject {
    private Long id;
    private String subject;

    public Subject(long id, String subject) {
        this.id = id;
        this.subject = subject;
    }
}
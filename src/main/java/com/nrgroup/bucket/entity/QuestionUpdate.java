package com.nrgroup.bucket.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionUpdate {

    private Long id;
    private Long userId;
    private String updatedQuestion;
    private Date createdAt;
    private Date updatedAt;
    private Long questionId;

}

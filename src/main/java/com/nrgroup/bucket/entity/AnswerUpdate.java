package com.nrgroup.bucket.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerUpdate {
    private Long id;
    private Long userId;
    private Long answerId;
    private String updatedAnswer;
    private Date createdAt;
    private Date updatedAt;
    private Integer vote;
}

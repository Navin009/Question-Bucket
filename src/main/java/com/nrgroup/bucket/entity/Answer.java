package com.nrgroup.bucket.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answer {
    private Long id;
    private Long userId;
    private Long questionId;
    private String answer;
    private Date createdAt;
    private Date updatedAt;
    private Integer votes;

    public Answer(Long id, Long userId, Long questionId, String answer, Date createdAt, Date updatedAt, Integer votes) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.answer = answer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.votes = votes;
    }

    public Answer() {
    }
}

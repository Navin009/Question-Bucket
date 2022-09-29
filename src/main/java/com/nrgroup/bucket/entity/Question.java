package com.nrgroup.bucket.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Question {

    private Long id;
    private Long userId;
    private String question;
    private String questionTitle;
    private Date createdAt;
    private Date updatedAt;
    private Integer votes;
    private Integer noOfAnswers;

    public Question(Long id, Long userId, String question, String questionTitle, Date createdAt, Date updatedAt,
            Integer votes,
            Integer noOfAnswers) {
        this.id = id;
        this.userId = userId;
        this.question = question;
        this.questionTitle = questionTitle;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.votes = votes;
        this.noOfAnswers = noOfAnswers;
    }

    public Question() {
    }
}

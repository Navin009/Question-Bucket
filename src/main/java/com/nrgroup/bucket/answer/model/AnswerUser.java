package com.nrgroup.bucket.answer.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AnswerUser {
    private Long id;
    private Long questionId;
    private String answer;
    private Date updatedAt;
    private Integer votes;
    private Long userId;
    private String userFirstName;
    private String userLastName;

    public AnswerUser(Long id, Long questionId, String answer, Date updatedAt, Integer votes, Long userId,
            String userFirstName, String userLastName) {
        this.id = id;
        this.questionId = questionId;
        this.answer = answer;
        this.updatedAt = updatedAt;
        this.votes = votes;
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }
}

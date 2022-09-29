package com.nrgroup.bucket.entity;

import java.util.Date;

import com.nrgroup.bucket.entity.enumeration.ConsentType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserConsent {
    private Long id;
    private Long userId;
    private ConsentType consentType;
    private Boolean consent;
    private Date createdAt;
    private Date updatedAt;
}

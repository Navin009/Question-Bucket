package com.nrgroup.bucket.entity;

import java.util.Date;

import com.nrgroup.bucket.entity.enumeration.ComplaintCategory;
import com.nrgroup.bucket.entity.enumeration.ComplaintSubCategory;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserComplaint {
    private Long id;
    private Long userId;
    private ComplaintCategory complaintCategory;
    private ComplaintSubCategory complaintSubCategory;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}

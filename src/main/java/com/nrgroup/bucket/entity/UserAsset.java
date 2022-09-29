package com.nrgroup.bucket.entity;

import java.util.Date;

import com.nrgroup.bucket.entity.enumeration.AssetType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserAsset {
    private Long id;
    private Long userId;
    private AssetType assetType;
    private String url;
    private Date createdAt;
    private Date updatedAt;
}
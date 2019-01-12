package com.example.wwq.DO;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserScoreListDO {

    private String userId;

    private String integralDetail;

    private String productId;

    private String productName;

    private String wayType;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String createDate;

    public String getIntegralDetail() {
        return integralDetail;
    }

    public void setIntegralDetail(String integralDetail) {
        this.integralDetail = integralDetail;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWayType() {
        return wayType;
    }

    public void setWayType(String wayType) {
        this.wayType = wayType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

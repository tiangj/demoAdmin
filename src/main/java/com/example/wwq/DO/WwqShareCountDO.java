package com.example.wwq.DO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class WwqShareCountDO {

    private String shareId;

    private String userId;

    private String openId;

    private String userName;

    private String userLevel;

    private String firstShareNum;

    private String secondShareNum;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDate;

    private String remark;

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getFirstShareNum() {
        return firstShareNum;
    }

    public void setFirstShareNum(String firstShareNum) {
        this.firstShareNum = firstShareNum;
    }

    public String getSecondShareNum() {
        return secondShareNum;
    }

    public void setSecondShareNum(String secondShareNum) {
        this.secondShareNum = secondShareNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}

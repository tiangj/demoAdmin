package com.example.wwq.DO;

import com.example.wwq.entity.WwqProduct;

public class ProductAddDO extends WwqProduct {

    private String detailId;

    private String postWayId;

    private String deliveryAreaId;

    private String detailRemark;

    private String postDateId;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getPostWayId() {
        return postWayId;
    }

    public void setPostWayId(String postWayId) {
        this.postWayId = postWayId;
    }

    public String getDeliveryAreaId() {
        return deliveryAreaId;
    }

    public void setDeliveryAreaId(String deliveryAreaId) {
        this.deliveryAreaId = deliveryAreaId;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }

    public String getPostDateId() {
        return postDateId;
    }

    public void setPostDateId(String postDateId) {
        this.postDateId = postDateId;
    }
}

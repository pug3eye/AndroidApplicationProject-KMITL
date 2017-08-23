package com.example.android.plm.model;

public class History {

    private int id;
    private int memberID;
    private int point;
    private boolean isAdd;
    private String detail;
    private String createdAt;

    public History(int id, int memberID, int point, boolean isAdd, String detail, String createdAt) {
        this.id = id;
        this.memberID = memberID;
        this.point = point;
        this.isAdd = isAdd;
        this.detail = detail;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setIsAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
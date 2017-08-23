package com.example.android.plm.model;

import java.io.Serializable;

public class Reward implements Serializable {

    private int id;
    private String barcode;
    private String name;
    private int point_use;
    private String detail;
    private String imageURL;

    public Reward() {
        this.id = 0;
        this.barcode = null;
        this.name = null;
        this.point_use = 0;
        this.detail = null;
        this.imageURL = null;
    }

    public Reward(int id, String barcode, String name,
                  int point_use, String detail, String imageURL) {
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.point_use = point_use;
        this.detail = detail;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPointUse() {
        return point_use;
    }

    public void setPointUse(int point_use) {
        this.point_use = point_use;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}

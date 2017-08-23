package com.example.android.plm.model;

public class Shop {

    private int id;
    private String name;
    private String owner;
    private String logoUrl;

    public Shop() {
        this.id = 0;
        this.name = null;
        this.owner = null;
        this.logoUrl = null;
    }

    public Shop (int id, String name, String logoUrl) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
        this.owner = null;
    }

    public Shop (int id, String name, String owner, String logoUrl) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.logoUrl = logoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

}

package com.gae.dto;

import org.apache.ibatis.type.Alias;

@Alias("ph")
public class PHDTO {
    private int phId;
    private String phRegion;
    private String phAddress;
    private String phName;
    private String phTel;
    private String phHour;
    private double phLat; // 위도
    private double phLng; // 경도

    public PHDTO() { }

    public PHDTO(int phId, String phRegion, String phAddress, String phName, String phTel, String phHour, double phLat, double phLng) {
        super();
        this.phId = phId;
        this.phRegion = phRegion;
        this.phAddress = phAddress;
        this.phName = phName;
        this.phTel = phTel;
        this.phHour = phHour;
        this.phLat = phLat;
        this.phLng = phLng;
    }

    // Getters and Setters

    public int getPhId() {
        return phId;
    }

    public void setPhId(int phId) {
        this.phId = phId;
    }

    public String getPhRegion() {
        return phRegion;
    }

    public void setPhRegion(String phRegion) {
        this.phRegion = phRegion;
    }

    public String getPhAddress() {
        return phAddress;
    }

    public void setPhAddress(String phAddress) {
        this.phAddress = phAddress;
    }

    public String getPhName() {
        return phName;
    }

    public void setPhName(String phName) {
        this.phName = phName;
    }

    public String getPhTel() {
        return phTel;
    }

    public void setPhTel(String phTel) {
        this.phTel = phTel;
    }

    public String getPhHour() {
        return phHour;
    }

    public void setPhHour(String phHour) {
        this.phHour = phHour;
    }

    public double getPhLat() {
        return phLat;
    }

    public void setPhLat(double phLat) {
        this.phLat = phLat;
    }

    public double getPhLng() {
        return phLng;
    }

    public void setPhLng(double phLng) {
        this.phLng = phLng;
    }
}

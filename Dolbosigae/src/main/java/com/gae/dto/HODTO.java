package com.gae.dto;

import org.apache.ibatis.type.Alias;

@Alias("ho")
public class HODTO {
    private int hoId;
    private String hoRegion;
    private String hoAddress;
    private String hoName;
    private String hoTel;
    private String hoPost;
    private double hoLat; // 추가: 위도
    private double hoLng; // 추가: 경도

    public HODTO() { }

    public HODTO(int hoId, String hoRegion, String hoAddress, String hoName, String hoTel, String hoPost, double hoLat, double hoLng) {
        super();
        this.hoId = hoId;
        this.hoRegion = hoRegion;
        this.hoAddress = hoAddress;
        this.hoName = hoName;
        this.hoTel = hoTel;
        this.hoPost = hoPost;
        this.hoLat = hoLat;
        this.hoLng = hoLng;
    }

    // Getters and Setters

    public int getHoId() {
        return hoId;
    }

    public void setHoId(int hoId) {
        this.hoId = hoId;
    }

    public String getHoRegion() {
        return hoRegion;
    }

    public void setHoRegion(String hoRegion) {
        this.hoRegion = hoRegion;
    }

    public String getHoAddress() {
        return hoAddress;
    }

    public void setHoAddress(String hoAddress) {
        this.hoAddress = hoAddress;
    }

    public String getHoName() {
        return hoName;
    }

    public void setHoName(String hoName) {
        this.hoName = hoName;
    }

    public String getHoTel() {
        return hoTel;
    }

    public void setHoTel(String hoTel) {
        this.hoTel = hoTel;
    }

    public String getHoPost() {
        return hoPost;
    }

    public void setHoPost(String hoPost) {
        this.hoPost = hoPost;
    }

    public double getHoLat() {
        return hoLat;
    }

    public void setHoLat(double hoLat) {
        this.hoLat = hoLat;
    }

    public double getHoLng() {
        return hoLng;
    }

    public void setHoLng(double hoLng) {
        this.hoLng = hoLng;
    }
}

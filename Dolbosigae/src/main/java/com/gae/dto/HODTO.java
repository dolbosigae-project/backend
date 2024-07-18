package com.gae.dto;

import org.apache.ibatis.type.Alias;

@Alias("ho")
public class HODTO {
    private String hoId;
    private String hoRegion;
    private String hoAddress;
    private String hoName;
    private String hoTel;
    private String hoPost;

    // Getters and Setters

    public String getHoId() {
        return hoId;
    }

    public void setHoId(String hoId) {
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
}

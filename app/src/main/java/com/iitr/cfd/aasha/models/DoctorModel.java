package com.iitr.cfd.aasha.models;

public class DoctorModel {

    private int id;
    private long uid;
    private String name;
    private String password;
    private String image;
    private String details;
    private String phone;
    private String address;

    public DoctorModel(int id, int uid, String name, String password, String image,
                       String details, String phone, String address) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.image = image;
        this.details = details;
        this.phone = phone;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

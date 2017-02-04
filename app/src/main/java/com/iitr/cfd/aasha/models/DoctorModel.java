package com.iitr.cfd.aasha.models;

public class DoctorModel {

    private int id;
    private int uid;
    private String name;
    private String encryptedPassword;
    private String photo;
    private String details;
    private String phone;
    private String address;

    public DoctorModel(int id, int uid, String name, String encryptedPassword, String photo,
                       String details, String phone, String address) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.encryptedPassword = encryptedPassword;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

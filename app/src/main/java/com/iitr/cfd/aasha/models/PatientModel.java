package com.iitr.cfd.aasha.models;

import com.google.gson.annotations.SerializedName;

public class PatientModel {

    private int id;
    private long uid;
    private String name;
    private String password;
    private String photo;
    private String address;
    private String phone;

    @SerializedName("pregnant")
    private boolean isPregnant;

    @SerializedName("duedate")
    private String dueDate;

    @SerializedName("conceivedate")
    private String conceiveDate;

    @SerializedName("hospital_id")
    private int hospitalId;

    public PatientModel(int id, int uid, String name, String password, String photo,
                        String address, String phone, boolean isPregnant, String dueDate,
                        String conceiveDate, int hospitalId) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.photo = photo;
        this.address = address;
        this.phone = phone;
        this.isPregnant = isPregnant;
        this.dueDate = dueDate;
        this.conceiveDate = conceiveDate;
        this.hospitalId = hospitalId;
    }

    public int getId() {
        return id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getConceiveDate() {
        return conceiveDate;
    }

    public void setConceiveDate(String conceiveDate) {
        this.conceiveDate = conceiveDate;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }
}

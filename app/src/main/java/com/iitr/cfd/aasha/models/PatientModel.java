package com.iitr.cfd.aasha.models;

public class PatientModel {

    private int id;
    private int uid;
    private String name;
    private String encryptedPassword;
    private String photo;
    private String address;
    private String phone;
    private boolean isPregnant;
    private String dueDate;
    private String conceiveDate;

    public PatientModel(int id, int uid, String name, String encryptedPassword, String photo,
                        String address, String phone, boolean isPregnant, String dueDate,
                        String conceiveDate) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.encryptedPassword = encryptedPassword;
        this.photo = photo;
        this.address = address;
        this.phone = phone;
        this.isPregnant = isPregnant;
        this.dueDate = dueDate;
        this.conceiveDate = conceiveDate;
    }

    public int getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
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
}

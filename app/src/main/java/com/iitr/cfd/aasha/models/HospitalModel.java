package com.iitr.cfd.aasha.models;

public class HospitalModel {

    private int id;
    private String name;
    private String address;
    private String contact;
    private double latitude;
    private double longitude;

    public HospitalModel(int id, String name, String address, String phoneNumber, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

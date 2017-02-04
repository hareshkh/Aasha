package com.iitr.cfd.aasha.models;

public class HospitalModel {

    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private double gpsX;
    private double gpsY;

    public HospitalModel(int id, String name, String address, String phoneNumber, double gpsX, double gpsY) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getGpsX() {
        return gpsX;
    }

    public void setGpsX(double gpsX) {
        this.gpsX = gpsX;
    }

    public double getGpsY() {
        return gpsY;
    }

    public void setGpsY(double gpsY) {
        this.gpsY = gpsY;
    }
}

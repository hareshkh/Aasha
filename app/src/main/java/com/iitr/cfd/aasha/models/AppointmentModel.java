package com.iitr.cfd.aasha.models;

public class AppointmentModel {

    private int id;
    private int patientId;
    private int hospitalId;
    private int doctorId;
    private String timestamp;
    private String description;
    private String status;

    public AppointmentModel(int id, int patientId, int hospitalId, int doctorId, String timestamp,
                            String description, String status) {
        this.id = id;
        this.patientId = patientId;
        this.hospitalId = hospitalId;
        this.doctorId = doctorId;
        this.timestamp = timestamp; // to be parsed into java Date later
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

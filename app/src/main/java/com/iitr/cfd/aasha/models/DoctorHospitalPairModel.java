package com.iitr.cfd.aasha.models;

/**
 * Created by Harjot on 07-Feb-17.
 */

public class DoctorHospitalPairModel {
    DoctorModel doctorModel;
    HospitalModel hospitalModel;

    public DoctorHospitalPairModel(DoctorModel doctorModel, HospitalModel hospitalModel) {
        this.doctorModel = doctorModel;
        this.hospitalModel = hospitalModel;
    }

    public DoctorModel getDoctorModel() {
        return doctorModel;
    }

    public void setDoctorModel(DoctorModel doctorModel) {
        this.doctorModel = doctorModel;
    }

    public HospitalModel getHospitalModel() {
        return hospitalModel;
    }

    public void setHospitalModel(HospitalModel hospitalModel) {
        this.hospitalModel = hospitalModel;
    }
}

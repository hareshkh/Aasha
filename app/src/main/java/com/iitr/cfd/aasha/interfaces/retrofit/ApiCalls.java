package com.iitr.cfd.aasha.interfaces.retrofit;

import com.iitr.cfd.aasha.models.AppointmentModel;
import com.iitr.cfd.aasha.models.DoctorModel;
import com.iitr.cfd.aasha.models.HospitalModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiCalls {

    String BASE_URL = "http://0c884a4e.ngrok.io/";

    @GET("db/hospitals")
    Call<List<HospitalModel>> getHospitals();

    @GET("db/appointments")
    Call<List<AppointmentModel>> getAppointments();

    @GET("db/doctors")
    Call<List<DoctorModel>> getDoctors();

    class Factory {
        public static ApiCalls service;

        public static ApiCalls getInstance() {
            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.
                        create()).baseUrl(BASE_URL).build();
                service = retrofit.create(ApiCalls.class);
            }
            return service;
        }
    }
}

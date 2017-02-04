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

    String BASE_URL = "https://xxx.xxx.com";

    @GET("hospitals")
    Call<List<HospitalModel>> getHospitals();

    @GET("appointments")
    Call<List<AppointmentModel>> getAppointments();

    @GET("doctors")
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

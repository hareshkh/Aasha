package com.iitr.cfd.aasha.interfaces.retrofit;

import com.iitr.cfd.aasha.models.AppointmentModel;
import com.iitr.cfd.aasha.models.DoctorModel;
import com.iitr.cfd.aasha.models.HospitalModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiCalls {

    String BASE_URL = "http://0c884a4e.ngrok.io/db/";

    @GET("hospitals")
    Call<List<HospitalModel>> getHospitals();

    @GET("appointments")
    Call<List<AppointmentModel>> getAppointments();

    @GET("doctors")
    Call<List<DoctorModel>> getDoctors();

    @FormUrlEncoded
    @POST("login")
    Call<Integer> getPatientId(@Field("uid") long uid, @Field("password") String password);

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

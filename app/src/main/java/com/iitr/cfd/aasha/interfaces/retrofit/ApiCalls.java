package com.iitr.cfd.aasha.interfaces.retrofit;

import com.iitr.cfd.aasha.models.AppointmentModel;
import com.iitr.cfd.aasha.models.DoctorModel;
import com.iitr.cfd.aasha.models.HospitalModel;
import com.iitr.cfd.aasha.models.PatientModel;
import com.iitr.cfd.aasha.models.VisitingDoctorModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiCalls {

    String BASE_URL = "http://04aee59d.ngrok.io/db/";

    @GET("hospitals")
    Call<List<HospitalModel>> getHospitals();

    @GET("appointments")
    Call<List<AppointmentModel>> getAppointments();

    @GET("doctors")
    Call<List<DoctorModel>> getDoctors();

    @GET("visits")
    Call<List<VisitingDoctorModel>> getVisits();

    @FormUrlEncoded
    @POST("login")
    Call<Integer> loginRequest(@Field("uid") long uid, @Field("password") String password);

    @FormUrlEncoded
    @POST("patients")
    Call<PatientModel> signupRequest(@Field("name") String name,
                                     @Field("uid") long uid,
                                     @Field("password") String password,
                                     @Field("image") String image,
                                     @Field("address") String address,
                                     @Field("phone") String phone,
                                     @Field("pregnant") int isPregnant,
                                     @Field("duedate") String dueDate,
                                     @Field("conceivedate") String conceiveDate);

    @FormUrlEncoded
    @PATCH("patients/{patient_id}")
    Call<PatientModel> setHospitalId(@Path("patient_id") int patient_id, @Field("hospital_id") int hospitalID);

    @FormUrlEncoded
    @POST("appointments")
    Call<AppointmentModel> appointmentBookRequest(@Field("patient_id") int patientId,
                                                  @Field("hospital_id") int hospitalId,
                                                  @Field("doctor_id") int doctorId,
                                                  @Field("time") String time,
                                                  @Field("description") String description,
                                                  @Field("status") String status);

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

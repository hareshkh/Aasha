package com.iitr.cfd.aasha.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iitr.cfd.aasha.interfaces.sms.SmsCallback;
import com.iitr.cfd.aasha.models.AppointmentModel;
import com.iitr.cfd.aasha.models.DoctorModel;
import com.iitr.cfd.aasha.models.HospitalModel;
import com.iitr.cfd.aasha.models.PatientModel;
import com.iitr.cfd.aasha.models.VisitingDoctorModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmsHandler extends BroadcastReceiver {

    static final String TAG = "SMS_Handler";
    static final String SERVER_ADDRESS = "+919463623475";

    static SmsCallback<List<HospitalModel>> hospitalCallback;
    static SmsCallback<List<AppointmentModel>> appointmentsCallback;
    static SmsCallback<List<DoctorModel>> doctorsCallback;
    static SmsCallback<List<VisitingDoctorModel>> visitsCallback;
    static SmsCallback<Integer> loginCallback;
    static SmsCallback<PatientModel> signupCallback;
    static SmsCallback<PatientModel> setHospitalIdCallback;
    static SmsCallback<AppointmentModel> bookAppointmentCallback;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Message recieved");
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdus = (Object[]) bundle.get("pdus");
                assert pdus != null;
                String finalResponse = "";
                for (Object aPdusObj : pdus) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);

                    String senderNum = currentMessage.getDisplayOriginatingAddress();
                    finalResponse = finalResponse.concat(currentMessage.getDisplayMessageBody());
                }

                List<String> items = Arrays.asList(finalResponse.split("\\s*,\\s*"));
                int responseCode = Integer.parseInt(items.get(0));
                switch (responseCode) {
                    case 1: {
                        String decoded = new String(Base64.decode(items.get(1), Base64.DEFAULT));
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<HospitalModel>>() {
                        }.getType();
                        List<HospitalModel> hospitalModels = gson.fromJson(decoded, type);
                        hospitalCallback.onReceive(hospitalModels);
                        break;
                    }
                    case 2: {
                        String decoded = new String(Base64.decode(items.get(1), Base64.DEFAULT));
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<AppointmentModel>>() {
                        }.getType();
                        List<AppointmentModel> appointmentModels = gson.fromJson(decoded, type);
                        appointmentsCallback.onReceive(appointmentModels);
                        break;
                    }
                    case 3: {
                        String decoded = new String(Base64.decode(items.get(1), Base64.DEFAULT));
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<DoctorModel>>() {
                        }.getType();
                        List<DoctorModel> doctorModels = gson.fromJson(decoded, type);
                        doctorsCallback.onReceive(doctorModels);
                        break;
                    }
                    case 4: {
                        String decoded = new String(Base64.decode(items.get(1), Base64.DEFAULT));
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<VisitingDoctorModel>>() {
                        }.getType();
                        List<VisitingDoctorModel> visitingDoctorModels = gson.fromJson(decoded, type);
                        visitsCallback.onReceive(visitingDoctorModels);
                        break;
                    }
                    case 5: {
                        String decoded = new String(Base64.decode(items.get(1), Base64.DEFAULT));
                        loginCallback.onReceive(Integer.parseInt(decoded));
                        break;
                    }
                    case 6: {
                        String decoded = new String(Base64.decode(items.get(1), Base64.DEFAULT));
                        Gson gson = new Gson();
                        Type type = new TypeToken<PatientModel>() {
                        }.getType();
                        PatientModel patientModel = gson.fromJson(decoded, type);
                        signupCallback.onReceive(patientModel);
                        break;
                    }
                    case 7: {
                        String decoded = new String(Base64.decode(items.get(1), Base64.DEFAULT));
                        Gson gson = new Gson();
                        Type type = new TypeToken<PatientModel>() {
                        }.getType();
                        PatientModel patientModel = gson.fromJson(decoded, type);
                        setHospitalIdCallback.onReceive(patientModel);
                        break;
                    }
                    case 8: {
                        String decoded = new String(Base64.decode(items.get(1), Base64.DEFAULT));
                        Gson gson = new Gson();
                        Type type = new TypeToken<AppointmentModel>() {
                        }.getType();
                        AppointmentModel appointmentModel = gson.fromJson(decoded, type);
                        bookAppointmentCallback.onReceive(appointmentModel);
                        break;
                    }
                }

            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e.getMessage());
        }
    }

    public static void sendSms(String msg, String phoneNo) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Log.d(TAG, "Sms Sent");
        } catch (Exception e) {
            Log.d(TAG, "Sms Not Sent");
        }
    }

    public static void getHospitals(SmsCallback<List<HospitalModel>> callback) {
        hospitalCallback = callback;
        List<String> parameters = new ArrayList<>();
        parameters.add("1");
        sendSms(TextUtils.join(",", parameters), SERVER_ADDRESS);
    }

    public static void getAppointments(int patientId, SmsCallback<List<AppointmentModel>> callback) {
        appointmentsCallback = callback;
        List<String> parameters = new ArrayList<>();
        parameters.add("2");
        parameters.add(String.valueOf(patientId));
        sendSms(TextUtils.join(",", parameters), SERVER_ADDRESS);
    }

    public static void getDoctors(SmsCallback<List<DoctorModel>> callback) {
        doctorsCallback = callback;
        List<String> parameters = new ArrayList<>();
        parameters.add("3");
        sendSms(TextUtils.join(",", parameters), SERVER_ADDRESS);
    }

    public static void getVisits(SmsCallback<List<VisitingDoctorModel>> callback) {
        visitsCallback = callback;
        List<String> parameters = new ArrayList<>();
        parameters.add("4");
        sendSms(TextUtils.join(",", parameters), SERVER_ADDRESS);
    }

    public static void loginRequest(long uid, String password, SmsCallback<Integer> callback) {
        loginCallback = callback;
        List<String> parameters = new ArrayList<>();
        parameters.add("5");
        parameters.add(String.valueOf(uid));
        parameters.add(password);
        sendSms(TextUtils.join(",", parameters), SERVER_ADDRESS);
    }

    public static void signupRequest(String name, long uid, String password, String image, String address, String phone, int isPregnant, String dueDate, String conceiveDate, SmsCallback<PatientModel> callback) {
        signupCallback = callback;
        List<String> parameters = new ArrayList<>();
        parameters.add("6");
        parameters.add(name);
        parameters.add(String.valueOf(uid));
        parameters.add(password);
        parameters.add(image);
        parameters.add(address);
        parameters.add(phone);
        parameters.add(String.valueOf(isPregnant));
        parameters.add(dueDate);
        parameters.add(conceiveDate);
        sendSms(TextUtils.join(",", parameters), SERVER_ADDRESS);
    }

    public static void setHospitalId(int patient_id, int hospitalID, SmsCallback<PatientModel> callback) {
        setHospitalIdCallback = callback;
        List<String> parameters = new ArrayList<>();
        parameters.add("7");
        parameters.add(String.valueOf(patient_id));
        parameters.add(String.valueOf(hospitalID));
        sendSms(TextUtils.join(",", parameters), SERVER_ADDRESS);
    }

    public static void appointmentBookRequest(int patientId, int hospitalId, int doctorId, String time, String description, String status, SmsCallback<AppointmentModel> callback) {
        bookAppointmentCallback = callback;
        List<String> parameters = new ArrayList<>();
        parameters.add("8");
        parameters.add(String.valueOf(patientId));
        parameters.add(String.valueOf(hospitalId));
        parameters.add(String.valueOf(doctorId));
        parameters.add(time);
        parameters.add(description);
        parameters.add(status);
        sendSms(TextUtils.join(",", parameters), SERVER_ADDRESS);
    }

}

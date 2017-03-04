package com.iitr.cfd.aasha.fragments;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.activities.HomeActivity;
import com.iitr.cfd.aasha.activities.LoginActivity;
import com.iitr.cfd.aasha.interfaces.retrofit.ApiCalls;
import com.iitr.cfd.aasha.interfaces.sms.SmsCallback;
import com.iitr.cfd.aasha.models.AppointmentModel;
import com.iitr.cfd.aasha.models.DoctorModel;
import com.iitr.cfd.aasha.models.HospitalModel;
import com.iitr.cfd.aasha.utilities.NetworkUtils;
import com.iitr.cfd.aasha.utilities.SmsHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointmentFragment extends Fragment {

    TextView doctorName;
    TextView hospitalName;
    TextView appointmentDate;
    TextView appointmentTime;
    EditText appointmentDescription;

    Button bookAppointmentButton;

    String date;
    String timeFinal;

    DoctorModel doctor;
    HospitalModel hospital;

    String appointmentDateString, appointmentTimeString, appointmentDescriptionString;
    int patientId, hospitalId, doctorId; // To be set from data received in Bundle

    Calendar calendar;

    ProgressDialog progressDialog;

    public BookAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        patientId = LoginActivity.PATIENT_ID;
        doctorId = getArguments().getInt("doctor_id");
        hospitalId = getArguments().getInt("hospital_id");

        for (DoctorModel doctorModel : HomeActivity.doctors) {
            if (doctorModel.getId() == doctorId) {
                doctor = doctorModel;
                break;
            }
        }

        for (HospitalModel hospitalModel : HomeActivity.hospitals) {
            if (hospitalModel.getId() == doctorId) {
                hospital = hospitalModel;
                break;
            }
        }

        date = getArguments().getString("time");
        return inflater.inflate(R.layout.fragment_book_appointment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        doctorName = (TextView) view.findViewById(R.id.doctor_name_appointment);
        doctorName.setText(doctorName.getText() + "\n" + doctor.getName());

        hospitalName = (TextView) view.findViewById(R.id.hospital_name_appointment);
        hospitalName.setText(hospitalName.getText() + "\n" + hospital.getName());

        appointmentDate = (TextView) view.findViewById(R.id.date_appointment);
        appointmentDate.setText(getString(R.string.hint_date_appointment) + "\n" + date);

        appointmentTime = (TextView) view.findViewById(R.id.time_appointment);

        appointmentDescription = (EditText) view.findViewById(R.id.input_description_appointment);

        bookAppointmentButton = (Button) view.findViewById(R.id.button_book_appointment);

        calendar = Calendar.getInstance();
        final TimePickerDialog.OnTimeSetListener appointmentTimeDialog = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                String format = "hh:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                timeFinal = sdf.format(calendar.getTime());
                appointmentTime.setText(getString(R.string.hint_time_appointment) + "\n" + timeFinal);
            }
        };

        appointmentTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(), appointmentTimeDialog,
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });

        bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extractData();
                if (validate()) {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Booking your appointment");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    if(NetworkUtils.isNetworkAvailable(getContext())) {
                        ApiCalls.Factory.getInstance().appointmentBookRequest(patientId, hospitalId,
                                doctorId, appointmentDateString.concat(" " + appointmentTimeString),
                                appointmentDescriptionString, "Pending").enqueue(new Callback<AppointmentModel>() {
                            @Override
                            public void onResponse(Call<AppointmentModel> call, Response<AppointmentModel> response) {
                                progressDialog.dismiss();
                                HomeActivity.appointments.add(response.body());
                                ((HomeActivity) getActivity()).updateAppointments();
                                Toast.makeText(getContext(), "Appointment booked", Toast.LENGTH_SHORT).show();
                                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            }

                            @Override
                            public void onFailure(Call<AppointmentModel> call, Throwable t) {
                                progressDialog.dismiss();
                                Log.d("RETRO", t.getMessage());
                                Toast.makeText(getContext(), "Try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        progressDialog.setMessage("Booking your appointment through SMS");
                        SmsHandler.appointmentBookRequest(patientId, hospitalId,
                                doctorId, appointmentDateString.concat(" " + appointmentTimeString),
                                appointmentDescriptionString, "Pending", new SmsCallback<AppointmentModel>() {
                                    @Override
                                    public void onReceive(AppointmentModel appointmentModel) {
                                        progressDialog.dismiss();
                                        HomeActivity.appointments.add(appointmentModel);
                                        ((HomeActivity) getActivity()).updateAppointments();
                                        Toast.makeText(getContext(), "Appointment booked", Toast.LENGTH_SHORT).show();
                                        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    }
                                });
                    }
                } else {
                    Toast.makeText(getContext(), "Fill the form completely", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void extractData() {
        appointmentDateString = date;
        appointmentTimeString = timeFinal;
        appointmentDescriptionString = appointmentDescription.getText().toString();
    }

    public boolean validate() {
        boolean formStatus = true;
        if (appointmentDescriptionString.equals("")) {
            formStatus = false;
            appointmentDescription.setError("Enter a longer description");
        }
        if (appointmentDate.getText().toString().equals(getString(R.string.hint_date_appointment))) {
            formStatus = false;
            appointmentDate.setError("Select a date");
        }
        if (appointmentTime.getText().toString().equals(getString(R.string.hint_time_appointment))) {
            formStatus = false;
            appointmentTime.setError("Select a time");
        }
        return formStatus;
    }
}

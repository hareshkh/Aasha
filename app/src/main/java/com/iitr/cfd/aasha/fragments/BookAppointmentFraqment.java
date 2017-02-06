package com.iitr.cfd.aasha.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.interfaces.retrofit.ApiCalls;
import com.iitr.cfd.aasha.models.AppointmentModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAppointmentFraqment extends Fragment {

    TextView doctorName;
    TextView hospitalName;
    TextView appointmentDate;
    TextView appointmentTime;
    EditText appointmentDescription;

    Button bookAppointmentButton;

    String appointmentDateString, appointmentTimeString, appointmentDescriptionString;
    int patientId, hospitalId, doctorId; // To be set from data received in Bundle

    Calendar calendar;

    ProgressDialog progressDialog;

    public BookAppointmentFraqment() {
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
        return inflater.inflate(R.layout.fragment_book_appointment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        doctorName = (TextView) view.findViewById(R.id.doctor_name_appointment);
        hospitalName = (TextView) view.findViewById(R.id.hospital_name_appointment);
        appointmentDate = (TextView) view.findViewById(R.id.date_appointment);
        appointmentTime = (TextView) view.findViewById(R.id.time_appointment);
        appointmentDescription = (EditText) view.findViewById(R.id.input_description_appointment);
        bookAppointmentButton = (Button) view.findViewById(R.id.button_book_appointment);

        calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener appointmentDateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String format = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                appointmentDate.setText(getString(R.string.hint_date_appointment) + "\n" + sdf.format(calendar.getTime()));
            }
        };

        final TimePickerDialog.OnTimeSetListener appointmentTimeDialog = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                String format = "hh:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                appointmentTime.setText(getString(R.string.hint_time_appointment) + "\n" + sdf.format(calendar.getTime()));
            }
        };

        appointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), appointmentDateDialog, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
                    ApiCalls.Factory.getInstance().appointmentBookRequest(patientId, hospitalId,
                            doctorId, appointmentDateString.concat(" "+appointmentTimeString),
                            appointmentDescriptionString, "").enqueue(new Callback<AppointmentModel>() {
                        @Override
                        public void onResponse(Call<AppointmentModel> call, Response<AppointmentModel> response) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Appointment booked", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        }

                        @Override
                        public void onFailure(Call<AppointmentModel> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Fill the form completely", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void extractData() {
        appointmentDateString = appointmentDate.getText().toString().substring(appointmentDate.getText().toString().length() - 10);
        appointmentTimeString = appointmentTime.getText().toString().substring(appointmentTime.getText().toString().length() - 8);
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

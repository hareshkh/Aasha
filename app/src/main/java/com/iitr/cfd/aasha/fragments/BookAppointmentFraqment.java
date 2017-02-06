package com.iitr.cfd.aasha.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.iitr.cfd.aasha.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookAppointmentFraqment extends Fragment {

    TextView doctorName;
    TextView hospitalName;
    TextView appointmentDate;
    TextView appointmentTime;
    EditText appointmentDescription;

    Calendar calendar;

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
    }
}

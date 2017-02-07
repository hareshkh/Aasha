package com.iitr.cfd.aasha.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.activities.HomeActivity;
import com.iitr.cfd.aasha.activities.LoginActivity;
import com.iitr.cfd.aasha.adapters.AppointmentsRecyclerAdapter;
import com.iitr.cfd.aasha.models.AppointmentModel;
import com.iitr.cfd.aasha.utilities.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AppointmentFragment extends Fragment {

    public RecyclerView appointmentsRecycler;
    public AppointmentsRecyclerAdapter appointmentsAdapter;
    public List<AppointmentModel> appointments;
    FloatingActionButton fab;

    public AppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("APPOINT", HomeActivity.appointments.size() + "");
        appointments = new ArrayList<>();
        for (AppointmentModel appointmentModel : HomeActivity.appointments) {
            if (appointmentModel.getPatientId() == LoginActivity.PATIENT_ID && !Utils.isOlder(appointmentModel.getTime())) {
                appointments.add(appointmentModel);
            }
        }
        Log.d("APPOINT", appointments.size() + "");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointments, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appointmentsRecycler = (RecyclerView) view.findViewById(R.id.appointments_recycler);
        fab = (FloatingActionButton) view.findViewById(R.id.new_appointment_fab);

        if (appointments != null) {
            appointmentsAdapter = new AppointmentsRecyclerAdapter(getContext(), appointments);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

            appointmentsRecycler.setLayoutManager(linearLayoutManager);
            appointmentsRecycler.setItemAnimator(new DefaultItemAnimator());
            appointmentsRecycler.setAdapter(appointmentsAdapter);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                final String[] time = {""};
                final DatePickerDialog.OnDateSetListener appointmentDateDialog = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String format = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                        time[0] = sdf.format(calendar.getTime());

                        AppointmentDoctorSelectFragment appointmentDoctorSelectFragment = new AppointmentDoctorSelectFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("time", time[0]);
                        appointmentDoctorSelectFragment.setArguments(bundle);

                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.frag_container, appointmentDoctorSelectFragment)
                                .addToBackStack("book_appointment")
                                .commit();

                    }
                };

                new DatePickerDialog(getContext(), appointmentDateDialog, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });
    }

    public void updateList() {
        Log.d("APPOINT", HomeActivity.appointments.size() + "");
        appointments.clear();
        for (AppointmentModel appointmentModel : HomeActivity.appointments) {
            if (appointmentModel.getPatientId() == LoginActivity.PATIENT_ID && !Utils.isOlder(appointmentModel.getTime())) {
                appointments.add(appointmentModel);
            }
        }
        Log.d("APPOINT", appointments.size() + "");
        appointmentsAdapter.notifyDataSetChanged();
    }

}

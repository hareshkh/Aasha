package com.iitr.cfd.aasha.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.activities.HomeActivity;
import com.iitr.cfd.aasha.adapters.DoctorsRecyclerAdapter;
import com.iitr.cfd.aasha.models.DoctorModel;
import com.iitr.cfd.aasha.models.VisitingDoctorModel;
import com.iitr.cfd.aasha.utilities.ClickItemTouchListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorsFragment extends Fragment {

    RecyclerView doctorsRecycler;
    List<DoctorModel> doctors;

    int hospitalID = 0;

    public DoctorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hospitalID = getArguments().getInt("hospital_id");
        doctors = new ArrayList<>();
        for (VisitingDoctorModel visitingDoctorModel : HomeActivity.visits) {
            if (visitingDoctorModel.getHospitalID() == hospitalID) {
                for (DoctorModel doctorModel : HomeActivity.doctors) {
                    if (doctorModel.getId() == visitingDoctorModel.getDoctorID()) {
                        doctors.add(doctorModel);
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        doctorsRecycler = (RecyclerView) view.findViewById(R.id.doctors_recycler);

        DoctorsRecyclerAdapter doctorsRecyclerAdapter = new DoctorsRecyclerAdapter(getContext(), doctors);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        doctorsRecycler.setLayoutManager(linearLayoutManager);
        doctorsRecycler.setItemAnimator(new DefaultItemAnimator());
        doctorsRecycler.setAdapter(doctorsRecyclerAdapter);

        doctorsRecycler.addOnItemTouchListener(new ClickItemTouchListener(doctorsRecycler) {
            @Override
            public boolean onClick(RecyclerView parent, View view, final int position, long id) {

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

                        BookAppointmentFragment bookAppointmentFragment = new BookAppointmentFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("time", time[0]);
                        bundle.putInt("doctor_id", doctors.get(position).getId());
                        bundle.putInt("hospital_id", hospitalID);
                        bookAppointmentFragment.setArguments(bundle);

                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frag_container, bookAppointmentFragment)
                                .addToBackStack("book_appointment")
                                .commit();

                    }
                };

                new DatePickerDialog(getContext(), appointmentDateDialog, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();


                return true;
            }

            @Override
            public boolean onLongClick(RecyclerView parent, View view, int position, long id) {
                return false;
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }
}

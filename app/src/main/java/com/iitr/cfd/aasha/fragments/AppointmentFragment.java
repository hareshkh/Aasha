package com.iitr.cfd.aasha.fragments;

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

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.activities.HomeActivity;
import com.iitr.cfd.aasha.adapters.AppointmentsRecyclerAdapter;
import com.iitr.cfd.aasha.models.AppointmentModel;

import java.util.List;

public class AppointmentFragment extends Fragment {

    RecyclerView appointmentsRecycler;
    List<AppointmentModel> appointments;

    public AppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appointments = HomeActivity.appointments;
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

        if (appointments != null) {
            AppointmentsRecyclerAdapter appointmentsAdapter = new AppointmentsRecyclerAdapter(getContext(), appointments);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

            appointmentsRecycler.setLayoutManager(linearLayoutManager);
            appointmentsRecycler.setItemAnimator(new DefaultItemAnimator());
            appointmentsRecycler.setAdapter(appointmentsAdapter);
        }

    }
}

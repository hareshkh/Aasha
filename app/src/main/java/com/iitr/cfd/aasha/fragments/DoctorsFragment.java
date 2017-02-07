package com.iitr.cfd.aasha.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.activities.HomeActivity;
import com.iitr.cfd.aasha.models.DoctorModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorsFragment extends Fragment {

    RecyclerView doctorsRecycler;
    List<DoctorModel> doctors;
    public static int HOSPITAL_ID;

    public DoctorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        doctors = new ArrayList<>();
        for (DoctorModel doctorModel : HomeActivity.doctors) {
//            if()
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors, container, false);
    }

}

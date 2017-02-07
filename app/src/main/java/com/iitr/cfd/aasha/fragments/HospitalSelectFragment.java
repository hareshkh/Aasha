package com.iitr.cfd.aasha.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iitr.cfd.aasha.activities.LoginActivity;
import com.iitr.cfd.aasha.interfaces.retrofit.ApiCalls;
import com.iitr.cfd.aasha.models.PatientModel;
import com.iitr.cfd.aasha.utilities.ClickItemTouchListener;
import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.activities.HomeActivity;
import com.iitr.cfd.aasha.adapters.HospitalsRecyclerAdapter;
import com.iitr.cfd.aasha.models.HospitalModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HospitalSelectFragment extends Fragment {

    RecyclerView hospitalSelectRecycler;
    List<HospitalModel> hospitals;

    public HospitalSelectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hospitals = HomeActivity.hospitals;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hospital_select, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hospitalSelectRecycler = (RecyclerView) view.findViewById(R.id.hospital_select_recycler);

        HospitalsRecyclerAdapter hospitalsRecyclerAdapter = new HospitalsRecyclerAdapter(getContext(), hospitals);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        hospitalSelectRecycler.setLayoutManager(linearLayoutManager);
        hospitalSelectRecycler.setItemAnimator(new DefaultItemAnimator());
        hospitalSelectRecycler.setAdapter(hospitalsRecyclerAdapter);

        hospitalSelectRecycler.addOnItemTouchListener(new ClickItemTouchListener(hospitalSelectRecycler) {
            @Override
            public boolean onClick(RecyclerView parent, View view, final int position, long id) {
                LoginActivity.patientModel.setHospitalId(hospitals.get(position).getId());
                ApiCalls.Factory.getInstance().setHospitalId(LoginActivity.PATIENT_ID, hospitals.get(position).getId()).enqueue(new Callback<PatientModel>() {
                    @Override
                    public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                        Log.d("RETRO", "Successful Patch");
                        LoginActivity.patientModel = response.body();
                    }

                    @Override
                    public void onFailure(Call<PatientModel> call, Throwable t) {
                        Log.d("RETRO123", t.getMessage());
                    }
                });
                getActivity().getSupportFragmentManager().popBackStack();
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

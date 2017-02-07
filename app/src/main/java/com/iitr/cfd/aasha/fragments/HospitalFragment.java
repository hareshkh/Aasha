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
import com.iitr.cfd.aasha.adapters.HospitalsRecyclerAdapter;
import com.iitr.cfd.aasha.models.HospitalModel;
import com.iitr.cfd.aasha.utilities.ClickItemTouchListener;

import java.util.List;

public class HospitalFragment extends Fragment {

    RecyclerView hospitalsRecycler;
    List<HospitalModel> hospitals;

    public HospitalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hospitals = HomeActivity.hospitals;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hospitals, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hospitalsRecycler = (RecyclerView) view.findViewById(R.id.hospitals_recycler);

        if (hospitals != null) {
            HospitalsRecyclerAdapter hospitalsRecyclerAdapter = new HospitalsRecyclerAdapter(getContext(), hospitals);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

            hospitalsRecycler.setLayoutManager(linearLayoutManager);
            hospitalsRecycler.setItemAnimator(new DefaultItemAnimator());
            hospitalsRecycler.setAdapter(hospitalsRecyclerAdapter);

            hospitalsRecycler.addOnItemTouchListener(new ClickItemTouchListener(hospitalsRecycler) {
                @Override
                public boolean onClick(RecyclerView parent, View view, int position, long id) {


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
}

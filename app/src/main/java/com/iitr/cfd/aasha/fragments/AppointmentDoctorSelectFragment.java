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
import com.iitr.cfd.aasha.adapters.DoctorSelectAdapter;
import com.iitr.cfd.aasha.models.DoctorHospitalPairModel;
import com.iitr.cfd.aasha.models.DoctorModel;
import com.iitr.cfd.aasha.models.HospitalModel;
import com.iitr.cfd.aasha.models.VisitingDoctorModel;
import com.iitr.cfd.aasha.utilities.ClickItemTouchListener;
import com.iitr.cfd.aasha.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDoctorSelectFragment extends Fragment {

    RecyclerView doctorSelectRecycler;
    List<VisitingDoctorModel> visitingDoctorModels;
    List<DoctorHospitalPairModel> doctorHospitalPairModels;

    String time, time2;

    public AppointmentDoctorSelectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        time = getArguments().getString("time");
        time2 = time.concat(" 00:00:00");

        visitingDoctorModels = new ArrayList<>();
        doctorHospitalPairModels = new ArrayList<>();
        for (VisitingDoctorModel visitingDoctorModel : HomeActivity.visits) {
            if (Utils.isOlder(visitingDoctorModel.getBeginTime(), time2) && Utils.isOlder(time2, visitingDoctorModel.getEndTime())) {
                visitingDoctorModels.add(visitingDoctorModel);

                DoctorModel doctor = null;
                HospitalModel hospital = null;

                for (DoctorModel doctorModel : HomeActivity.doctors) {
                    if (doctorModel.getId() == visitingDoctorModel.getDoctorID()) {
                        doctor = doctorModel;
                    }
                }

                for (HospitalModel hospitalModel : HomeActivity.hospitals) {
                    if (hospitalModel.getId() == visitingDoctorModel.getHospitalID()) {
                        hospital = hospitalModel;
                    }
                }

                doctorHospitalPairModels.add(new DoctorHospitalPairModel(doctor, hospital));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hospital_doctor_select, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        doctorSelectRecycler = (RecyclerView) view.findViewById(R.id.hospital_doctor_select_recycler);

        DoctorSelectAdapter doctorSelectAdapter = new DoctorSelectAdapter(getContext(), doctorHospitalPairModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        doctorSelectRecycler.setLayoutManager(linearLayoutManager);
        doctorSelectRecycler.setItemAnimator(new DefaultItemAnimator());
        doctorSelectRecycler.setAdapter(doctorSelectAdapter);

        doctorSelectRecycler.addOnItemTouchListener(new ClickItemTouchListener(doctorSelectRecycler) {
            @Override
            public boolean onClick(RecyclerView parent, View view, int position, long id) {

                BookAppointmentFragment bookAppointmentFragment = new BookAppointmentFragment();
                Bundle bundle = new Bundle();
                bundle.putString("time", time);
                bundle.putInt("doctor_id", doctorHospitalPairModels.get(position).getDoctorModel().getId());
                bundle.putInt("hospital_id", doctorHospitalPairModels.get(position).getHospitalModel().getId());
                bookAppointmentFragment.setArguments(bundle);

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_container, bookAppointmentFragment)
                        .addToBackStack("book_appointment_final")
                        .commit();

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

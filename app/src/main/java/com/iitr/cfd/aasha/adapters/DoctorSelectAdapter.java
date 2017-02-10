package com.iitr.cfd.aasha.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.models.DoctorHospitalPairModel;

import java.util.List;

public class DoctorSelectAdapter extends RecyclerView.Adapter<DoctorSelectAdapter.MyViewHolder> {

    List<DoctorHospitalPairModel> doctorHospitalPairModels;

    public DoctorSelectAdapter(Context context, List<DoctorHospitalPairModel> doctorHospitalPairModels) {
        this.doctorHospitalPairModels = doctorHospitalPairModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visiting_doctor_item, parent, false);
        return new DoctorSelectAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.doctorName.setText(doctorHospitalPairModels.get(position).getDoctorModel().getName());
        holder.hospitalName.setText(doctorHospitalPairModels.get(position).getHospitalModel().getName());
        holder.doctorDetails.setText(doctorHospitalPairModels.get(position).getDoctorModel().getDetails());
    }

    @Override
    public int getItemCount() {
        return doctorHospitalPairModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView doctorName, hospitalName, doctorDetails;

        public MyViewHolder(View itemView) {
            super(itemView);
            doctorName = (TextView) itemView.findViewById(R.id.doctor_name);
            hospitalName = (TextView) itemView.findViewById(R.id.hospital_name);
            doctorDetails = (TextView) itemView.findViewById(R.id.doctor_details);
        }
    }

}

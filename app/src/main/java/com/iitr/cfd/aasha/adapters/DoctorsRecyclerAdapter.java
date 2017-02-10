package com.iitr.cfd.aasha.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.activities.HomeActivity;
import com.iitr.cfd.aasha.models.DoctorModel;
import com.iitr.cfd.aasha.models.HospitalModel;

import java.util.List;

public class DoctorsRecyclerAdapter extends RecyclerView.Adapter<DoctorsRecyclerAdapter.MyViewHolder> {

    Context context;
    List<DoctorModel> doctorModelList;
    int hospitalID;

    public DoctorsRecyclerAdapter(Context context, List<DoctorModel> doctorModelList, int hospitalID) {
        this.context = context;
        this.doctorModelList = doctorModelList;
        this.hospitalID = hospitalID;
    }

    @Override
    public DoctorsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visiting_doctor_item, parent, false);
        return new DoctorsRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DoctorsRecyclerAdapter.MyViewHolder holder, int position) {
        DoctorModel doctorModel = doctorModelList.get(position);
        holder.doctorName.setText(doctorModel.getName());
        holder.doctorDetails.setText(doctorModel.getDetails());
        for (HospitalModel hospitalModel : HomeActivity.hospitals) {
            if (hospitalModel.getId() == hospitalID) {
                holder.hospitalName.setText(hospitalModel.getName());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return doctorModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView doctorName;
        TextView doctorDetails;
        TextView hospitalName;

        public MyViewHolder(View itemView) {
            super(itemView);
            doctorName = (TextView) itemView.findViewById(R.id.doctor_name);
            doctorDetails = (TextView) itemView.findViewById(R.id.doctor_details);
            hospitalName = (TextView) itemView.findViewById(R.id.hospital_name);
        }
    }

}

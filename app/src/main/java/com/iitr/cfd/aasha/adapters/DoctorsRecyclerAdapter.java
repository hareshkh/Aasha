package com.iitr.cfd.aasha.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.models.DoctorModel;
import com.iitr.cfd.aasha.models.HospitalModel;

import java.util.List;

/**
 * Created by haresh on 4/2/17.
 */

public class DoctorsRecyclerAdapter extends RecyclerView.Adapter<DoctorsRecyclerAdapter.MyViewHolder> {

    Context context;
    List<DoctorModel> doctorModelList;

    public DoctorsRecyclerAdapter(Context context, List<DoctorModel> doctorModelList) {
        this.context = context;
        this.doctorModelList = doctorModelList;
    }

    @Override
    public DoctorsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_recycler_item, parent, false);
        return new DoctorsRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DoctorsRecyclerAdapter.MyViewHolder holder, int position) {
        DoctorModel doctorModel = doctorModelList.get(position);
        holder.doctorName.setText(doctorModel.getName());
        holder.doctorDetails.setText(doctorModel.getDetails());
    }

    @Override
    public int getItemCount() {
        return doctorModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView doctorImage;
        TextView doctorName;
        TextView doctorDetails;

        public MyViewHolder(View itemView) {
            super(itemView);
            doctorImage = (ImageView) itemView.findViewById(R.id.doctor_image);
            doctorName = (TextView) itemView.findViewById(R.id.doctor_name);
            doctorDetails = (TextView) itemView.findViewById(R.id.doctor_details);
        }
    }

}

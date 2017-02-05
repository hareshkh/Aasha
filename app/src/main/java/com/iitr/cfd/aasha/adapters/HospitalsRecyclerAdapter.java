package com.iitr.cfd.aasha.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.models.HospitalModel;

import java.util.List;

public class HospitalsRecyclerAdapter extends RecyclerView.Adapter<HospitalsRecyclerAdapter.MyViewHolder> {

    Context context;
    List<HospitalModel> hospitalModelList;

    public HospitalsRecyclerAdapter(Context context, List<HospitalModel> hospitalModelList) {
        this.context = context;
        this.hospitalModelList = hospitalModelList;
    }

    @Override
    public HospitalsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hospital_recycler_item, parent, false);
        return new HospitalsRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HospitalsRecyclerAdapter.MyViewHolder holder, int position) {
        HospitalModel hospital = hospitalModelList.get(position);
        holder.hospitalName.setText(hospital.getName());
        holder.hospitalContact.setText(hospital.getPhone());
    }

    @Override
    public int getItemCount() {
        return hospitalModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView hospitalName;
        TextView hospitalContact;
        TextView hospitalDistance;

        public MyViewHolder(View itemView) {
            super(itemView);
            hospitalName = (TextView) itemView.findViewById(R.id.hospital_name);
            hospitalContact = (TextView) itemView.findViewById(R.id.hospital_contact);
            hospitalDistance = (TextView) itemView.findViewById(R.id.hospital_distance);
        }
    }

}

package com.iitr.cfd.aasha.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.models.AppointmentModel;

import java.util.List;

/**
 * Created by haresh on 4/2/17.
 */

public class AppointmentsRecyclerAdapter extends RecyclerView.Adapter<AppointmentsRecyclerAdapter.MyViewHolder> {

    Context context;
    List<AppointmentModel> appointmentModelList;

    public AppointmentsRecyclerAdapter(Context context, List<AppointmentModel> appointmentModelList) {
        this.context = context;
        this.appointmentModelList = appointmentModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_recycler_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AppointmentModel appointment = appointmentModelList.get(position);
        holder.appointmentTimeText.setText(appointment.getTime());
        holder.appointmentStatusText.setText(appointment.getStatus());
        holder.appointmentDescriptionText.setText(appointment.getDescription());
    }

    @Override
    public int getItemCount() {
        return appointmentModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView appointmentTimeText;
        TextView appointmentStatusText;
        TextView appointmentDescriptionText;

        public MyViewHolder(View itemView) {
            super(itemView);
            appointmentTimeText = (TextView) itemView.findViewById(R.id.appointment_time);
            appointmentStatusText = (TextView) itemView.findViewById(R.id.appointment_status);
            appointmentDescriptionText = (TextView) itemView.findViewById(R.id.appointment_description);
        }
    }

}

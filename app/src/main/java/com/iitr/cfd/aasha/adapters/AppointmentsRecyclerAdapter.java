package com.iitr.cfd.aasha.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iitr.cfd.aasha.R;
import com.iitr.cfd.aasha.activities.HomeActivity;
import com.iitr.cfd.aasha.models.AppointmentModel;
import com.iitr.cfd.aasha.models.DoctorModel;

import java.util.List;

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
        DoctorModel doctor = null;
        for (DoctorModel doctorModel : HomeActivity.doctors) {
            if (doctorModel.getId() == appointment.getDoctorId()) {
                doctor = doctorModel;
            }
        }

        if (doctor == null) {
            return;
        }

        holder.appointmentDoctorText.setText(doctor.getName());
        holder.appointmentTimeText.setText(appointment.getTime());
        holder.appointmentStatusText.setText(appointment.getStatus());
        holder.appointmentDescriptionText.setText(appointment.getDescription());
    }

    @Override
    public int getItemCount() {
        return appointmentModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView appointmentDoctorText;
        TextView appointmentTimeText;
        TextView appointmentStatusText;
        TextView appointmentDescriptionText;

        public MyViewHolder(View itemView) {
            super(itemView);
            appointmentDoctorText = (TextView) itemView.findViewById(R.id.appointment_doctor_name);
            appointmentTimeText = (TextView) itemView.findViewById(R.id.appointment_time);
            appointmentStatusText = (TextView) itemView.findViewById(R.id.appointment_status);
            appointmentDescriptionText = (TextView) itemView.findViewById(R.id.appointment_description);
        }
    }

}

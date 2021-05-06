package com.indev.jubicare_premium.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.activity.CommonAppointment;
import com.indev.jubicare_premium.database.OldAppointmentPojo;

import java.util.ArrayList;

public class OldAppointmentAdapter extends RecyclerView.Adapter<OldAppointmentAdapter.ViewHolder> {
private Context context;
private ArrayList<OldAppointmentPojo> arrayList;

public OldAppointmentAdapter(Context context, ArrayList<OldAppointmentPojo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        }


@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.custom_old_appointment, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_date.setText(arrayList.get(position).getDate());
        holder.tv_doctor.setText(arrayList.get(position).getDoctor_name());
        holder.view.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        Intent intentDetailActivity=new Intent(context, CommonAppointment.class);
        context.startActivity(intentDetailActivity);
        ((Activity)context).finish();
        }
        });

        }

@Override
public int getItemCount() {
        return arrayList.size();
        }

public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_date,tv_doctor;
    ImageView view;

    public ViewHolder(View itemView) {
        super(itemView);
        this.tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        this.tv_doctor = (TextView) itemView.findViewById(R.id.tv_doctor);
        this.view = (ImageView) itemView.findViewById(R.id.view);

    }
}
}
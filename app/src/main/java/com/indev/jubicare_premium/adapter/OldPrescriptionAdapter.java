package com.indev.jubicare_premium.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.indev.jubicare_premium.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indev.jubicare_premium.activity.CommonPrescription;
import com.indev.jubicare_premium.database.OldPrescriptionPojo;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OldPrescriptionAdapter extends RecyclerView.Adapter<OldPrescriptionAdapter.ViewHolder> {
    private Context context;
    private List<ContentValues> listModels;
    SharedPrefHelper sharedPrefHelper;

    public OldPrescriptionAdapter(Context context, List<ContentValues> listModels) {
        this.context = context;
        this.listModels = listModels;
        sharedPrefHelper=new SharedPrefHelper(context);    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.custom_old_prescription, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_date.setText(new StringBuilder().append(listModels.get(position).get("date").toString()).append(", ").toString());
        holder.tv_doctor.setText(new StringBuilder().append(listModels.get(position).get("doctor_name").toString()).append(", ").toString());

//        holder.tv_date.setText(arrayList.get(position).getDate());
//        holder.tv_doctor.setText(arrayList.get(position).getDoctor_name());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentDetailActivity=new Intent(context, CommonPrescription.class);
                context.startActivity(intentDetailActivity);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView tv_date,tv_doctor;
//        ImageView view;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_doctor)
        TextView tv_doctor;
        @BindView(R.id.view)
        ImageView view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
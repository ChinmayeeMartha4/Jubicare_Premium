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
import com.indev.jubicare_premium.activity.CommonReport;
import com.indev.jubicare_premium.database.ReportsPojo;

import java.util.ArrayList;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ReportsPojo> arrayList;

    public ReportsAdapter(Context context, ArrayList<ReportsPojo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ReportsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.custom_reports, parent, false);
        ReportsAdapter.ViewHolder viewHolder = new ReportsAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsAdapter.ViewHolder holder, int position) {
        holder.tv_date1.setText(arrayList.get(position).getDate());
        holder.tv_title.setText(arrayList.get(position).getTitle());
        holder.view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Need discussion", Toast.LENGTH_SHORT).show();
                Intent intentDetailActivity=new Intent(context, CommonReport.class);
                context.startActivity(intentDetailActivity);
                ((Activity)context).finish();
//                Intent intentDetailActivity=new Intent(context, .class);
//                context.startActivity(intentDetailActivity);
//                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date1, tv_title;
        ImageView view1;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_date1 = (TextView) itemView.findViewById(R.id.tv_date1);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.view1 = (ImageView) itemView.findViewById(R.id.view1);

        }
    }
}
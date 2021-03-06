package com.indev.jubicare_premium.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.activity.CommonAppointment;
import com.indev.jubicare_premium.activity.OldAppointment;
import com.indev.jubicare_premium.database.OldAppointmentPojo;
import com.indev.jubicare_premium.sqlitehelper.SharedPrefHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OldAppointmentAdapter extends RecyclerView.Adapter<OldAppointmentAdapter.ViewHolder> {
private Context context;
    private List<ContentValues> listModels;
    private List<ContentValues> listModels1;
    SharedPrefHelper sharedPrefHelper;
    OldAppointmentAdapter.ClickListener clickListener;
    private String symptom;
public OldAppointmentAdapter(Context context, List<ContentValues> listModels, String symptom) {
    this.context = context;
    this.listModels = listModels;
    this.symptom = symptom;
    sharedPrefHelper=new SharedPrefHelper(context);    }



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
    holder.tv_date.setText(new StringBuilder().append(listModels.get(position).get("assigned_doctor_on").toString()).toString());
    holder.tv_doctor.setText(new StringBuilder().append(listModels.get(position).get("assigned_doctor").toString()).toString());
    if (holder.tv_doctor.getText().toString().equalsIgnoreCase("0")) {
        holder.tv_doctor.setText(Html.fromHtml("Doctor's name not assigned"));
    }
//        holder.tv_date.setText(arrayList.get(position).getDate());
//        holder.tv_doctor.setText(arrayList.get(position).getDoctor_name());

        holder.view.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        Intent intentDetailActivity=new Intent(context, CommonAppointment.class);
        intentDetailActivity.putExtra("id",listModels.get(position).get("id").toString());
        intentDetailActivity.putExtra("prescribed_medicine",listModels.get(position).get("prescribed_medicine").toString());
        intentDetailActivity.putExtra("prescribed_medicine_date",listModels.get(position).get("prescribed_medicine_date").toString());
        intentDetailActivity.putExtra("is_emergency",listModels.get(position).get("is_emergency").toString());
        intentDetailActivity.putExtra("remarks",listModels.get(position).get("remarks").toString());
        intentDetailActivity.putExtra("bp_lower",listModels.get(position).get("bp_lower").toString());
        intentDetailActivity.putExtra("bp_upper",listModels.get(position).get("bp_upper").toString());
        intentDetailActivity.putExtra("sugar",listModels.get(position).get("sugar").toString());
        intentDetailActivity.putExtra("temperature",listModels.get(position).get("temperature").toString());
        intentDetailActivity.putExtra("blood_oxygen_level",listModels.get(position).get("blood_oxygen_level").toString());
        intentDetailActivity.putExtra("pulse",listModels.get(position).get("pulse").toString());
        intentDetailActivity.putExtra("assigned_doctor_on",listModels.get(position).get("assigned_doctor_on").toString());
//        intentDetailActivity.putExtra("view_prescription_click",listModels.get(position).get("view_prescription_click").toString());
        intentDetailActivity.putExtra("assigned_doctor",listModels.get(position).get("assigned_doctor").toString());
        intentDetailActivity.putExtra("sy",symptom);


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
    public interface ClickListener {
        void onItemClick(int position);

        void onListItemClick(int position);
    }

    public void onItemClick(OldAppointmentAdapter.ClickListener listener) {
        this.clickListener = listener;
    }

}

package com.indev.jubicare_premium.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.indev.jubicare_premium.R;

import com.indev.jubicare_premium.database.FamilyPojo;
import com.indev.jubicare_premium.database.SignUpModel;

import java.util.ArrayList;

public class FamilyListAdapter extends ArrayAdapter<FamilyPojo> {

    public FamilyListAdapter(Context context, ArrayList<FamilyPojo> familyPojos) {
        super(context, 0, familyPojos);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View hold, @NonNull ViewGroup parent) {
//        holder.tv_patient_name.setText(new StringBuilder().append(familyPojos.get(position).get("full_name").toString()).append(", ").toString());

        return initView(position, hold, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View hold, @NonNull ViewGroup parent) {
        if (hold == null) {
            hold = LayoutInflater.from(getContext()).inflate(R.layout.activity_family_home, parent, false
            );
        }
//        ImageView imageViewFlag = convertView.findViewById(R.id.image_view_flag);
        TextView textViewName = hold.findViewById(R.id.tv_name);
        FamilyPojo familyPojos = getItem(position);

        if (familyPojos != null) {
//            imageViewFlag.setImageResource(familyPojo.getFlagImage());
            textViewName.setText(familyPojos.getName());
            textViewName.setTextColor((Color.GRAY));
        }
        return hold;
    }


    private View initView(int position, View hold, ViewGroup parent) {
        if (hold == null) {
            hold = LayoutInflater.from(getContext()).inflate(R.layout.activity_family_home, parent, false
            );
        }
//        ImageView imageViewFlag = convertView.findViewById(R.id.image_view_flag);
        TextView textViewName = hold.findViewById(R.id.tv_name);
        FamilyPojo familyPojos = getItem(position);
        if (familyPojos != null) {
//            imageViewFlag.setImageResource(familyPojo.getFlagImage());
            textViewName.setText(familyPojos.getName());
            textViewName.setTextColor((Color.WHITE));
        }
        return hold;
    }


}

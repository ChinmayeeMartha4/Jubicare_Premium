package com.indev.jubicare_premium.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indev.jubicare_premium.WebViewImageActivity;
import com.indev.jubicare_premium.R;
import com.indev.jubicare_premium.rest_api.APIClient;
import com.indev.jubicare_premium.WebViewImageActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TestDocsAdapterInProfile extends RecyclerView.Adapter<TestDocsAdapterInProfile.ViewHolder> {

    private Context context;
    ArrayList<String> docs;

    public TestDocsAdapterInProfile(Context context, ArrayList<String> docs) {
        this.context = context;
        this.docs = docs;
    }

    @NonNull
    @Override
    public TestDocsAdapterInProfile.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.test_docs_inprofile, parent, false);
        TestDocsAdapterInProfile.ViewHolder viewHolder = new TestDocsAdapterInProfile.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestDocsAdapterInProfile.ViewHolder holder, int position) {
        String url = APIClient.IMAGE_URL_DOC + docs.get(position);
        if (!url.equals("") || url!=null){
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.doc_attach)
                    .into(holder.iv_test_docs);
        }

        holder.iv_test_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url= APIClient.IMAGE_URL_DOC + docs.get(position);
                Intent intent= new Intent(context, WebViewImageActivity.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return docs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_test_docs;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_test_docs=itemView.findViewById(R.id.iv_test_docs);
        }
    }
}

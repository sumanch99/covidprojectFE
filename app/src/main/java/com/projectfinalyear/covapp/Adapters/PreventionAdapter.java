package com.projectfinalyear.covapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.projectfinalyear.covapp.Models.CategoryModel;
import com.projectfinalyear.covapp.NewsActivity;
import com.projectfinalyear.covapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PreventionAdapter extends RecyclerView.Adapter<PreventionAdapter.ViewHolder> {

    private List<CategoryModel> list;
    Context context;

    public PreventionAdapter(List<CategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_category, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull PreventionAdapter.ViewHolder holder, int position) {
        CategoryModel cm = list.get(position);

        holder.title.setText(cm.getName());
        Glide.with(context).load(cm.getUrl()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setIntent = new Intent(context, NewsActivity.class);
                setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                setIntent.putExtra("catId",cm.getCategoryId());
                context.startActivity(setIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView image;
        private TextView title;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.category_img);
            title = itemView.findViewById(R.id.text_view_title);
        }
    }
}

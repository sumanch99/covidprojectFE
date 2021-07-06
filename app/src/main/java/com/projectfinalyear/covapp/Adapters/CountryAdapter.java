package com.projectfinalyear.covapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.projectfinalyear.covapp.Apis.CountryData;
import com.projectfinalyear.covapp.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private Context context;
    private List<CountryData> list;

    public CountryAdapter(Context context, List<CountryData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_country, parent,false);

        return new CountryViewHolder(view);
    }

    public void filterList(List<CountryData> filterList)
    {
        list = filterList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull  CountryAdapter.CountryViewHolder holder, int position) {
        CountryData countryData = list.get(position);

        holder.countryCases.setText(NumberFormat.getInstance().format(Integer.parseInt(countryData.getCases())));
        holder.countryName.setText(countryData.getCountry());
        holder.sNo.setText(String.valueOf(position+1));

        Map<String, String> img = countryData.getCountryInfo();
        Glide.with(context).load(img.get("flag")).into(holder.imageView);
//start
        /*
       holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("country", countryData.getCountry());
                context.startActivity(intent);
            }
        });*/
//end
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {
        private TextView sNo, countryName, countryCases;
        private ImageView imageView;


        public CountryViewHolder(@NonNull  View itemView) {
            super(itemView);

            sNo = itemView.findViewById(R.id.sNo);
            countryName = itemView.findViewById(R.id.countries_name);
            countryCases = itemView.findViewById(R.id.country_cases);
            imageView = itemView.findViewById(R.id.countries_image);
        }
    }
}

package com.projectfinalyear.covapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.projectfinalyear.covapp.Apis.Constants.STATE_ACTIVE;
import static com.projectfinalyear.covapp.Apis.Constants.STATE_CONFIRMED;
import static com.projectfinalyear.covapp.Apis.Constants.STATE_CONFIRMED_NEW;
import static com.projectfinalyear.covapp.Apis.Constants.STATE_DEATH;
import static com.projectfinalyear.covapp.Apis.Constants.STATE_DEATH_NEW;
import static com.projectfinalyear.covapp.Apis.Constants.STATE_LAST_UPDATE;
import static com.projectfinalyear.covapp.Apis.Constants.STATE_NAME;
import static com.projectfinalyear.covapp.Apis.Constants.STATE_RECOVERED;
import static com.projectfinalyear.covapp.Apis.Constants.STATE_RECOVERED_NEW;


public class EachStateDataActivity extends AppCompatActivity {
    private TextView tv_confirmed, tv_confirmed_new, tv_active, tv_active_new, tv_death, tv_death_new,
            tv_recovered, tv_recovered_new, tv_lastupdatedate, tv_dist, stateName;

    private String str_stateName, str_confirmed, str_confirmed_new, str_active, str_active_new, str_death, str_death_new,
            str_recovered, str_recovered_new, str_lastupdatedate;

    private PieChart pieChart;

    private LinearLayout lin_district;

    private MainActivity activity = new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_state_data);

        GetIntent();


        Init();

        LoadStateData();

        lin_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EachStateDataActivity.this, "Select District of "+str_stateName, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EachStateDataActivity.this, DistrictWiseDataActivity.class);
                intent.putExtra(STATE_NAME, str_stateName);
                startActivity(intent);
            }
        });
    }

    private void LoadStateData() {
        //Show dialog
        Handler postDelayToshowProgress = new Handler();
        postDelayToshowProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_confirmed.setText(NumberFormat.getInstance().format(Integer.parseInt(str_confirmed)));
                tv_confirmed_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_confirmed_new)));

                tv_active.setText(NumberFormat.getInstance().format(Integer.parseInt(str_active)));
                int int_active_new = Integer.parseInt(str_confirmed_new)
                        - (Integer.parseInt(str_recovered_new) + Integer.parseInt(str_death_new));
                tv_active_new.setText("+"+NumberFormat.getInstance().format(int_active_new<0 ? 0 : int_active_new));

                tv_death.setText(NumberFormat.getInstance().format(Integer.parseInt(str_death)));
                tv_death_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_death_new)));

                tv_recovered.setText(NumberFormat.getInstance().format(Integer.parseInt(str_recovered)));
                tv_recovered_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_recovered_new)));

                String formatDate = FormatDate(str_lastupdatedate, 0);
                tv_lastupdatedate.setText(formatDate);

                tv_dist.setText("District data of "+str_stateName);

                //setting piechart

                pieChart.addPieSlice(new PieModel("Confirm",Integer.parseInt(str_confirmed),getColor(R.color.yellow)));
                pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(str_active), getResources().getColor(R.color.blue)));
                pieChart.addPieSlice(new PieModel("Death", Integer.parseInt(str_recovered), getResources().getColor(R.color.green)));
                pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(str_death), getResources().getColor(R.color.red)));

                pieChart.startAnimation();

            }
        },1000);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    public String FormatDate(String date, int testCase) {
        Date mDate = null;
        String dateFormat;
        try {
            mDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(date);
            if (testCase == 0) {
                dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a").format(mDate);
                return dateFormat;
            } else if (testCase == 1) {
                dateFormat = new SimpleDateFormat("dd MMM yyyy").format(mDate);
                return dateFormat;
            } else if (testCase == 2) {
                dateFormat = new SimpleDateFormat("hh:mm a").format(mDate);
                return dateFormat;
            } else {
                Log.d("error", "Wrong input! Choose from 0 to 2");
                return "Error";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }


    private void Init() {
        tv_confirmed = findViewById(R.id.activity_each_state_confirmed_textView);
        tv_confirmed_new = findViewById(R.id.activity_each_state_confirmed_new_textView);
        tv_active = findViewById(R.id.activity_each_state_active_textView);
        tv_active_new = findViewById(R.id.activity_each_state_active_new_textView);
        tv_recovered = findViewById(R.id.activity_each_state_recovered_textView);
        tv_recovered_new = findViewById(R.id.activity_each_state_recovered_new_textView);
        tv_death = findViewById(R.id.activity_each_state_death_textView);
        tv_death_new = findViewById(R.id.activity_each_state_death_new_textView);
        tv_lastupdatedate = findViewById(R.id.activity_each_state_lastupdate_textView);
        tv_dist = findViewById(R.id.activity_each_state_district_data_title);
        pieChart = findViewById(R.id.activity_each_state_piechart);
        lin_district = findViewById(R.id.activity_each_state_lin);
        stateName = findViewById(R.id.stateName);
        stateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EachStateDataActivity.this, StateWiseDataActivity.class));
            }
        });
    }

    private void GetIntent() {
        Intent intent = getIntent();
        str_stateName = intent.getStringExtra(STATE_NAME);
        str_confirmed = intent.getStringExtra(STATE_CONFIRMED);
        str_confirmed_new = intent.getStringExtra(STATE_CONFIRMED_NEW);
        str_active = intent.getStringExtra(STATE_ACTIVE);
        str_death = intent.getStringExtra(STATE_DEATH);
        str_death_new = intent.getStringExtra(STATE_DEATH_NEW);
        str_recovered = intent.getStringExtra(STATE_RECOVERED);
        str_recovered_new = intent.getStringExtra(STATE_RECOVERED_NEW);
        str_lastupdatedate = intent.getStringExtra(STATE_LAST_UPDATE);
    }
}
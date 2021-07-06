package com.projectfinalyear.covapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;

import static com.projectfinalyear.covapp.Apis.Constants.DISTRICT_ACTIVE;
import static com.projectfinalyear.covapp.Apis.Constants.DISTRICT_CONFIRMED;
import static com.projectfinalyear.covapp.Apis.Constants.DISTRICT_CONFIRMED_NEW;
import static com.projectfinalyear.covapp.Apis.Constants.DISTRICT_DEATH;
import static com.projectfinalyear.covapp.Apis.Constants.DISTRICT_DEATH_NEW;
import static com.projectfinalyear.covapp.Apis.Constants.DISTRICT_NAME;
import static com.projectfinalyear.covapp.Apis.Constants.DISTRICT_RECOVERED;
import static com.projectfinalyear.covapp.Apis.Constants.DISTRICT_RECOVERED_NEW;


public class EachDistrictDataActivity extends AppCompatActivity {

    private TextView tv_confirmed, tv_confirmed_new, tv_active, tv_active_new,
            tv_recovered, tv_recovered_new, tv_death, tv_death_new,dist_name;

    private String str_districtName, str_confirmed, str_confirmed_new, str_active, str_death, str_death_new,
            str_recovered, str_recovered_new;

    private PieChart pieChart;
    ProgressDialog dialog;
    private LinearLayout home_btn;

    private MainActivity activity = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_district_data);

        //Fetching data which is passed from previous activity into this activity
        GetIntent();

        //Initialise all textviews
        Init();


        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading........");
        dialog.setCancelable(false);
        dialog.show();


        //Load data
        LoadDistrictData();
    }

    private void LoadDistrictData() {
        //Show dialog
        dialog.dismiss();

        Handler postDelayToshowProgress = new Handler();
        postDelayToshowProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_confirmed.setText(NumberFormat.getInstance().format(Integer.parseInt(str_confirmed)));
                tv_confirmed_new.setText("+"+ NumberFormat.getInstance().format(Integer.parseInt(str_confirmed_new)));

                tv_active.setText(NumberFormat.getInstance().format(Integer.parseInt(str_active)));
                int int_active_new = Integer.parseInt(str_confirmed_new)
                        - (Integer.parseInt(str_recovered_new) + Integer.parseInt(str_death_new));
                tv_active_new.setText("+"+ NumberFormat.getInstance().format(int_active_new<0 ? 0 : int_active_new));

                tv_death.setText(NumberFormat.getInstance().format(Integer.parseInt(str_death)));
                tv_death_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_death_new)));

                tv_recovered.setText(NumberFormat.getInstance().format(Integer.parseInt(str_recovered)));
                tv_recovered_new.setText("+"+NumberFormat.getInstance().format(Integer.parseInt(str_recovered_new)));

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

    private void GetIntent() {
        Intent intent = getIntent();
        str_districtName = intent.getStringExtra(DISTRICT_NAME);
        str_confirmed = intent.getStringExtra(DISTRICT_CONFIRMED);
        str_confirmed_new = intent.getStringExtra(DISTRICT_CONFIRMED_NEW);
        str_active = intent.getStringExtra(DISTRICT_ACTIVE);
        str_death = intent.getStringExtra(DISTRICT_DEATH);
        str_death_new = intent.getStringExtra(DISTRICT_DEATH_NEW);
        str_recovered = intent.getStringExtra(DISTRICT_RECOVERED);
        str_recovered_new = intent.getStringExtra(DISTRICT_RECOVERED_NEW);
        home_btn = findViewById(R.id.home_btn);
        dist_name = findViewById(R.id.dist_Name);

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EachDistrictDataActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    private void Init() {
        tv_confirmed = findViewById(R.id.activity_each_district_confirmed_textView);
        tv_confirmed_new = findViewById(R.id.activity_each_district_confirmed_new_textView);
        tv_active = findViewById(R.id.activity_each_district_active_textView);
        tv_active_new = findViewById(R.id.activity_each_district_active_new_textView);
        tv_recovered = findViewById(R.id.activity_each_district_recovered_textView);
        tv_recovered_new = findViewById(R.id.activity_each_district_recovered_new_textView);
        tv_death = findViewById(R.id.activity_each_district_death_textView);
        tv_death_new = findViewById(R.id.activity_each_district_death_new_textView);
        pieChart = findViewById(R.id.activity_each_district_piechart);
    }
}
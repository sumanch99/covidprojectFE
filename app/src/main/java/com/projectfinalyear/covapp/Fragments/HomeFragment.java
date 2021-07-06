package com.projectfinalyear.covapp.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.projectfinalyear.covapp.Apis.ApiUtils;
import com.projectfinalyear.covapp.Apis.CountryData;
import com.projectfinalyear.covapp.CountryActivity;
import com.projectfinalyear.covapp.R;
import com.projectfinalyear.covapp.StateWiseDataActivity;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private TextView totalConfirm, totalActive, totalRecovered, totalDeath, totalTests;
    private TextView todayConfirm, todayRecovered, todayDeath, dateTv, countryName;
    private PieChart pieChart;
    ProgressDialog dialog;

    private LinearLayout linearLayout, prevention;

    private String str_confirmed, str_confirmed_new, str_active, str_active_new, str_recovered, str_recovered_new,
            str_death, str_death_new, str_tests, str_tests_new, str_last_update_time;


    private List<CountryData> list;

        View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        list = new ArrayList<>();
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading........");
        dialog.setCancelable(false);
        dialog.show();

        init();
        countryName = view.findViewById(R.id.countryName);

        FetchData();

        linearLayout = view.findViewById(R.id.state);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StateWiseDataActivity.class));
            }
        });



        countryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CountryActivity.class));

            }
        });


        ApiUtils.getApiInterface().getCountryData()
                .enqueue(new Callback<List<CountryData>>() {
                    @Override
                    public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                        list.addAll(response.body());
                        dialog.dismiss();
                        for (int i = 0; i<list.size();i++)
                        {
                            if (list.get(i).getCountry().equals("India"))
                            {
                                int confirm = Integer.parseInt(list.get(i).getCases());
                                int active = Integer.parseInt(list.get(i).getActive());
                                int recovered = Integer.parseInt(list.get(i).getRecovered());
                                int death = Integer.parseInt(list.get(i).getDeaths());


                                totalConfirm.setText(NumberFormat.getInstance().format(confirm));
                                totalActive.setText(NumberFormat.getInstance().format(active));
                                totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                                totalDeath.setText(NumberFormat.getInstance().format(death));

                                todayDeath.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                                todayConfirm.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                                todayRecovered.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));
                                totalTests.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));


                                setDate(list.get(i).getUpdated());


                                pieChart.addPieSlice(new PieModel("Confirm", confirm, getResources().getColor(R.color.yellow)));
                                pieChart.addPieSlice(new PieModel("Active", active, getResources().getColor(R.color.blue)));
                                pieChart.addPieSlice(new PieModel("Death", recovered, getResources().getColor(R.color.green)));
                                pieChart.addPieSlice(new PieModel("Recovered", death, getResources().getColor(R.color.red)));
                                pieChart.startAnimation();


                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<List<CountryData>> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        return view;
    }

    private void FetchData() {

        //show progress dialog

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String apiUrl = "https://api.covid19india.org/data.json";

        pieChart.clearChart();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //As the data of the json are in a nested array, so we need to define the array from which we want to fetch the data.
                        JSONArray all_state_jsonArray = null;
                        JSONArray testData_jsonArray = null;

                        try {
                            all_state_jsonArray = response.getJSONArray("statewise");
                            testData_jsonArray = response.getJSONArray("tested");
                            JSONObject data_india = all_state_jsonArray.getJSONObject(0);
                            JSONObject test_data_india = testData_jsonArray.getJSONObject(testData_jsonArray.length()-1);

                            //Fetching data for India and storing it in String
                            str_confirmed = data_india.getString("confirmed");   //Confirmed cases in India
                            str_confirmed_new = data_india.getString("deltaconfirmed");   //New Confirmed cases from last update time

                            str_active = data_india.getString("active");    //Active cases in India

                            str_recovered = data_india.getString("recovered");  //Total recovered cased in India
                            str_recovered_new = data_india.getString("deltarecovered"); //New recovered cases from last update time

                            str_death = data_india.getString("deaths");     //Total deaths in India
                            str_death_new = data_india.getString("deltadeaths");    //New death cases from last update time

                            str_last_update_time = data_india.getString("lastupdatedtime"); //Last update date and time

                            str_tests = test_data_india.getString("totalsamplestested"); //Total samples tested in India
                            str_tests_new = test_data_india.getString("samplereportedtoday");   //New samples tested today

                            Handler delayToshowProgess = new Handler();

                            delayToshowProgess.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Setting text in the textview
                                    pieChart.startAnimation();


                                }
                            }, 1000);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private void setDate(String updated) {

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

        long milliseconds  = Long.parseLong(updated);

        Calendar calendar  = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        dateTv.setText("Updated at : " +dateFormat.format(calendar.getTime()));

    }

    private void init() {
        totalConfirm = view.findViewById(R.id.total_confirm);
        totalActive= view.findViewById(R.id.total_active);
        totalRecovered = view.findViewById(R.id.total_recovered);
        totalDeath = view.findViewById(R.id.total_death);
        totalTests= view.findViewById(R.id.total_tests);
        todayConfirm = view.findViewById(R.id.today_confirm);
        todayRecovered = view.findViewById(R.id.today_recovered);
        todayDeath = view.findViewById(R.id.today_death) ;
        pieChart = view.findViewById(R.id.pieChart) ;
        dateTv = view.findViewById(R.id.date_time) ;
        prevention = view.findViewById(R.id.prevention) ;

        prevention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new PriventionFragment(),"CoWin");
            }
        });


    }

    private void replace(Fragment fragment, String s) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.addToBackStack(s);
        transaction.commit();
    }

}
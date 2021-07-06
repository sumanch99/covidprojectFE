package com.projectfinalyear.covapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.projectfinalyear.covapp.Fragments.CowinFragment;
import com.projectfinalyear.covapp.Fragments.HomeFragment;
import com.projectfinalyear.covapp.Fragments.PrivacyPolicy;
import com.projectfinalyear.covapp.Fragments.WhoFragment;
import com.projectfinalyear.covapp.Fragments.doctor_registration;
import com.projectfinalyear.covapp.Fragments.prediction;
import com.projectfinalyear.covapp.Utility.NeTWorkChange;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import hotchemi.android.rate.AppRate;
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DuoDrawerLayout drawerLayout;
    NeTWorkChange neTWorkChange = new NeTWorkChange();
    private int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(1)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);


        AppUpdateManager updateManager = AppUpdateManagerFactory.create(MainActivity.this);
        Task<AppUpdateInfo> appUpdateInfoTask = updateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
                {
                    try {
                        updateManager.startUpdateFlowForResult(result,AppUpdateType.IMMEDIATE, MainActivity.this, REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace(); } }
            }
        });

    }
    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        drawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        View contentView = drawerLayout.getContentView();
        View menuView = drawerLayout.getMenuView();

        LinearLayout ll_Home = menuView.findViewById(R.id.ll_Home);
        LinearLayout ll_Profile = menuView.findViewById(R.id.ll_moreInfo);
        LinearLayout ll_MoreApps = menuView.findViewById(R.id.ll_MoreApps);
        //LinearLayout ll_Share = menuView.findViewById(R.id.ll_share);
       LinearLayout ll_aarogya = menuView.findViewById(R.id.ll_aarogya);
        LinearLayout ll_exit = menuView.findViewById(R.id.ll_exit);
        LinearLayout ll_coWin = menuView.findViewById(R.id.ll_CoWin);
        //LinearLayout ll_privacyPolicy  = menuView.findViewById(R.id.ll_privacyPolicy);
        LinearLayout ll_doctor_registration  = menuView.findViewById(R.id.ll_doctor_registration);



        ll_Home.setOnClickListener(this);
        ll_Profile.setOnClickListener(this);
        //ll_Share.setOnClickListener(this);
      ll_aarogya.setOnClickListener(this);
        ll_exit.setOnClickListener(this);
        ll_coWin.setOnClickListener(this);
        //ll_privacyPolicy.setOnClickListener(this);
        ll_MoreApps.setOnClickListener(this);
        ll_doctor_registration.setOnClickListener(this);


        replace(new HomeFragment(), "Home");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_Home:
                replace(new HomeFragment(),"Home");
                break;

            case R.id.ll_moreInfo:
                replace(new WhoFragment(),"favourite");
                break;
/*
            case R.id.ll_privacyPolicy:
                replace(new PrivacyPolicy(),"Setting");
                break;
                */
            case R.id.ll_CoWin:
                replace(new CowinFragment(),"CoWin");
                break;
/*
            case R.id.ll_share:
                try
                {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "Share Texting");
                    String shareMsg = "https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID;
                    intent.putExtra(Intent.EXTRA_TEXT, shareMsg);
                    startActivity(Intent.createChooser(intent, "ShareVia"));
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
*/
            /*case R.id.ll_MoreApps:

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"));
                startActivity(intent);

                break;*/
            case R.id.ll_MoreApps:

                replace(new prediction(),"prediction");
                break;

            case R.id.ll_doctor_registration:

                replace(new doctor_registration(),"doctor_registration");
                break;

            case R.id.ll_aarogya:

                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=nic.goi.aarogyasetu"));
                startActivity(intent1);

                break;




            case R.id.ll_exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Exit");
                builder.setIcon(R.drawable.ic_baseline_exit_to_app_24);
                builder.setMessage("Are You Sure Want to Exit?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                            }
                        }).show();

                break;

        }
        drawerLayout.closeDrawer();
    }

    private void replace(Fragment fragment, String s) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.addToBackStack(s);
        transaction.commit();
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit");
        builder.setIcon(R.drawable.ic_baseline_exit_to_app_24);
        builder.setMessage("Are You Sure Want to Exit?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                }).show();    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(neTWorkChange, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(neTWorkChange);
        super.onStop();
    }
}
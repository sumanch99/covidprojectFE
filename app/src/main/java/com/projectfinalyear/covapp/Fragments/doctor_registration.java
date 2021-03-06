/*package com.projectfinalyear.covapp.Fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.projectfinalyear.covapp.R;

public class doctor_registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);
    }
}*/

/*package com.projectfinalyear.covapp.Fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.projectfinalyear.covapp.R;

public class prediction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);
    }
}
*/
package com.projectfinalyear.covapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.projectfinalyear.covapp.MainActivity;
import com.projectfinalyear.covapp.R;

public class doctor_registration extends Fragment {

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_doctor_registration, container, false);


        webView = (WebView) view.findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://covapp-ind.web.app/register-doctor");

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        return view;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent intent =  new Intent(getContext(), MainActivity.class);
            getContext().startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
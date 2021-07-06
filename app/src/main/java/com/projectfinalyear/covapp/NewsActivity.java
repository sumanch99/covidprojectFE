package com.projectfinalyear.covapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.projectfinalyear.covapp.Adapters.NewsAdapter;
import com.projectfinalyear.covapp.Models.CategoryModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    private ArrayList<CategoryModel> list;
    private RecyclerView recyclerView;
    FirebaseFirestore database;
    String catId;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        recyclerView = findViewById(R.id.news_fav);
        database = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading........");
        dialog.setCancelable(false);
        dialog.show();


        list = new ArrayList<>();
        catId = getIntent().getStringExtra("catId");

        NewsAdapter adapter = new NewsAdapter(list, this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        database.collection("Prevention")
                .document(catId)
                .collection("News")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                dialog.dismiss();
                list.clear();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots)
                {
                    CategoryModel statusModel = snapshot.toObject(CategoryModel.class);
                    list.add(statusModel);
                }
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(NewsActivity.this, "Data Not found", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
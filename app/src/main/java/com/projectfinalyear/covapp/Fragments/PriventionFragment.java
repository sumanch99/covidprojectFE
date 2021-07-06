package com.projectfinalyear.covapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projectfinalyear.covapp.Adapters.PreventionAdapter;
import com.projectfinalyear.covapp.Models.CategoryModel;
import com.projectfinalyear.covapp.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class PriventionFragment extends Fragment {

    private List<CategoryModel> list;
    private RecyclerView recyclerView;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_privention, container, false);

        database = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading........");
        dialog.setCancelable(false);
        dialog.show();

        recyclerView = view.findViewById(R.id.recyclerview__sub_category);

        list = new ArrayList<>();
        List<CategoryModel> list = new ArrayList<>();

        PreventionAdapter categoryAdapter = new PreventionAdapter(list, getContext());
        recyclerView.setAdapter(categoryAdapter);

        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoryAdapter);

        database.collection("Prevention").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                list.clear();
                dialog.dismiss();
                for (DocumentSnapshot snapshot : value.getDocuments())
                {
                    CategoryModel categoryModel = snapshot.toObject(CategoryModel.class);
                    categoryModel.setCategoryId(snapshot.getId());
                    list.add(categoryModel);
                }
                categoryAdapter.notifyDataSetChanged();
            }
        });



        return view;
    }
}
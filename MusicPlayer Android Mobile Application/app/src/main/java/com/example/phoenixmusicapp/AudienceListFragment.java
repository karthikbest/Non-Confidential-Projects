package com.example.phoenixmusicapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//Developed by: Saivarun Bandari

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;



import com.example.phoenixmusicapp.Adapter.AudienceAdapter;

import com.example.phoenixmusicapp.model.User;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AudienceListFragment extends Fragment {

    private RecyclerView recyclerView;
    private AudienceAdapter adapter;
    private ArrayList<User> audienceArrayList;
    private DatabaseManager databaseManager;
    private CardView cardView;

    public static AudienceListFragment newInstance() {
        return new AudienceListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audience_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewAudience);
        databaseManager = new DatabaseManager(getActivity());
        databaseManager.open();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        audienceArrayList = new ArrayList<>();
        adapter = new AudienceAdapter(getActivity(), audienceArrayList);
        recyclerView.setAdapter(adapter);
        createListData();
        cardView = (CardView) view.findViewById(R.id.cvAudienceList);
        return view;
    }

    private void createListData() {
        //getting the list of users from user logs table
         ArrayList<User> audienceList = databaseManager.getAllAudience();
        for (User aL : audienceList) {
            audienceArrayList.add(aL);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}

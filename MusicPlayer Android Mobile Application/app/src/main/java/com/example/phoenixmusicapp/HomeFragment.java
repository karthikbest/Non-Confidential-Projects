package com.example.phoenixmusicapp;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.phoenixmusicapp.Adapter.AudienceAdapter;
import com.example.phoenixmusicapp.Adapter.SongAdapter;
import com.example.phoenixmusicapp.model.Song;
import com.example.phoenixmusicapp.model.User;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private ArrayList<Song> ArrayList;
    private DatabaseManager databaseManager;
    private CardView cardView;
    int userid;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.home_fragment, container, false);
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerHome);
        //initialise DB manager
        databaseManager = new DatabaseManager(getActivity());
        databaseManager.open();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList = new ArrayList<>();
        //add to adapter
        adapter = new SongAdapter(getActivity(), ArrayList);
        recyclerView.setAdapter(adapter);
        createListData(view);
        cardView = (CardView) view.findViewById(R.id.cvAudienceList);
        return view;
    }
    private void createListData(View view) {
        //getting the list of users from user logs table
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        userid = Integer.parseInt(sharedPreferences.getString("userId", ""));

        ArrayList<Song> sl = databaseManager.getSongsByUserSort(userid);
        for (Song aL : sl) {
            ArrayList.add(aL);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}

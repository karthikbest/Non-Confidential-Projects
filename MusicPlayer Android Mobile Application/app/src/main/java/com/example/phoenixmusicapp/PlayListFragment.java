
//Developed by: Saivarun Bandari

package com.example.phoenixmusicapp;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phoenixmusicapp.Adapter.CardAdapter;
import com.example.phoenixmusicapp.model.PlayList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class PlayListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private ArrayList<PlayList> playlistArrayList;
    private CardView cardView;
    private EditText edtPlaylistName;
    int userid;
    DatabaseManager dm;
    public static PlayListFragment newInstance() {
        return new PlayListFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_list_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        edtPlaylistName = (EditText) view.findViewById(R.id.edtPlaylistName);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        playlistArrayList = new ArrayList<>();
        dm = new DatabaseManager(getActivity());
        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        userid = Integer.parseInt(sharedPreferences.getString("userId", ""));
        adapter = new CardAdapter(getActivity(), playlistArrayList);
        recyclerView.setAdapter(adapter);
        createListData();
        cardView = (CardView) view.findViewById(R.id.cvPlaylist);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playlistname=edtPlaylistName.getText().toString();
                if(!playlistname.isEmpty()) {
                    dm.open();
                    dm.addPlaylist(playlistname,userid);
                    edtPlaylistName.setText("");
                    createListData();
                    Toast.makeText(getActivity(), "Playlist added successfully", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "please enter playlist name", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void createListData() {
        dm.open();
        ArrayList<PlayList> playlists=dm.getPlaylistByUser(userid);
        for (PlayList p : playlists) {
           // Toast.makeText(getActivity(),p.getPlayListID(), Toast.LENGTH_LONG).show();
            PlayList playlist = new PlayList(p.getplayListName(), p.getUserID(),p.getcreatedDate() ,p.getupdatedDate(),p.getPlayListID());
            playlistArrayList.add(p);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // switch (item.getItemId()) {
        //     case android.R.id.home:
        //         finish();
        //         break;
        // }
        return super.onOptionsItemSelected(item);
    }

}

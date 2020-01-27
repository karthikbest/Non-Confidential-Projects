
//Developed by: Pamy Ann Patrick

package com.example.phoenixmusicapp;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
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

import com.example.phoenixmusicapp.Adapter.AddSongAdapter;
import com.example.phoenixmusicapp.Adapter.SongAdapter;
import com.example.phoenixmusicapp.model.PlayList;
import com.example.phoenixmusicapp.model.Song;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class PlayListSongFragment extends Fragment {
 int playlistid;
    int userid;
    DatabaseManager dm;
    Cursor musiccursor;
    FloatingActionButton fab;
    List<Song> songs;
    private RecyclerView recyclerView;
    private AddSongAdapter adapter;
    private SongAdapter adapterplay;
    private ArrayList<Song> songsArrayList;
    private ArrayList<Song> songsPlayArrayList;
    private CardView cardView;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static PlayListSongFragment newInstance() {
        return new PlayListSongFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        playlistid = Integer.parseInt(sharedPreferences.getString("playlistid", ""));
       // Toast.makeText(getContext(), playlistid, Toast.LENGTH_SHORT).show();
        //SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        userid = Integer.parseInt(sharedPreferences.getString("userId", ""));

        dm = new DatabaseManager(this.getContext());
        dm.open();
        View view = inflater.inflate(R.layout.play_list_song_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.playListSongRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        songsArrayList = new ArrayList<>();
        songsPlayArrayList = new ArrayList<>();



        loadfromDB();
        // for (Song s : songs) {
        //     Song song = new Song(s.getName(),s.getAddedDate(), s.getPath());
        //   songsArrayList.add(s);
        // }

        cardView = (CardView) view.findViewById(R.id.cvSongs);
        fab = (FloatingActionButton) view.findViewById(R.id.playListSongAddButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadSongs();
                fab.hide();
            }
        });
        //cardView.
        return view;
     //   return inflater.inflate(R.layout.play_list_song_fragment, container, false);
    }
    void loadfromDB() {
        dm.open();
        songsPlayArrayList = new ArrayList<>();
        ArrayList<Song> songs = dm.getSongsByPlaylist(playlistid);
        if (songs != null)
        {
            for (Song p : songs) {
                // Toast.makeText(getActivity(),p.getPlayListID(), Toast.LENGTH_LONG).show();
                //String Name,String AddedDate, String Path
                Song songp = new Song(p.getName(), p.getAddedDate(), p.getPath());
                songsPlayArrayList.add(songp);
            }
            adapterplay = new SongAdapter(getActivity(), songsPlayArrayList);
            recyclerView.setAdapter(adapterplay);
            adapterplay.notifyDataSetChanged();
        }
    }
    void loadSongs(){
        if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {
            ArrayList<HashMap<String,String>> songList=getPlayList("/storage/emulated/0/Android/songs/");
            adapter = new AddSongAdapter(getActivity(), songsArrayList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(getActivity(), "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
    ArrayList<HashMap<String,String>> getPlayList(String rootPath) {
        ArrayList<HashMap<String,String>> fileList = new ArrayList<>();

        try {
            File rootFolder = new File(rootPath);
            File[] files = rootFolder.listFiles(); //here you will get NPE if directory doesn't contains  any file,handle it like this.
            //  Toast.makeText(getActivity(), files.length, Toast.LENGTH_SHORT).show();
            for (File file : files) {
                if (file.isDirectory()) {
                    if (getPlayList(file.getAbsolutePath()) != null) {
                        fileList.addAll(getPlayList(file.getAbsolutePath()));
                    } else {
                        break;
                    }
                } else if (file.getName().endsWith(".mp3")) {
                    Song s=new Song(file.getName(), DateFormat.getDateTimeInstance().format(new Date()), file.getAbsolutePath());
                    songsArrayList.add(s);
                }
            }
            return fileList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}

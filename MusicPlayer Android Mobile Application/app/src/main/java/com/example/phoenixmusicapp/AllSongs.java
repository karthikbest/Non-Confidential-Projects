//Developed by: Pamy Ann Patrick
package com.example.phoenixmusicapp;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.phoenixmusicapp.Adapter.SongAdapter;
import com.example.phoenixmusicapp.model.PlayList;
import com.example.phoenixmusicapp.model.Song;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class AllSongs<Login> extends Fragment {

    private String mParam1;
    private String mParam2;
    Cursor musiccursor;
    List<Song> songs;
    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private ArrayList<Song> songsArrayList;
    private CardView cardView;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public AllSongs() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_songs, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewSongs);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        songsArrayList = new ArrayList<>();
        adapter = new SongAdapter(getActivity(), songsArrayList);
        recyclerView.setAdapter(adapter);
        loadSongs();
       // for (Song s : songs) {
       //     Song song = new Song(s.getName(),s.getAddedDate(), s.getPath());
         //   songsArrayList.add(s);
       // }
        adapter.notifyDataSetChanged();
        cardView = (CardView) view.findViewById(R.id.cvSongs);
        //cardView.
        return view;
    }
    public static AllSongs newInstance() {
        return new AllSongs();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    void loadSongs(){
        //loading songs from the external location
    if (checkPermissionREAD_EXTERNAL_STORAGE(getActivity())) {
        ArrayList<HashMap<String,String>> songList=getPlayList("/storage/emulated/0/Android/songs/");
    }
}
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            //getting permission to read from external storage
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
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
        //Alert message that permission is necessary
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
        //Verifying if permission is granted or not
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

            for (File file : files) {
                if (file.isDirectory()) {
                    if (getPlayList(file.getAbsolutePath()) != null) {
                        //Getting the list of songs from external path
                            fileList.addAll(getPlayList(file.getAbsolutePath()));
                    } else {
                        break;
                    }
                } else if (file.getName().endsWith(".mp3")) {
                    Song s=new Song(file.getName(),DateFormat.getDateTimeInstance().format(new Date()), file.getAbsolutePath());
                    songsArrayList.add(s);
                }
            }
            return fileList;
        } catch (Exception e) {
            return null;
        }
    }


}

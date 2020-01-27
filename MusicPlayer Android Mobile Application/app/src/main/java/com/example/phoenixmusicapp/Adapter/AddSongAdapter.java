package com.example.phoenixmusicapp.Adapter;

import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoenixmusicapp.AudienceListFragment;
import com.example.phoenixmusicapp.DatabaseManager;
import com.example.phoenixmusicapp.PlayListSongFragment;
import com.example.phoenixmusicapp.PlaySong;
import com.example.phoenixmusicapp.R;
import com.example.phoenixmusicapp.RatingFragment;
import com.example.phoenixmusicapp.model.PlayList;
import com.example.phoenixmusicapp.model.Song;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AddSongAdapter extends RecyclerView.Adapter<AddSongAdapter.AddSongHolder> {


    private Context context;
    private ArrayList<Song> songs;
    private Fragment fragment;
    int playlistid;
    int userid;
    public AddSongAdapter(Context context, ArrayList<Song> Songs) {
        this.context = context;
        this.songs = Songs;
    }

    public class AddSongHolder extends RecyclerView.ViewHolder {

        public LinearLayout holderLayout;
        private TextView txtName, txtAddedDate;
        private Button button;

        AddSongHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtSongName);
            button = itemView.findViewById(R.id.btnPlay);
            holderLayout = (LinearLayout) itemView.findViewById(R.id.songListItemLayout);
        }

        void setDetails(Song song) {
            txtName.setText(song.getName());

        }
    }

    @NonNull
    @Override
    public AddSongHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.songs_card, parent, false);
        return new AddSongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddSongHolder holder, int position) {
        final Song song = songs.get(position);
        holder.setDetails(song);
        holder.holderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSong(view,song);

            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    //Loading each fragment based on navigation bar item selected
    private void saveSong( View view,Song song) {
        // load fragment
        DatabaseManager dm = new DatabaseManager(view.getContext());
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        playlistid = Integer.parseInt(sharedPreferences.getString("playlistid", ""));
        userid = Integer.parseInt(sharedPreferences.getString("userId", ""));
        userid = Integer.parseInt(sharedPreferences.getString("userId", ""));
        dm.open();
        dm.addSong( song.getName(),song.getPath(),userid,playlistid);
        Toast.makeText(view.getContext(),"Song added to the playlist", Toast.LENGTH_LONG).show();
    }

    public void audioPlayer(String path){
        //set up MediaPlayer
        MediaPlayer mp = new MediaPlayer();

        try {
            mp.setDataSource(path);
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



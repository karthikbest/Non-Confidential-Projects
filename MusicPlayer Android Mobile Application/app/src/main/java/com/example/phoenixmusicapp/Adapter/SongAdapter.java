package com.example.phoenixmusicapp.Adapter;

import android.content.ContentUris;
import android.content.Context;
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
import com.example.phoenixmusicapp.PlayListSongFragment;
import com.example.phoenixmusicapp.PlaySong;
import com.example.phoenixmusicapp.R;
import com.example.phoenixmusicapp.RatingFragment;
import com.example.phoenixmusicapp.model.PlayList;
import com.example.phoenixmusicapp.model.Song;

import java.io.File;
import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {


    private Context context;
    private ArrayList<Song> songs;
    private Fragment fragment;

    public SongAdapter(Context context, ArrayList<Song> Songs) {
        this.context = context;
        this.songs = Songs;
    }

    public class SongHolder extends RecyclerView.ViewHolder {

        public LinearLayout holderLayout;
        private TextView txtName, txtAddedDate;
        private Button button;

        SongHolder(View itemView) {
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
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.songs_card, parent, false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SongHolder holder, int position) {
        final Song song = songs.get(position);
        holder.setDetails(song);
        holder.holderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new PlaySong();
                Bundle args = new Bundle();
                args.putString("path", song.getPath());
                fragment.setArguments(args);
                loadFragment(fragment, view);

            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    //Loading each fragment based on navigation bar item selected
    private void loadFragment(Fragment fragment, View view) {
        // load fragment

        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    
}



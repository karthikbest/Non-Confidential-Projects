
//Developed by: Swathi Liz Thomas

package com.example.phoenixmusicapp;

import androidx.lifecycle.ViewModelProviders;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class PlaySong extends Fragment {



    public static PlaySong newInstance() {
        return new PlaySong();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.play_song_fragment, container, false);
        VideoView video = (VideoView) view.findViewById(R.id.songView);
        //Creating a media controller
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(video);

        String value = getArguments().getString("path");
        //Specifying the location of the video file
        Uri uriObject = Uri.parse(value);

        //Setting up the media controller and URI and then playing the video
        video.setMediaController(mediaController);
        video.setVideoURI(uriObject);
        video.requestFocus();
        video.start();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}


//Developed by: Swathi Liz Thomas

package com.example.phoenixmusicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class RatingFragment extends Fragment {

    private RatingBar ratingBar;
    private Button submitButton;
    int userId;
    private Fragment fragment;
    DatabaseManager dm;
    String rate;
    View view;
    public static RatingFragment newInstance() {
        return new RatingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rating_fragment, container, false);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        submitButton = (Button) view.findViewById(R.id.btnSubmit);
        dm = new DatabaseManager(this.getContext());
        dm.open();
        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        userId = Integer.parseInt(sharedPreferences.getString("userId", ""));
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                Toast.makeText(getActivity(),
                        String.valueOf(ratingBar.getRating()),
                        Toast.LENGTH_SHORT).show();

                rate=Float.toString(ratingBar.getRating());



            }


        });
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dm.updateRating(userId,rate);
                fragment = new HomeFragment();
                loadFragment(fragment,view);
            }

        });

        return view;
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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}

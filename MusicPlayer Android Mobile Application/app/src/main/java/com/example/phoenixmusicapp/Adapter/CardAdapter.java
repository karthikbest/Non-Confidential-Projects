package com.example.phoenixmusicapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoenixmusicapp.PlayListSongFragment;
import com.example.phoenixmusicapp.R;
import com.example.phoenixmusicapp.model.PlayList;

import java.util.ArrayList;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.PlayListHolder> {

    private Context context;
    private ArrayList<PlayList> playlists;
    private Fragment fragment;

    public class PlayListHolder extends RecyclerView.ViewHolder {
        public LinearLayout holderLayout;
        private TextView txtName, txtLastUpdated,txtID;

        PlayListHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtID = itemView.findViewById(R.id.txtID);
            holderLayout = (LinearLayout) itemView.findViewById(R.id.holderLayout);
        }

        void setDetails(PlayList playlist) {
            txtName.setText(playlist.getplayListName());
            txtID.setText(playlist.getPlayListIDStr());
        }
    }


    public CardAdapter(Context context, ArrayList<PlayList> playlists) {
        this.context = context;
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public PlayListHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.paylist_card, parent, false);
        return new PlayListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayListHolder holder, int position) {
        final PlayList playlist = playlists.get(position);
        holder.setDetails(playlist);
        holder.holderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showPopupMenu(holder.holderLayout);
                fragment = new PlayListSongFragment();
                loadFragment(fragment, view, playlist);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }


    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }


    //Loading each fragment based on navigation bar item selected
    private void loadFragment(Fragment fragment, View view, PlayList item) {
        // load fragment
        //Toast.makeText(view.getContext(), item.getPlayListIDStr(), Toast.LENGTH_SHORT).show();
       // Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("sharedPreferences",MODE_PRIVATE); //No other application can modify

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("playlistid", Integer.toString(item.getPlayListID()));
        editor.commit();
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.playNext:
                    Toast.makeText(context, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}

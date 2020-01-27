package com.example.phoenixmusicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phoenixmusicapp.R;
import com.example.phoenixmusicapp.model.User;

import java.util.ArrayList;

public class AudienceAdapter extends RecyclerView.Adapter<AudienceAdapter.AudienceListHolder> {

    private Context context;
    private ArrayList<User> audiencelists;

    public AudienceAdapter(Context context, ArrayList<User> audiencelists) {
        this.context = context;
        this.audiencelists = audiencelists;
    }

    @NonNull
    @Override
    public AudienceListHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.audience_card, parent, false);
        return new AudienceListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudienceListHolder holder, int position) {
        User audiencelist = audiencelists.get(position);
        holder.setDetails(audiencelist);
    }

    @Override
    public int getItemCount() {
        return audiencelists.size();
    }

    class AudienceListHolder extends RecyclerView.ViewHolder {

        private TextView txtUserId, txtFirstName, txtLastName, txtAppLastUsed, txtRating;

        AudienceListHolder(@NonNull View itemView) {
            super(itemView);
            txtUserId = itemView.findViewById(R.id.txtuserId);
            txtFirstName = itemView.findViewById(R.id.txtFirstName);
            txtLastName = itemView.findViewById(R.id.txtLastName);
            txtAppLastUsed = itemView.findViewById(R.id.txtLastUsedApp);
            txtRating = itemView.findViewById(R.id.txtRating);
        }

        void setDetails(User audiencelist) {
            txtUserId.setText("User ID : "+audiencelist.getUserID());
            txtFirstName.setText("First Name : "+audiencelist.getFirstName());
            txtLastName.setText("Last Name : "+audiencelist.getLastName());
            txtAppLastUsed.setText("Last used App on : "+audiencelist.getAppLastUsed());
            txtRating.setText("Rating : "+audiencelist.getRating());
        }
    }
}

//Developed by: Swathi Liz Thomas, Pamy Ann Patrick

package com.example.phoenixmusicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// for Swathi to create home page with menu
public class MainActivity extends AppCompatActivity {

    private DatabaseManager dbManager;
    private Fragment fragment;
    SharedPreferences sharedPreferences;
    String role;
    Boolean alVisible = false;
    MenuItem audienceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Loading home page
        fragment = new HomeFragment();
        loadFragment(fragment);

        //Setting up listener for items on navigation bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.navigation_songs:
                        fragment = new AllSongs();
                        loadFragment(fragment);
                        break;
                    case R.id.navigation_my_playlist:
                        fragment = new PlayListFragment();
                        loadFragment(fragment);
                        break;
                        default:
                            break;

                }
                return true;
            }
        });

        //creating instance of database manager
        dbManager = new DatabaseManager(this);
        //calling open function in the class
        dbManager.open();
    }

    public boolean onPrepareOptionsMenu(Menu menu) {

        String role = "";
        sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        role = sharedPreferences.getString("role", "");
        audienceList = menu.findItem(R.id.audienceList);
        if(role.equals("admin"))
        {
            audienceList.setVisible(true);
        }
        else
        {
            audienceList.setVisible(false);
        }
        return true;
    }

    //Inflating the menu resource
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    //Responding to menu item selection
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.audienceList:
                fragment = new AudienceListFragment();
                loadFragment(fragment);
                return true;
            case R.id.rating:
                fragment = new RatingFragment();
                loadFragment(fragment);
                return true;
            case R.id.logout:
                SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE); //No other application can modify

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userId", "null");
                editor.apply();
                Intent userLogoutIntent = new Intent(MainActivity.this
                        , LoginActivity.class);

                startActivity(userLogoutIntent);


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Loading each fragment based on navigation bar item selected
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

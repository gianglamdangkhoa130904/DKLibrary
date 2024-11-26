package com.example.finalvalue.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.finalvalue.Customer.HomeFragments.ExploreFragment;
import com.example.finalvalue.Customer.HomeFragments.MainFragment;
import com.example.finalvalue.Customer.HomeFragments.UserFragment;
import com.example.finalvalue.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottom_nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottom_nav = findViewById(R.id.bottom_nav);
        setFragment(new MainFragment());
        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.main_home){
                    setFragment(new MainFragment());
                    return true;
                }
                if(item.getItemId() == R.id.explore_home){
                    setFragment(new ExploreFragment());
                    return true;
                }
                if(item.getItemId() == R.id.user_home){
                    setFragment(new UserFragment());
                    return true;
                }
                return false;
            }
        });
    }
    protected  void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }

}
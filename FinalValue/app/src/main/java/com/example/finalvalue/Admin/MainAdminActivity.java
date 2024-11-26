package com.example.finalvalue.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.finalvalue.Admin.MainAdminFragments.AdminAccountFragment;
import com.example.finalvalue.Admin.MainAdminFragments.AdminBookFragment;
import com.example.finalvalue.Admin.MainAdminFragments.AdminCategoryBookFragment;
import com.example.finalvalue.Admin.MainAdminFragments.AdminCategoryFragment;
import com.example.finalvalue.Admin.MainAdminFragments.AdminChapterFragment;
import com.example.finalvalue.Admin.MainAdminFragments.AdminCustomerFragment;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainAdminActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        bottomNav = findViewById(R.id.admin_bottom_nav);
        setFragment(new AdminAccountFragment());
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.admin_account_btm_nav){
                    setFragment(new AdminAccountFragment());
                } else if (item.getItemId() == R.id.admin_customer_btm_nav) {
                    setFragment(new AdminCustomerFragment());
                }
                else if (item.getItemId() == R.id.admin_book_btm_nav) {
                    setFragment(new AdminBookFragment());
                }
                else if (item.getItemId() == R.id.admin_category_btm_nav) {
                    setFragment(new AdminCategoryFragment());
                }
                else if (item.getItemId() == R.id.admin_bookCategory_btm_nav) {
                    setFragment(new AdminCategoryBookFragment());
                }
                return true;
            }
        });
    }
    protected  void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.admin_fm_container, fragment);
        fragmentTransaction.commit();
    }
}
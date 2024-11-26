package com.example.finalvalue.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.finalvalue.Customer.Adapter.ViewPagerAdapter;
import com.example.finalvalue.Customer.CategoryFragments.ListBookByCategoryFragment;
import com.example.finalvalue.Model.CategoryModel;
import com.example.finalvalue.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    TabLayout categoryTablayout;
    ViewPager categoryViewpager;
    Button back;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryTablayout = findViewById(R.id.category_tablayout);
        categoryViewpager = findViewById(R.id.category_viewpager);
        back = findViewById(R.id.category_back);
        categoryTablayout.setupWithViewPager(categoryViewpager);
        db = new DBHelper(CategoryActivity.this);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        Cursor cursor = db.get_All_category();
        if(cursor != null){
            while(cursor.moveToNext()){
                viewPagerAdapter.addFragment(new ListBookByCategoryFragment(cursor.getString(0)),cursor.getString(1));
            }
        }
        categoryViewpager.setAdapter(viewPagerAdapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
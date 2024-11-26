package com.example.finalvalue.Admin.MainAdminFragments;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalvalue.Admin.Adapter.CategoryBookAdapter;
import com.example.finalvalue.Admin.MainAdminFragments.AddDetailBook.CategoryOfBookFragment;
import com.example.finalvalue.Admin.MainAdminFragments.AddDetailBook.ChapterOfBookFragment;
import com.example.finalvalue.Admin.MainAdminFragments.AddDetailBook.PageOfBookFragment;
import com.example.finalvalue.Customer.Adapter.ViewPagerAdapter;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class AdminCategoryBookFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new CategoryOfBookFragment(), "Category");
        viewPagerAdapter.addFragment(new ChapterOfBookFragment(), "Chapter");
        viewPagerAdapter.addFragment(new PageOfBookFragment(), "Pages");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toolbar actionBar = (Toolbar) getActivity().findViewById(R.id.materialToolbarAdmin);
        actionBar.setTitle("Attribute Of Book Management");
        actionBar.setBackgroundColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(actionBar);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new CategoryOfBookFragment(), "Category");
        viewPagerAdapter.addFragment(new ChapterOfBookFragment(), "Chapter");
        viewPagerAdapter.addFragment(new PageOfBookFragment(), "Pages");
        return inflater.inflate(R.layout.fragment_admin_category_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.attribute_tablyout);
        viewPager = view.findViewById(R.id.attribute_viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(viewPagerAdapter);
    }
}
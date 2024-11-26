package com.example.finalvalue.Customer.HomeFragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalvalue.Customer.Adapter.ViewPagerAdapter;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Customer.InforUserActivity;
import com.example.finalvalue.Customer.SearchActivity;
import com.example.finalvalue.Customer.UserFragments.HistoryFragment;
import com.example.finalvalue.Customer.UserFragments.LoverFragment;
import com.example.finalvalue.R;
import com.example.finalvalue.Customer.SettingActivity;
import com.google.android.material.tabs.TabLayout;

public class UserFragment extends Fragment {
    TextView getName, getID, go_to_search;
    ImageView getimage;
    LinearLayout goto_infor;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new HistoryFragment(), "Lịch sử");
        viewPagerAdapter.addFragment(new LoverFragment(), "Yêu thích");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toolbar actionBar = (Toolbar) getActivity().findViewById(R.id.materialToolbar_home);
        actionBar.setTitle("");
        actionBar.setBackgroundColor(Color.parseColor("#DC8686"));
        ((AppCompatActivity) getActivity()).setSupportActionBar(actionBar);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new HistoryFragment(), "Lịch sử");
        viewPagerAdapter.addFragment(new LoverFragment(), "Yêu thích");
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getName = (view).findViewById(R.id.getName);
        getID = (view).findViewById(R.id.getID);
        getimage = view.findViewById(R.id.getimage);
        goto_infor = view.findViewById(R.id.goto_infor);
        tabLayout = view.findViewById(R.id.user_tablayout);
        viewPager = view.findViewById(R.id.user_viewpager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(viewPagerAdapter);
        go_to_search = view.findViewById(R.id.go_to_search);
        go_to_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        goto_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), InforUserActivity.class);
                startActivity(a);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
        String nameCus = sharedPreferences.getString("NameCus", "");
        int IDCus = sharedPreferences.getInt("IDCus", 0);

        if(nameCus.length() >= 10){
            String S = "";
            for (int i = 0; i< 10;i++){
                S += nameCus.split("")[i];
            }
            getName.setText(S + "...");
            getID.setText("ID: " + IDCus);
        }else {
            getName.setText(nameCus);
            getID.setText("ID: " + IDCus);
        }
        DBHelper dbHelper = new DBHelper(getActivity());
        Cursor cursor = dbHelper.get_customer_by_ID(IDCus); //
        if(cursor == null)
            Toast.makeText(getActivity(), "No data.", Toast.LENGTH_SHORT).show();
        else{
            while(cursor.moveToNext()){
                byte[] imagebyte = cursor.getBlob(6);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
                getimage.setImageBitmap(bitmap);
            }
            cursor.close();
        }
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_user, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.setting_toolbar_home) {
            Intent b = new Intent(getActivity(), SettingActivity.class);
            startActivity(b);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
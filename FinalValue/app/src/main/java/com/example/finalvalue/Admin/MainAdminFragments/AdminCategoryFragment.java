package com.example.finalvalue.Admin.MainAdminFragments;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalvalue.Admin.Adapter.CategoryAdminAdapter;
import com.example.finalvalue.Admin.AddActivity.AddCategoryActivity;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.CategoryModel;
import com.example.finalvalue.Model.CustomerModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class AdminCategoryFragment extends Fragment {
    Button gotoAdd;
    DBHelper db;
    RecyclerView recyclerView;
    ArrayList<CategoryModel> arrCategory;
    CategoryAdminAdapter categoryAdminAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toolbar actionBar = (Toolbar) getActivity().findViewById(R.id.materialToolbarAdmin);
        actionBar.setTitle("Category Management");
        actionBar.setBackgroundColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(actionBar);
        return inflater.inflate(R.layout.fragment_admin_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcv_admin_category);
        gotoAdd = view.findViewById(R.id.btn_goto_addCategory);

        arrCategory = new ArrayList<>();
        db = new DBHelper(getActivity());
        store_data_to_list();
        categoryAdminAdapter = new CategoryAdminAdapter(getActivity(),arrCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(categoryAdminAdapter);
        gotoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), AddCategoryActivity.class);
                startActivity(a);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        arrCategory = new ArrayList<>();
        db = new DBHelper(getActivity());
        store_data_to_list();
        categoryAdminAdapter = new CategoryAdminAdapter(getActivity(),arrCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(categoryAdminAdapter);

    }

    void store_data_to_list(){
        Cursor cursor = db.get_All_category();
        if(cursor == null)
            Toast.makeText(getActivity(), "No data.", Toast.LENGTH_SHORT).show();
        else{
            while(cursor.moveToNext()){
                CategoryModel categoryModel = new CategoryModel(cursor.getString(0),
                                                                cursor.getString(1));
                arrCategory.add(categoryModel);
            }
            cursor.close();
        }
    }
}
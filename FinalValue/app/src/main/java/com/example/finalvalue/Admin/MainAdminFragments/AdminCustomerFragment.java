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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalvalue.Admin.Adapter.CustomerAdapter;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.CustomerModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class AdminCustomerFragment extends Fragment {
    DBHelper db;
    RecyclerView recyclerView;
    CustomerAdapter customerAdapter;
    ArrayList<CustomerModel> arrCustomer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toolbar actionBar = (Toolbar) getActivity().findViewById(R.id.materialToolbarAdmin);
        actionBar.setTitle("Customer Management");
        actionBar.setBackgroundColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(actionBar);
        return inflater.inflate(R.layout.fragment_admin_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.list_customer_rcv);
        arrCustomer = new ArrayList<>();
        db = new DBHelper(getActivity());
        store_data_to_list();
        customerAdapter = new CustomerAdapter(getActivity(), arrCustomer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(customerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        arrCustomer = new ArrayList<>();
        db = new DBHelper(getActivity());
        store_data_to_list();
        customerAdapter = new CustomerAdapter(getActivity(), arrCustomer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(customerAdapter);
    }

    void store_data_to_list(){
        Cursor cursor = db.readAllDataCustomer(); //
        if(cursor == null)
            Toast.makeText(getActivity(), "No data.", Toast.LENGTH_SHORT).show();
        else{
            while(cursor.moveToNext()){
                byte[] imagebyte = cursor.getBlob(6);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
                CustomerModel customerModel = new CustomerModel(Integer.valueOf(cursor.getString(0)),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(1),
                        cursor.getString(2),
                        bitmap);
                arrCustomer.add(customerModel);
            }
            cursor.close();
        }
    }
}
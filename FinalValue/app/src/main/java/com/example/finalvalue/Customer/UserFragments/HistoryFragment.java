package com.example.finalvalue.Customer.UserFragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalvalue.Customer.Adapter.ViewHistoryAdapter;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    int IDCus;
    ArrayList<String> arrBookName;
    ArrayList<Integer> arrViewer;
    ArrayList<Integer> arrBookID;
    DBHelper db;
    RecyclerView recyclerView;
    ViewHistoryAdapter viewHistoryAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arrBookName = new ArrayList<>();
        arrViewer = new ArrayList<>();
        arrBookID = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rcv_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        db = new DBHelper(getActivity());

    }
    public void get_data(){
        Cursor cursor = db.get_viewer_byIDCus(IDCus);
        if(cursor != null){
            while (cursor.moveToNext()){
                arrBookID.add(cursor.getInt(1));
                arrBookName.add(cursor.getString(2));
                arrViewer.add(cursor.getInt(3));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
        IDCus = sharedPreferences.getInt("IDCus", 0);
        get_data();
        viewHistoryAdapter = new ViewHistoryAdapter(getActivity(), arrBookName, arrViewer, arrBookID);
        recyclerView.setAdapter(viewHistoryAdapter);
    }
}
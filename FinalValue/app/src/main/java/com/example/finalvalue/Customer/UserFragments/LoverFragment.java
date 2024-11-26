package com.example.finalvalue.Customer.UserFragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalvalue.Customer.Adapter.ListTitleHomeAdapter;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.R;

import java.util.ArrayList;


public class LoverFragment extends Fragment {
    int IDCus;
    DBHelper db;
    RecyclerView recyclerView;
    ListTitleHomeAdapter listTitleHomeAdapter;
    ArrayList<BookModel> arrBook;
    ArrayList<String> arrName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arrBook = new ArrayList<>();
        arrName = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rcv_love);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        db = new DBHelper(getActivity());
    }
    public void get_data(){
        Cursor cursor = db.get_ALl_loveBook(IDCus);
        if(cursor != null){
            while (cursor.moveToNext()){
                arrName.add(cursor.getString(1));
            }
        }
        for(int i = 0; i < arrName.size(); i++){
            cursor = db.get_Book_by_name(arrName.get(i));
            if(cursor != null){
                while(cursor.moveToNext()){
                    byte[] imagebyte = cursor.getBlob(5);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
                    BookModel bookModel = new BookModel(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            bitmap, cursor.getString(6));
                    arrBook.add(bookModel);
                }
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);
        IDCus = sharedPreferences.getInt("IDCus", 0);
        get_data();
        listTitleHomeAdapter = new ListTitleHomeAdapter(getActivity(), arrBook, R.layout.row_love, R.id.main_love_layout, R.id.get_name_love, R.id.get_image_love);
        recyclerView.setAdapter(listTitleHomeAdapter);
    }
}
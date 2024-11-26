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

import com.example.finalvalue.Admin.Adapter.BookAdminAdapter;
import com.example.finalvalue.Admin.Adapter.CategoryAdminAdapter;
import com.example.finalvalue.Admin.AddActivity.AddBookActivity;
import com.example.finalvalue.Admin.AddActivity.AddCategoryActivity;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.Model.CategoryModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class AdminBookFragment extends Fragment {
    Button gotoAdd;
    DBHelper db;
    RecyclerView recyclerView;
    ArrayList<BookModel> arrBook;
    BookAdminAdapter bookAdminAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toolbar actionBar = (Toolbar) getActivity().findViewById(R.id.materialToolbarAdmin);
        actionBar.setTitle("Book Management");
        actionBar.setBackgroundColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(actionBar);
        return inflater.inflate(R.layout.fragment_admin_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcv_admin_book);
        gotoAdd = view.findViewById(R.id.btn_goto_addBook);

        arrBook = new ArrayList<>();
        db = new DBHelper(getActivity());
        store_data_to_list();
        bookAdminAdapter = new BookAdminAdapter(getActivity(), arrBook);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(bookAdminAdapter);
        gotoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), AddBookActivity.class);
                startActivity(a);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        arrBook = new ArrayList<>();
        db = new DBHelper(getActivity());
        store_data_to_list();
        bookAdminAdapter = new BookAdminAdapter(getActivity(), arrBook);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(bookAdminAdapter);
    }

    void store_data_to_list(){
        Cursor cursor = db.get_All_book();
        if(cursor == null)
            Toast.makeText(getActivity(), "No data.", Toast.LENGTH_SHORT).show();
        else{
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
            cursor.close();
        }
    }
}
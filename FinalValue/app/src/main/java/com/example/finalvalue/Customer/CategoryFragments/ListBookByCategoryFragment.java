package com.example.finalvalue.Customer.CategoryFragments;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalvalue.Customer.Adapter.ListTitleHomeAdapter;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class ListBookByCategoryFragment extends Fragment {
    String categoryID;
    RecyclerView recyclerView;
    ListTitleHomeAdapter listTitleHomeAdapter;
    DBHelper dbHelper;
    ArrayList<BookModel> arrBook;
    public ListBookByCategoryFragment(String categoryid) {
        this.categoryID = categoryid;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_book_by_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcv_listbook_by_categoryID);
        dbHelper = new DBHelper(getActivity());
        arrBook = new ArrayList<>();
        store_data();
        listTitleHomeAdapter = new ListTitleHomeAdapter(getActivity(),arrBook,R.layout.row_customer_book2,R.id.main_book_home_layout2, R.id.get_name_book_home2, R.id.get_image_book_home2);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(listTitleHomeAdapter);
    }
    public void store_data(){
        Cursor cursor = dbHelper.get_book_by_categoryID(categoryID);
        if(cursor!=null){
            while (cursor.moveToNext()){
                byte[] imagebyte = cursor.getBlob(5);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
                BookModel bookModel = new BookModel(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        bitmap);
                arrBook.add(bookModel);
            }
        }
        else Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
    }
}
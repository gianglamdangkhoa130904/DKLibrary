package com.example.finalvalue.Admin.MainAdminFragments.AddDetailBook;

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
import android.widget.Toast;

import com.example.finalvalue.Admin.Adapter.ChapterBookAdapter;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class ChapterOfBookFragment extends Fragment {
    RecyclerView recyclerView;
    DBHelper dbHelper;
    ChapterBookAdapter chapterBookAdapter;
    ArrayList<BookModel> arrBook;
    public ChapterOfBookFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chapter_of_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcv_listbook_chapter);
        arrBook = new ArrayList<>();
        dbHelper = new DBHelper(getActivity());
        store_data();
    }

    @Override
    public void onResume() {
        super.onResume();
        arrBook = new ArrayList<>();
        store_data();
        chapterBookAdapter = new ChapterBookAdapter(getActivity(), arrBook);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(chapterBookAdapter);
    }

    public void store_data(){
        Cursor cursor = dbHelper.get_All_book();
        if(cursor != null){
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
            cursor.close();
        }
        else {
            Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
        }
    }
}
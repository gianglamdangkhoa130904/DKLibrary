package com.example.finalvalue.Customer.DetailFragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalvalue.Customer.Adapter.ListChapterAdapter;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.ChapterModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class ChapterDetailsFragment extends Fragment {
    RecyclerView recyclerView;
    ListChapterAdapter listChapterAdapter;
    ArrayList<ChapterModel> arrChap;
    DBHelper db;
    int bookID;
    public ChapterDetailsFragment(int bookID){
        this.bookID = bookID;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chapter_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.get_listchapter);
        arrChap = new ArrayList<>();
        db = new DBHelper(getActivity());
        store_data();
        listChapterAdapter = new ListChapterAdapter(getActivity(), arrChap);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(listChapterAdapter);
    }
    private void store_data(){
        Cursor cursor = db.get_chapter_by_bookID(bookID);
        if(cursor != null){
            while (cursor.moveToNext()){
                ChapterModel chapterModel = new ChapterModel(cursor.getInt(0)
                        ,cursor.getInt(1)
                        ,cursor.getInt(2)
                        ,cursor.getString(3));
                arrChap.add(chapterModel);
            }
            cursor.close();
        }
    }
}
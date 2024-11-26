package com.example.finalvalue.Admin.ListActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finalvalue.Admin.Adapter.ChapterAdapter;
import com.example.finalvalue.Admin.Adapter.PageChapAdapter;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.Model.ChapterModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class ListChapterActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ChapterModel> arrChapter;
    PageChapAdapter pageChapAdapter;
    DBHelper db;
    int idBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chapter);
        recyclerView = findViewById(R.id.rcv_chapter_pages);
        getAndSetIntent();
        db = new DBHelper(ListChapterActivity.this);
        arrChapter = new ArrayList<>();
        store_data();
        pageChapAdapter = new PageChapAdapter(ListChapterActivity.this, arrChapter);
        recyclerView.setLayoutManager(new GridLayoutManager(ListChapterActivity.this, 3));
        recyclerView.setAdapter(pageChapAdapter);
    }
    private void store_data() {
        Cursor cursor = db.get_chapter_by_bookID(idBook);
        if(cursor != null){
            while (cursor.moveToNext()){
                ChapterModel chapterModel = new ChapterModel(cursor.getInt(0)
                                                            ,cursor.getInt(1)
                                                            ,cursor.getInt(2)
                                                            ,cursor.getString(3));
                arrChapter.add(chapterModel);
            }
        }
        else Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
    }

    private void getAndSetIntent() {
        if(getIntent().hasExtra("book_id")){
            idBook = getIntent().getIntExtra("book_id",0);
        }
        else Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
    }
}
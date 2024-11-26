package com.example.finalvalue.Admin.AddActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalvalue.Admin.Adapter.ChapterAdapter;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.ChapterModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class AddChapterActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView get_nameBook, get_chapterNumber;
    EditText get_description;
    Button btn_add;
    String book_name;
    int book_id, chapNumber;
    DBHelper db;
    ChapterAdapter chapterAdapter;
    ArrayList<ChapterModel> arrChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chapter);
        get_nameBook = findViewById(R.id.get_add_chapter_bookName);
        get_chapterNumber= findViewById(R.id.get_add_chapter_chapterNumber);
        get_description = findViewById(R.id.get_add_chapter_descriptionChapter);
        btn_add = findViewById(R.id.btn_add_chapter);
        recyclerView = findViewById(R.id.rcv_chapter);

        arrChapter = new ArrayList<>();
        db = new DBHelper(AddChapterActivity.this);
        getAndSetIntent();
        get_ChapNumber();
        store_data();
        chapterAdapter = new ChapterAdapter(AddChapterActivity.this, arrChapter);
        recyclerView.setLayoutManager(new GridLayoutManager(AddChapterActivity.this, 3));
        recyclerView.setAdapter(chapterAdapter);
        get_chapterNumber.setText("Chapter "+ chapNumber);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmpty()){
                    if(checkFormat()){
                        ChapterModel chapterModel = new ChapterModel(book_id, chapNumber, get_description.getText().toString().trim());
                        db.add_chapter(chapterModel);
                        Toast.makeText(AddChapterActivity.this, "Thêm chương truyện thành công", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }
                }
            }
        });
    }

    private boolean checkFormat() {
        if(get_description.getText().toString().trim().length() > 1000){
            Toast.makeText(this, "Giới hạn mô tả nhỏ hơn 1000 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
    private void get_ChapNumber(){
        Cursor cursor = db.get_chapter_by_bookID(book_id);
        if(cursor == null){
            chapNumber = 1;
        }
        else {
            int count = 0;
            while (cursor.moveToNext()){
                count++;
            }
            chapNumber = count + 1;
        }
    }
    private boolean checkEmpty() {
        if(get_description.getText().toString().equals("")){
            Toast.makeText(this, "Vui lòng thêm mô tả", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }

    private void getAndSetIntent() {
        if(getIntent().hasExtra("book_name") && getIntent().hasExtra("book_id")){
            book_name = getIntent().getStringExtra("book_name");
            book_id = Integer.valueOf(getIntent().getStringExtra("book_id"));
            get_nameBook.setText(book_name);
        }
        else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    private void store_data(){
        Cursor cursor = db.get_chapter_by_bookID(book_id);
        if(cursor != null){
            while (cursor.moveToNext()){
                ChapterModel chapterModel = new ChapterModel(cursor.getInt(0)
                        ,cursor.getInt(1)
                        ,cursor.getInt(2)
                        ,cursor.getString(3));
                arrChapter.add(chapterModel);
            }
            cursor.close();
        }
    }
}
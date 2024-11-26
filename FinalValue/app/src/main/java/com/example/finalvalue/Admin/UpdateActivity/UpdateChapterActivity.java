package com.example.finalvalue.Admin.UpdateActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.ChapterModel;
import com.example.finalvalue.R;

public class UpdateChapterActivity extends AppCompatActivity {
    EditText get_description;
    TextView get_chapter;
    int idchap, bookID, chapNumber;
    String description;
    DBHelper dbHelper;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_chapter);
        get_chapter = findViewById(R.id.get_update_chapter_chapterNumber);
        get_description = findViewById(R.id.get_update_chapter_descriptionChapter);
        btn_update = findViewById(R.id.btn_update_chapter);
        getAndSetIntent();
        get_description.setText(description);
        get_chapter.setText("Chương " + chapNumber);
        dbHelper = new DBHelper(UpdateChapterActivity.this);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmpty()){
                    if(checkFormat()){
                        ChapterModel chapterModel = new ChapterModel(idchap, bookID, chapNumber, description);
                        dbHelper.update_chapter(chapterModel);
                        finish();
                    }
                }
            }
        });
    }

    private void getAndSetIntent() {
        if(getIntent().hasExtra("chapter_id")
                && getIntent().hasExtra("chapter_bookid")
                && getIntent().hasExtra("chapter_chapNumber")
                && getIntent().hasExtra("chapter_description")){
            idchap = getIntent().getIntExtra("chapter_id", 0);
            bookID = getIntent().getIntExtra("chapter_bookid", 0);
            chapNumber = getIntent().getIntExtra("chapter_chapNumber", 0);
            description = getIntent().getStringExtra("chapter_description");
        }
        else Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
    }
    private boolean checkEmpty() {
        if(get_description.getText().toString().equals("")){
            Toast.makeText(this, "Vui lòng thêm mô tả", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
    private boolean checkFormat() {
        if(get_description.getText().toString().trim().length() > 1000){
            Toast.makeText(this, "Giới hạn mô tả nhỏ hơn 1000 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
}
package com.example.finalvalue.Admin.AddActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.PageModel;
import com.example.finalvalue.R;

public class AddPageActivity extends AppCompatActivity {
    int idchap, pageNumber;
    Button btn_add;
    TextView get_pageNumber;
    ImageView get_image;
    DBHelper db;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Uri imagePath;
    private Bitmap imageToStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);
        getAndSetIntent();
        btn_add = findViewById(R.id.btn_add_page);
        get_image = findViewById(R.id.get_add_page_image);
        get_pageNumber = findViewById(R.id.get_add_page_pageNumber);
        db = new DBHelper(AddPageActivity.this);
        get_PageNumber();
        get_pageNumber.setText("Page " + pageNumber);
        get_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageToStore == null){
                    Toast.makeText(AddPageActivity.this, "Vui lòng thêm hình ảnh", Toast.LENGTH_SHORT).show();
                }
                else{
                    PageModel pageModel = new PageModel(idchap, pageNumber, imageToStore);
                    db.add_pages(pageModel);
                    Toast.makeText(AddPageActivity.this, "Thêm trang truyện thành công", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }
            }
        });
    }

    private void getAndSetIntent() {
        if(getIntent().hasExtra("chapter_id")){
            idchap = getIntent().getIntExtra("chapter_id",0);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
                imagePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                get_image.setImageBitmap(imageToStore);
            }
        }
        catch (Exception e){
            Toast.makeText(this, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void chooseImage() {
        try {
            Intent a = new Intent();
            a.setType("image/*");
            a.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(a, PICK_IMAGE_REQUEST);
        }
        catch (Exception e){
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void get_PageNumber(){
        Cursor cursor = db.get_page_by_chapterID(idchap);
        if(cursor == null){
            pageNumber = 1;
        }
        else {
            int count = 0;
            while (cursor.moveToNext()){
                count++;
            }
            pageNumber = count + 1;
        }
    }
}
package com.example.finalvalue.Admin.UpdateActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalvalue.Admin.AddActivity.AddBookActivity;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateBookActivity extends AppCompatActivity {
    TextView get_id, get_name, get_author, get_chapter, get_description;
    ImageView get_image;
    Button btn_update, btn_delete;
    String name, author, description, id, chapter;
    byte[] imagebyte;
    DBHelper db;
    private static final int PICK_IMAGE_REQUEST = 99;
    private Uri imagePath;
    private Bitmap imageToStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);
        get_id = findViewById(R.id.get_update_book_id);
        get_name = findViewById(R.id.get_update_book_name);
        get_author = findViewById(R.id.get_update_book_author);
        get_description = findViewById(R.id.get_update_book_description);
        get_chapter = findViewById(R.id.get_update_book_chapter);
        get_image = findViewById(R.id.get_update_book_image);
        btn_update = findViewById(R.id.btn_update_book);
        btn_delete = findViewById(R.id.btn_delete_book);
        db = new DBHelper(UpdateBookActivity.this);
        get_and_set_Intent();
        get_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmpty()) {
                    if(checkFormat()) {
                        if(checkNameBook()) {
                            LocalDate myDateObj = LocalDate.now();
                            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            String formatDate = myFormatObj.format(myDateObj);
                            BookModel bookModel = new BookModel(Integer.valueOf(get_id.getText().toString().trim()),
                                    get_name.getText().toString().trim(),
                                    get_description.getText().toString().trim(),
                                    get_author.getText().toString().trim(),
                                    Integer.valueOf(get_chapter.getText().toString().trim()),
                                    imageToStore,
                                    formatDate);
                            db.update_book(bookModel);
                            finish();
                        }
                    }
                }
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b= new AlertDialog.Builder(UpdateBookActivity.this);
                b.setTitle("Xóa truyện");
                b.setMessage("Xác nhận xóa truyện này?");
                b.setIcon(R.drawable.manga_ic);
                b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        db.delete_book(get_id.getText().toString().trim());
                        finish();
                    }});
                b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();   }
                });
                b.create().show();
            }
        });
    }
    public void get_and_set_Intent(){
        if(getIntent().hasExtra("book_id")
                && getIntent().hasExtra("book_name")
                && getIntent().hasExtra("book_author")
                && getIntent().hasExtra("book_description")
                && getIntent().hasExtra("book_image")
                && getIntent().hasExtra("book_chapter") ) {
            id = getIntent().getStringExtra("book_id");
            name = getIntent().getStringExtra("book_name");
            author = getIntent().getStringExtra("book_author");
            description = getIntent().getStringExtra("book_description");
            imagebyte = getIntent().getByteArrayExtra("book_image");
            chapter = getIntent().getStringExtra("book_chapter");
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);

            imageToStore = bitmap;
            get_id.setText(id);
            get_id.setEnabled(false);
            get_name.setText(name);
            get_author.setText(author);
            get_description.setText(description);
            get_chapter.setText(chapter);
            get_image.setImageBitmap(bitmap);
        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
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
    public boolean checkEmpty(){
        if(imageToStore == null){
            Toast.makeText(this, "Vui lòng thêm hình ảnh", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!get_name.getText().toString().equals("") && !get_author.getText().toString().equals("")
                && !get_chapter.getText().toString().equals("") && !get_description.getText().toString().equals("")){
            return true;
        }
        else {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean checkNameBook() {
        Cursor cursor = db.get_Book_by_name(get_name.getText().toString().trim());
        while (cursor.moveToNext()) {
            if (get_name.getText().toString().trim().equals(cursor.getString(1))) {
                Toast.makeText(UpdateBookActivity.this, "Đầu sách đã tồn tại", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
    public boolean checkFormat(){
        if(Integer.valueOf(get_chapter.getText().toString().trim()) <=0 ||Integer.valueOf(get_chapter.getText().toString().trim()) >=500){
            Toast.makeText(this, "Số tập truyện phải lớn hơn 0 và nhỏ hơn 500", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(get_description.getText().toString().trim().length()>=1000){
            Toast.makeText(this, "Độ dài mô tả truyện nhỏ hơn 1000 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(get_name.getText().toString().trim().length()>=20){
            Toast.makeText(this, "Tên truyện nhỏ hơn 20 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(get_author.getText().toString().trim().length()>=30){
            Toast.makeText(this, "Tên tác giả nhỏ hơn 30 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
}
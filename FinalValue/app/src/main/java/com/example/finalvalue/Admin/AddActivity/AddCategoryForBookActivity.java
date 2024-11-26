package com.example.finalvalue.Admin.AddActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalvalue.Admin.Adapter.CategoryBookAdapter;
import com.example.finalvalue.Admin.Adapter.ShowCategoryOfBookAdapter;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.CategoryBookModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class AddCategoryForBookActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText get_categoryID;
    Button btn_add;
    DBHelper dbHelper;
    ShowCategoryOfBookAdapter categoryBookAdapter;
    ArrayList<CategoryBookModel> arrCategory;
    int bookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_for_book);
        recyclerView = findViewById(R.id.rcv_listCategoryOfBook);
        get_categoryID = findViewById(R.id.get_category_for_book);
        btn_add = findViewById(R.id.btn_add_category_for_book);
        dbHelper = new DBHelper(AddCategoryForBookActivity.this);
        arrCategory = new ArrayList<>();
        get_and_setIntent();
        store_data();
        categoryBookAdapter = new ShowCategoryOfBookAdapter(AddCategoryForBookActivity.this, arrCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddCategoryForBookActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(categoryBookAdapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCategoryID()){
                    if(checkExist()){
                        CategoryBookModel categoryBookModel = new CategoryBookModel(bookID, get_categoryID.getText().toString().trim().toLowerCase());
                        dbHelper.add_categoryBook(categoryBookModel);
                        Toast.makeText(AddCategoryForBookActivity.this, "Thêm thành công thể loại", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }
                }
            }
        });
    }
    public void get_and_setIntent(){
        if(!getIntent().hasExtra("book_id")){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else{
            bookID = Integer.valueOf(getIntent().getStringExtra("book_id"));
        }
    }
    public boolean checkCategoryID(){
        Cursor cursor = dbHelper.get_category_by_ID(get_categoryID.getText().toString().trim().toLowerCase());
        if(cursor == null){
            Toast.makeText(this, "Thể loại không tồn tại", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            while (cursor.moveToNext()){
                if(cursor.getString(0).equals(get_categoryID.getText().toString().trim().toLowerCase())){
                    return true;
                }
            }
            Toast.makeText(this, "Thể loại không tồn tại", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean checkExist(){
        Cursor cursor = dbHelper.get_category_by_bookID(bookID);
        if(cursor != null){
            while (cursor.moveToNext()) {
                if (cursor.getString(1).equals(get_categoryID.getText().toString().trim().toLowerCase())) {
                    Toast.makeText(this, "Thể loại đã được thêm", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }
    public void store_data(){
        Cursor cursor = dbHelper.get_category_by_bookID(bookID);
        if(cursor != null){
            while (cursor.moveToNext()){
                CategoryBookModel categoryBookModel = new CategoryBookModel(bookID, cursor.getString(1));
                arrCategory.add(categoryBookModel);
            }
        }
    }
}
package com.example.finalvalue.Admin.AddActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Customer.RegisterActivity;
import com.example.finalvalue.Model.CategoryModel;
import com.example.finalvalue.R;

public class AddCategoryActivity extends AppCompatActivity {
    TextView get_id, get_name;
    Button btn_add;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        get_name = findViewById(R.id.get_add_category_name);
        get_id = get_name;

        btn_add = findViewById(R.id.btn_add_category);
        db = new DBHelper(AddCategoryActivity.this);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmpty()){
                    if(checkFormat()) {
                        if (checkID()) {
                            CategoryModel categoryModel = new CategoryModel(get_id.getText().toString().trim().toLowerCase(),
                                    get_name.getText().toString().trim());
                            db.add_category(categoryModel);
                            Toast.makeText(AddCategoryActivity.this, "Thêm thể loại thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });
    }
    public boolean checkEmpty(){
        if(!get_name.getText().toString().equals("")){
            return true;
        }
        else {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean checkID() {
        Cursor cursor = db.get_category_by_ID(get_id.getText().toString().trim().toLowerCase());
        while (cursor.moveToNext()) {
            if (get_id.getText().toString().trim().toLowerCase().equals(cursor.getString(0))) {
                Toast.makeText(AddCategoryActivity.this, "Thể loại đã tồn tại", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
    public boolean checkFormat(){
        if(get_name.getText().toString().trim().length() >= 10){
            Toast.makeText(this, "Tên thể loại giới hạn 10 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
}
package com.example.finalvalue.Admin.UpdateActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.R;

public class UpdateCategoryActivity extends AppCompatActivity {
    EditText get_id, get_name;
    Button btn_update, btn_delete;

    String id, name;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);
        get_id = findViewById(R.id.get_update_category_id);
        get_name = findViewById(R.id.get_update_category_name);
        btn_update = findViewById(R.id.btn_update_category);
        btn_delete = findViewById(R.id.btn_delete_category);
        db = new DBHelper(UpdateCategoryActivity.this);
        get_and_set_Intent();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmpty()) {
                    if(checkFormat()) {
                        db.update_category(get_id.getText().toString().trim(),
                                get_name.getText().toString().trim());
                        finish();
                    }
                }
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b= new AlertDialog.Builder(UpdateCategoryActivity.this);
                b.setTitle("Xóa thể loại");
                b.setMessage("Xác nhận xóa thể loại này?");
                b.setIcon(R.drawable.manga_ic);
                b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        db.delete_category(get_id.getText().toString().trim());
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
        if(getIntent().hasExtra("category_id") && getIntent().hasExtra("category_name")) {
            id = getIntent().getStringExtra("category_id");
            name = getIntent().getStringExtra("category_name");

            get_id.setText(id);
            get_id.setEnabled(false);
            get_name.setText(name);
        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
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
    public boolean checkFormat(){
        if(get_name.getText().toString().trim().length() >= 10){
            Toast.makeText(this, "Tên thể loại giới hạn 10 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
}
package com.example.finalvalue.Admin.UpdateActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalvalue.Admin.AddActivity.AddAdminAccountActivity;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Customer.SettingActivity;
import com.example.finalvalue.LoginActivity;
import com.example.finalvalue.Model.AdminModel;
import com.example.finalvalue.R;

public class UpdateAdminActivity extends AppCompatActivity {
    EditText get_id, get_username, get_pass, get_role;
    Button btn_update, btn_delete;

    String id, username, password, role;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin);
        get_id = findViewById(R.id.get_update_admin_id);
        get_username = findViewById(R.id.get_update_admin_username);
        get_pass = findViewById(R.id.get_update_admin_password);
        get_role = findViewById(R.id.get_update_admin_role);
        btn_update = findViewById(R.id.btn_update_admin);
        btn_delete = findViewById(R.id.btn_delete_admin);
        db = new DBHelper(UpdateAdminActivity.this);
        get_and_set_Intent();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmpty()) {
                    if(checkFormat()){
                        if(checkUsername()) {
                            if (get_role.getText().toString().equals("")) {
                                db.update_admin(new AdminModel(Integer.valueOf(get_id.getText().toString().trim()),
                                        get_username.getText().toString().trim(),
                                        get_pass.getText().toString().trim(),
                                        "Employee"));
                                finish();
                            } else {
                                db.update_admin(new AdminModel(Integer.valueOf(get_id.getText().toString().trim()),
                                        get_username.getText().toString().trim(),
                                        get_pass.getText().toString().trim(),
                                        get_role.getText().toString().trim()));
                                finish();
                            }
                        }
                    }
                }
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b= new AlertDialog.Builder(UpdateAdminActivity.this);
                b.setTitle("Xóa người dùng");
                b.setMessage("Xác nhận xóa người dùng này?");
                b.setIcon(R.drawable.manga_ic);
                b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        db.delete_admin(get_id.getText().toString().trim());
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
        if(getIntent().hasExtra("admin_id") && getIntent().hasExtra("admin_name") && getIntent().hasExtra("admin_pass")&& getIntent().hasExtra("admin_role")) {
            id = getIntent().getStringExtra("admin_id");
            username = getIntent().getStringExtra("admin_name");
            password = getIntent().getStringExtra("admin_pass");
            role = getIntent().getStringExtra("admin_role");

            get_id.setText(id);
            get_id.setEnabled(false);
            get_username.setText(username);
            get_pass.setText(password);
            get_role.setText(role);
        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean checkEmpty(){
        if(!get_username.getText().toString().equals("") && !get_pass.getText().toString().equals("")){
            return true;
        }
        else {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public boolean checkUsername() {
        Cursor cursor = db.read_admin(get_username.getText().toString().trim());
        while (cursor.moveToNext()) {
            if (get_username.getText().toString().trim().equals(cursor.getString(1))) {
                Toast.makeText(UpdateAdminActivity.this, "Người dùng đã tồn tại", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
    public boolean checkFormat(){
        if(get_username.getText().toString().trim().length() >=20 || get_pass.getText().toString().trim().length() >= 20){
            Toast.makeText(this, "Giới hạn độ dài tên đăng nhập và mật khẩu là 20 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(get_role.getText().toString().trim().length() > 10){
            Toast.makeText(this, "Giới hạn độ dài chức vụ là 10 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
}
package com.example.finalvalue.Admin.AddActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.AdminModel;
import com.example.finalvalue.Model.CategoryModel;
import com.example.finalvalue.R;

public class AddAdminAccountActivity extends AppCompatActivity {
    TextView get_username, get_password, get_role;
    Button btn_add;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin_account);
        get_username = findViewById(R.id.get_add_admin_account_username);
        get_password = findViewById(R.id.get_add_admin_account_password);
        get_role = findViewById(R.id.get_add_admin_account_role);
        btn_add = findViewById(R.id.btn_add_admin_account);
        db = new DBHelper(AddAdminAccountActivity.this);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmpty()){
                    if(checkFormat()) {
                        if (checkUsername()) {
                            if (get_role.getText().toString().equals("")) {
                                AdminModel adminModel = new AdminModel(get_username.getText().toString().trim(),
                                        get_password.getText().toString().trim(),
                                        "Employee");
                                db.add_admin(adminModel);
                                Toast.makeText(AddAdminAccountActivity.this, "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                AdminModel adminModel = new AdminModel(get_username.getText().toString().trim(),
                                        get_password.getText().toString().trim(),
                                        get_role.getText().toString().trim());
                                db.add_admin(adminModel);
                                Toast.makeText(AddAdminAccountActivity.this, "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                }
            }
        });
    }
    public boolean checkEmpty(){
        if(!get_username.getText().toString().equals("") && !get_password.getText().toString().equals("")){
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
                Toast.makeText(AddAdminAccountActivity.this, "Người dùng đã tồn tại", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
    public boolean checkFormat(){
        if(get_username.getText().toString().trim().length() >=20 || get_password.getText().toString().trim().length() >= 20){
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
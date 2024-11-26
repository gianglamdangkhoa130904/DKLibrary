package com.example.finalvalue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalvalue.Admin.AdminActivity;
import com.example.finalvalue.Admin.MainAdminActivity;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Customer.ForgotPassword.InputGmailToResetActivity;
import com.example.finalvalue.Customer.HomeActivity;
import com.example.finalvalue.Customer.RegisterActivity;

public class LoginActivity extends AppCompatActivity {
    TextView btn_go_register, username_log, pass_log, forgot_password;
    Button btn_login;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_go_register = findViewById(R.id.go_register);
        btn_login = findViewById(R.id.btn_login);
        forgot_password = findViewById(R.id.forgot_password);

        username_log = findViewById(R.id.username_log);
        pass_log = findViewById(R.id.password_log);
        db = new DBHelper(LoginActivity.this);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, InputGmailToResetActivity.class);
                startActivity(intent);
            }
        });
        btn_go_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(a);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkUsername(username_log.getText().toString())){
                    checkPassword(username_log.getText().toString(), pass_log.getText().toString());
                }
                else if(checkAdmin(username_log.getText().toString())){
                    checkPassAdmin(username_log.getText().toString(), pass_log.getText().toString());
                }
                else {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
            public Boolean checkUsername(String user){
                Cursor cursor = db.readAllDataCustomer();
                while (cursor.moveToNext()){
                    if(user.equals(cursor.getString(1))){
                        return true;
                    }
                }
                return false;
            }public void checkPassword(String username, String password){
                Cursor cursor = db.read_customer(username);
                while ((cursor.moveToNext())){
                    if(password.equals(cursor.getString(2))){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent a = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(a);
                        //Lưu ID, Name vào Session
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserInfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("IDCus",cursor.getInt(0));
                        editor.putString("NameCus",cursor.getString(3));
                        editor.putString("UserNameCus", cursor.getString(1));
                        editor.apply();
                        //Đóng activity Login
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Mật khẩu chưa đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            public Boolean checkAdmin(String admin){
                Cursor cursor = db.readAllDataAdmin();
                while (cursor.moveToNext()){
                    if(admin.equals(cursor.getString(1))){
                        return true;
                    }
                }
                return false;
            }
            public void checkPassAdmin(String username, String password){
                Cursor cursor = db.read_admin(username);
                while ((cursor.moveToNext())){
                    if(password.equals(cursor.getString(2))){
                        if(cursor.getString(3).equals("Manager")){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent a = new Intent(LoginActivity.this, MainAdminActivity.class);
                        startActivity(a);
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent a = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(a);
                            //Đóng activity Login
                            finish();
                        }
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Mật khẩu chưa đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
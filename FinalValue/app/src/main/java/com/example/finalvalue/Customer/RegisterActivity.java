package com.example.finalvalue.Customer;

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

import com.example.finalvalue.LoginActivity;
import com.example.finalvalue.Model.CustomerModel;
import com.example.finalvalue.R;

import java.time.LocalDate;

public class RegisterActivity extends AppCompatActivity {
    TextView btn_go_login,name_reg,date_birth_reg, email_reg, username_reg, pass_reg, confirm_pass;
    ImageView image_reg;
    Button btn_register;

    private static final int PICK_IMAGE_REQUEST = 99;
    private Uri imagePath;
    private Bitmap imageToStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_go_login = findViewById(R.id.go_login);
        btn_register = findViewById(R.id.btn_register);

        name_reg = findViewById(R.id.name_reg);
        date_birth_reg = findViewById(R.id.date_birth_reg);
        email_reg = findViewById(R.id.email_reg);
        username_reg = findViewById(R.id.username_reg);
        pass_reg = findViewById(R.id.password_reg);
        confirm_pass = findViewById(R.id.confirm_pass);
        image_reg = findViewById(R.id.image_reg);

        DBHelper db = new DBHelper(RegisterActivity.this);
        image_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btn_go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(a);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name_reg.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
                else if(email_reg.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this, "Email không được để trống", Toast.LENGTH_SHORT).show();
                else if(date_birth_reg.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this, "Ngày sinh không được để trống", Toast.LENGTH_SHORT).show();
                else if(imageToStore == null){
                    Toast.makeText(RegisterActivity.this, "Vui lòng thêm hình ảnh", Toast.LENGTH_SHORT).show();
                }
                else if(username_reg.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Tên đăng nhập không được để trống", Toast.LENGTH_SHORT).show();
                }
                else if(pass_reg.getText().toString().equals(""))
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                else {
                    if(checkFormat()) {
                        if (check_username()) {
                            if (check_datetime()) {
                                check_password_and_add();
                            }
                        }
                    }
                }
            }
            public boolean checkFormat(){
                if(name_reg.getText().toString().trim().length() > 80){
                    Toast.makeText(RegisterActivity.this, "Độ dài tên nhỏ hơn 80 ký tự", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else if(email_reg.getText().toString().trim().length() > 100){
                    Toast.makeText(RegisterActivity.this, "Độ dài email nhỏ hơn 100 ký tự", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else if(username_reg.getText().toString().trim().length() > 50){
                    Toast.makeText(RegisterActivity.this, "Độ dài tên đăng nhập nhỏ hơn 50 ký tự", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else if(pass_reg.getText().toString().trim().length() > 50){
                    Toast.makeText(RegisterActivity.this, "Độ dài mật khẩu nhỏ hơn 50 ký tự", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else if (!check_email()) {
                    Toast.makeText(RegisterActivity.this, "Sai định dạng gmail", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else return true;
            }
            public boolean check_email(){
                String[] S = email_reg.getText().toString().trim().split("");
                for(int i = 0; i<S.length;i++){
                    if(S[i].equals("@") && email_reg.getText().toString().trim().split("@").length == 2){
                        if(email_reg.getText().toString().trim().split("@")[1].equals("gmail.com")) {
                            return true;
                        }
                    }
                }
                return false;
            }
            public Boolean check_username() {
                Cursor cursor = db.read_customer(username_reg.getText().toString().trim());
                while (cursor.moveToNext()) {
                    if (username_reg.getText().toString().equals(cursor.getString(1))) {
                        Toast.makeText(RegisterActivity.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    else{
                        return true;
                    }
                }
                return true;
            }
            public Boolean check_datetime(){
                String S = date_birth_reg.getText().toString();
                String[] check = S.split("/");
                if(check.length < 3){
                    Toast.makeText(RegisterActivity.this, "Chưa nhập đủ ngày tháng năm sinh", Toast.LENGTH_SHORT).show();
                    return false;
                } else if (check.length > 3) {
                    Toast.makeText(RegisterActivity.this, "Nhập dư ngày tháng năm sinh", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    int day = Integer.parseInt(check[0]);
                    int month = Integer.parseInt(check[1]);
                    int year = Integer.parseInt(check[2]);
                    int year_now = LocalDate.now().getYear();
                    if(year_now - year >= 16 && year_now - year <= 100){
                        if(month <= 12 && month > 0){
                            if(month == 2){
                                if(day > 29 || day <= 0){
                                    Toast.makeText(RegisterActivity.this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                                else{
                                    return true;
                                }
                            }
                            if(month == 4|| month == 6|| month == 9 || month == 11){
                                if(day > 30 || day <= 0){
                                    Toast.makeText(RegisterActivity.this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                                else{
                                    return true;
                                }
                            }
                            else{
                                if(day > 31 || day <= 0){
                                    Toast.makeText(RegisterActivity.this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                                else{
                                    return true;
                                }
                            }
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Tháng sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Năm sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
            public void check_password_and_add(){
                if (!pass_reg.getText().toString().equals(confirm_pass.getText().toString()))
                    Toast.makeText(RegisterActivity.this, "Mật khẩu chưa khớp", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    CustomerModel customer =  new CustomerModel(name_reg.getText().toString().trim(),
                                                                email_reg.getText().toString().trim(),
                                                                date_birth_reg.getText().toString().trim(),
                                                                username_reg.getText().toString().trim(),
                                                                pass_reg.getText().toString().trim(),
                                                                imageToStore);
                    db.add_customer(customer);
                    Intent a = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(a);
                    finish();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try{
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
                imagePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                image_reg.setImageBitmap(imageToStore);
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
}
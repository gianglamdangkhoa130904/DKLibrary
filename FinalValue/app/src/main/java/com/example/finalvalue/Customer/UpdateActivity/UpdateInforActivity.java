package com.example.finalvalue.Customer.UpdateActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Customer.RegisterActivity;
import com.example.finalvalue.LoginActivity;
import com.example.finalvalue.Model.CustomerModel;
import com.example.finalvalue.R;

import java.time.LocalDate;

public class UpdateInforActivity extends AppCompatActivity {
    EditText get_name, get_DOB, get_email;
    ImageView get_image;
    DBHelper db;
    Button btn_update;
    String password, username;
    int IDCus;

    private static final int PICK_IMAGE_REQUEST = 99;
    private Uri imagePath;
    private Bitmap imageToStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_infor);
        get_name = findViewById(R.id.name_update);
        get_email = findViewById(R.id.email_update);
        get_DOB = findViewById(R.id.date_birth_update);
        btn_update = findViewById(R.id.btn_update_infor);
        get_image = findViewById(R.id.image_update);
        db = new DBHelper(UpdateInforActivity.this);
        getAndSetIntent();
        get_user(IDCus);
        get_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(get_name.getText().toString().equals(""))
                    Toast.makeText(UpdateInforActivity.this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
                else if(get_email.getText().toString().equals(""))
                    Toast.makeText(UpdateInforActivity.this, "Email không được để trống", Toast.LENGTH_SHORT).show();
                else if(get_DOB.getText().toString().equals(""))
                    Toast.makeText(UpdateInforActivity.this, "Ngày sinh không được để trống", Toast.LENGTH_SHORT).show();
                else if(imageToStore == null){
                    Toast.makeText(UpdateInforActivity.this, "Vui lòng thêm hình ảnh", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(checkFormat()) {
                        if (check_datetime()) {
                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserInfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("IDCus",IDCus);
                            editor.putString("NameCus",get_name.getText().toString().trim());
                            editor.putString("UserNameCus", username);
                            editor.apply();

                            Toast.makeText(UpdateInforActivity.this, "Chỉnh sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                            CustomerModel customer =  new CustomerModel(IDCus,get_name.getText().toString().trim(),
                                    get_email.getText().toString().trim(),
                                    get_DOB.getText().toString().trim(),
                                    username,
                                    password,
                                    imageToStore);
                            db.update_customer(customer);
                            finish();
                        }
                    }
                }
            }
        });
    }
    public boolean checkFormat(){
        if(get_name.getText().toString().trim().length() > 80){
            Toast.makeText(UpdateInforActivity.this, "Độ dài tên nhỏ hơn 80 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(get_email.getText().toString().trim().length() > 100){
            Toast.makeText(UpdateInforActivity.this, "Độ dài email nhỏ hơn 100 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
    public Boolean check_datetime(){
        String S = get_DOB.getText().toString();
        String[] check = S.split("/");
        if(check.length < 3){
            Toast.makeText(UpdateInforActivity.this, "Chưa nhập đủ ngày tháng năm sinh", Toast.LENGTH_SHORT).show();
            return false;
        } else if (check.length > 3) {
            Toast.makeText(UpdateInforActivity.this, "Nhập dư ngày tháng năm sinh", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(UpdateInforActivity.this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                    if(month == 4|| month == 6|| month == 9 || month == 11){
                        if(day > 30 || day <= 0){
                            Toast.makeText(UpdateInforActivity.this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                    else{
                        if(day > 31 || day <= 0){
                            Toast.makeText(UpdateInforActivity.this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                }
                else{
                    Toast.makeText(UpdateInforActivity.this, "Tháng sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else{
                Toast.makeText(UpdateInforActivity.this, "Năm sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
    public void getAndSetIntent(){
        if(getIntent().hasExtra("IDCus")){
            IDCus = getIntent().getIntExtra("IDCus",0);
        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    public void get_user(int id){
        Cursor cursor = db.get_customer_by_ID(id);
        if(cursor != null) {
            while (cursor.moveToNext()) {
                get_name.setText(cursor.getString(3));
                get_email.setText(cursor.getString(4));
                get_DOB.setText(cursor.getString(5));
                username = cursor.getString(1);
                password = cursor.getString(2);

                byte[] imagebyte = cursor.getBlob(6);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
                get_image.setImageBitmap(bitmap);
                imageToStore = bitmap;
            }
        }
        else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "No data"+ e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "No data" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
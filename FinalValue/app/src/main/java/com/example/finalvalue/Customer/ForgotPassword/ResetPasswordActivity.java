package com.example.finalvalue.Customer.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Customer.RegisterActivity;
import com.example.finalvalue.LoginActivity;
import com.example.finalvalue.Model.CustomerModel;
import com.example.finalvalue.R;

public class ResetPasswordActivity extends AppCompatActivity {
    int idCus;
    EditText pass, confirm_pass;
    Button btn_confirm;
    DBHelper db;
    CustomerModel customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        pass = findViewById(R.id.password_reset);
        confirm_pass = findViewById(R.id.confirm_pass_reset);
        btn_confirm = findViewById(R.id.btn_reset_password);
        db = new DBHelper(ResetPasswordActivity.this);
        getAndSetIntent();
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.getText().toString().equals(""))
                    Toast.makeText(ResetPasswordActivity.this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                else{
                    if(checkFormat()) {
                        check_password_and_add();
                    }
                }
            }
        });
    }
    public void getAndSetIntent(){
        if(getIntent().hasExtra("id")){
            idCus = getIntent().getIntExtra("id", 0);
        }
        else Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
    }
    public boolean checkFormat(){
        if(pass.getText().toString().trim().length() > 50){
            Toast.makeText(ResetPasswordActivity.this, "Độ dài mật khẩu nhỏ hơn 50 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
    public void check_password_and_add(){
        if (!pass.getText().toString().equals(confirm_pass.getText().toString()))
            Toast.makeText(ResetPasswordActivity.this, "Mật khẩu chưa khớp", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(ResetPasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            Cursor cursor = db.get_customer_by_ID(idCus);
            if(cursor !=null){
                while (cursor.moveToNext()) {
                    byte[] imagebyte = cursor.getBlob(6);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
                    customer = new CustomerModel(cursor.getInt(0),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(1),
                            pass.getText().toString().trim(),
                            bitmap);
                }
            }
            db.update_customer(customer);
            Intent a = new Intent(ResetPasswordActivity.this, LoginActivity.class);
            startActivity(a);
            finish();
        }
    }
}
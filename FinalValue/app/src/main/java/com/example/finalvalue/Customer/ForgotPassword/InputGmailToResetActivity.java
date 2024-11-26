package com.example.finalvalue.Customer.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class InputGmailToResetActivity extends AppCompatActivity {
    EditText email;
    TextView warning;
    Button btn_confirm;
    DBHelper db;
    int idCus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_gmail_to_reset);
        email = findViewById(R.id.gmail_to_reset);
        btn_confirm = findViewById(R.id.confirm_email);
        warning =findViewById(R.id.warning_reset);
        db = new DBHelper(InputGmailToResetActivity.this);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().trim().equals("")){
                    if(checkEmail()){
                        warning.setText("");
                        Intent a = new Intent(InputGmailToResetActivity.this, ResetCodeActivity.class);
                        a.putExtra("email", email.getText().toString().trim());
                        a.putExtra("id", idCus);
                        startActivity(a);
                    }
                    else {
                        warning.setText("");
                        Toast.makeText(InputGmailToResetActivity.this, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    warning.setText("Vui lòng nhập email");
                    warning.setTextSize(20);
                }
            }
        });

    }
    public boolean checkEmail(){
        Cursor cursor = db.readAllDataCustomer();
        if(cursor != null){
            while (cursor.moveToNext()){
                if(cursor.getString(4).equals(email.getText().toString().trim())){
                    idCus = cursor.getInt(0);
                    return true;
                }
            }
        }
        return false;
    }
}
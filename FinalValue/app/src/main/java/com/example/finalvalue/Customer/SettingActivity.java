package com.example.finalvalue.Customer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalvalue.LoginActivity;
import com.example.finalvalue.R;

public class SettingActivity extends AppCompatActivity {
    TextView btn_logout;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btn_logout = findViewById(R.id.btn_logout);
        back = findViewById(R.id.setting_back);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b= new AlertDialog.Builder(SettingActivity.this);
                b.setTitle("Đăng xuất");
                b.setMessage("Bạn muốn đăng xuất?");
                b.setIcon(R.drawable.manga_ic);
                b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //Clear tất cả activity trước đó
                        startActivity(intent);
                        Toast.makeText(SettingActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    }});
                b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();   }
                });
                b.create().show();

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
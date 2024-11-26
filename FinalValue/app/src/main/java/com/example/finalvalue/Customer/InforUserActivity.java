package com.example.finalvalue.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalvalue.Customer.UpdateActivity.UpdateInforActivity;
import com.example.finalvalue.R;

public class InforUserActivity extends AppCompatActivity {
    TextView get_name, get_email, get_DOB, get_username, get_pass;
    ImageView get_image;
    Button btn_goto_update_infor;
    DBHelper db;
    int IDCus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_user);
        get_name = findViewById(R.id.get_infor_name);
        get_email = findViewById(R.id.get_infor_email);
        get_DOB = findViewById(R.id.get_infor_DOB);
        get_username = findViewById(R.id.get_infor_username);
        get_pass = findViewById(R.id.get_infor_password);
        get_image = findViewById(R.id.get_infor_image);
        btn_goto_update_infor = findViewById(R.id.btn_goto_update_infor);
        db = new DBHelper(InforUserActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = InforUserActivity.this.getSharedPreferences("UserInfo", MODE_PRIVATE);
        IDCus = sharedPreferences.getInt("IDCus", 0);
        get_user(IDCus);
        btn_goto_update_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InforUserActivity.this, UpdateInforActivity.class);
                intent.putExtra("IDCus", IDCus);
                startActivity(intent);
            }
        });
    }

    public void get_user(int id){
        Cursor cursor = db.get_customer_by_ID(id);
        if(cursor != null) {
            while (cursor.moveToNext()) {
                get_name.setText("Tên: "+cursor.getString(3));
                get_email.setText("Email: "+cursor.getString(4));
                get_DOB.setText("Ngày sinh: "+cursor.getString(5));
                get_username.setText("Tên đăng nhập: "+cursor.getString(1));
                get_pass.setText(cursor.getString(2));

                byte[] imagebyte = cursor.getBlob(6);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
                get_image.setImageBitmap(bitmap);
            }
        }
        else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}
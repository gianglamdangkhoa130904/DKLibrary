package com.example.finalvalue.Admin.ListActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalvalue.Admin.Adapter.PageAdapter;
import com.example.finalvalue.Admin.AddActivity.AddPageActivity;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.CustomerModel;
import com.example.finalvalue.Model.PageModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class ListPageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btn_goto;
    PageAdapter pageAdapter;
    ArrayList<PageModel> arrPage;
    DBHelper db;
    int idChap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);
        getAndSetIntent();
        recyclerView = findViewById(R.id.rcv_page_admin);
        btn_goto = findViewById(R.id.btn_goto_addPage);
        arrPage = new ArrayList<>();
        db = new DBHelper(ListPageActivity.this);
        store_data_to_list();
        pageAdapter = new PageAdapter(ListPageActivity.this, arrPage);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListPageActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(pageAdapter);
        btn_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPageActivity.this, AddPageActivity.class);
                intent.putExtra("chapter_id", idChap);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrPage = new ArrayList<>();
        store_data_to_list();
        pageAdapter = new PageAdapter(ListPageActivity.this, arrPage);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListPageActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(pageAdapter);
        btn_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPageActivity.this, AddPageActivity.class);
                intent.putExtra("chapter_id", idChap);
                startActivity(intent);
            }
        });
    }

    private void getAndSetIntent() {
        if(getIntent().hasExtra("chapter_id")){
            idChap = getIntent().getIntExtra("chapter_id",0);
        }
    }
    void store_data_to_list(){
        Cursor cursor = db.get_page_by_chapterID(idChap); //
        if(cursor == null)
            Toast.makeText(ListPageActivity.this, "No data.", Toast.LENGTH_SHORT).show();
        else{
            while(cursor.moveToNext()){
                byte[] imagebyte = cursor.getBlob(3);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
                PageModel pageModel = new PageModel(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        bitmap);
                arrPage.add(pageModel);
            }
            cursor.close();
        }
    }
}
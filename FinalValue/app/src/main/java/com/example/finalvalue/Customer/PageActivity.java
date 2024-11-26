package com.example.finalvalue.Customer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finalvalue.Admin.ListActivity.ListPageActivity;
import com.example.finalvalue.Admin.UpdateActivity.UpdateAdminActivity;
import com.example.finalvalue.Customer.Adapter.ListPageAdapter;
import com.example.finalvalue.Model.PageModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class PageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<PageModel> arrPage;
    ListPageAdapter listPageAdapter;
    DBHelper db;
    int idChap;
    String desChap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        getAndSetIntent();
        recyclerView = findViewById(R.id.rcv_listpager);
        arrPage = new ArrayList<>();
        db = new DBHelper(PageActivity.this);
        store_data_to_list();
        listPageAdapter = new ListPageAdapter(PageActivity.this, arrPage);
        recyclerView.setLayoutManager(new LinearLayoutManager(PageActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(listPageAdapter);
        showDescription();
    }
    private void showDescription(){
        AlertDialog.Builder b= new AlertDialog.Builder(PageActivity.this);
        b.setTitle("Mô tả chương truyện");
        b.setMessage(desChap);
        b.setIcon(R.drawable.manga_ic);
        b.setPositiveButton("Bỏ qua", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }});
        b.create().show();
    }
    private void getAndSetIntent() {
        if(getIntent().hasExtra("chapter_id") && getIntent().hasExtra("chapter_des") ){
            idChap = getIntent().getIntExtra("chapter_id",0);
            desChap = getIntent().getStringExtra("chapter_des");
        }
    }
    void store_data_to_list(){
        Cursor cursor = db.get_page_by_chapterID(idChap); //
        if(cursor == null)
            Toast.makeText(PageActivity.this, "No data.", Toast.LENGTH_SHORT).show();
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
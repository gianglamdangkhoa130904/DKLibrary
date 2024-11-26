package com.example.finalvalue.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalvalue.Customer.Adapter.ListTitleHomeAdapter;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    TextView back, search_result;
    EditText search_bar;
    RecyclerView recyclerView;
    CardView btn_search;
    ArrayList<BookModel> arrBook, arrSearch;
    DBHelper db;
    ListTitleHomeAdapter listTitleHomeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        back = findViewById(R.id.search_back);
        search_result = findViewById(R.id.search_result);
        search_bar = findViewById(R.id.search_txt);
        recyclerView = findViewById(R.id.rcv_search);
        btn_search = findViewById(R.id.btn_search);
        arrBook = new ArrayList<>();
        arrSearch = new ArrayList<>();
        db = new DBHelper(SearchActivity.this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        store_data();
        recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this, 3));
    }

    @Override
    protected void onResume() {
        super.onResume();
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrSearch = new ArrayList<>();
                search_result.setText("Kết quả tìm kiếm cho "+"'"+search_bar.getText().toString().trim()+"'");
                String search = search_bar.getText().toString().trim().toLowerCase();
                search_bar.setText("");
                for(int i = 0; i <arrBook.size(); i++){
                    if(arrBook.get(i).nameBook.toLowerCase().contains(search) || arrBook.get(i).authorBook.toLowerCase().equals(search)){
                        arrSearch.add(arrBook.get(i));
                    }
                }
                listTitleHomeAdapter = new ListTitleHomeAdapter(SearchActivity.this, arrSearch,
                        R.layout.row_customer_book2,
                        R.id.main_book_home_layout2,
                        R.id.get_name_book_home2,
                        R.id.get_image_book_home2);
                recyclerView.setAdapter(listTitleHomeAdapter);
            }
        });
    }

    private void store_data() {
        Cursor cursor = db.get_All_book();
        if(cursor != null){
            while(cursor.moveToNext()){
                byte[] imagebyte = cursor.getBlob(5);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
                BookModel bookModel = new BookModel(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        bitmap, cursor.getString(6));
                arrBook.add(bookModel);
            }
        }
    }

}
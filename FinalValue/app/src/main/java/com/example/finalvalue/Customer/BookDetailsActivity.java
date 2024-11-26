package com.example.finalvalue.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalvalue.Customer.Adapter.CategoryOfBookAdapter;
import com.example.finalvalue.Customer.Adapter.ViewPagerAdapter;
import com.example.finalvalue.Customer.CategoryFragments.ListBookByCategoryFragment;
import com.example.finalvalue.Customer.DetailFragments.ChapterDetailsFragment;
import com.example.finalvalue.Customer.DetailFragments.DescriptionDetailFragment;
import com.example.finalvalue.Customer.HomeFragments.ExploreFragment;
import com.example.finalvalue.Customer.HomeFragments.UserFragment;
import com.example.finalvalue.Model.CategoryModel;
import com.example.finalvalue.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class BookDetailsActivity extends AppCompatActivity {
    TextView get_name, get_author, get_chapter, get_date;
    ImageView get_image, get_love;
    TabLayout detailTablayout;
    ViewPager detailViewpager;
    Button back;
    ArrayList<CategoryModel> arrCategory;
    RecyclerView recyclerView_category;
    DBHelper db;
    byte[] imagebyte;
    String id, name, author, description, chapter, dateRelease;
    int IDCus;
    boolean state_love;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        get_User();
        recyclerView_category = findViewById(R.id.rcv_listcategory_of_book);
        detailTablayout = findViewById(R.id.details_tablayout);
        detailViewpager = findViewById(R.id.details_viewpager);
        detailTablayout.setupWithViewPager(detailViewpager);
        get_name = findViewById(R.id.get_detail_nameBook);
        get_author = findViewById(R.id.get_detail_authorBook);
        get_chapter = findViewById(R.id.get_detail_numberChapter);
        get_image = findViewById(R.id.get_detail_image);
        get_date = findViewById(R.id.details_dateUpdate);
        get_love = findViewById(R.id.get_love);
        back = findViewById(R.id.detail_back);
        db = new DBHelper(BookDetailsActivity.this);
        arrCategory = new ArrayList<>();
        recyclerView_category.setLayoutManager(new LinearLayoutManager(BookDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        get_setIntent();
        set_Love();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new DescriptionDetailFragment(description), "Mô tả");
        viewPagerAdapter.addFragment(new ChapterDetailsFragment(Integer.valueOf(id)), "Chương");
        detailViewpager.setAdapter(viewPagerAdapter);
        get_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state_love){
                    get_love.setImageResource(R.drawable.ic_heart_empty);
                    Cursor cursor = db.get_love_Book(IDCus, Integer.valueOf(id));
                    while (cursor.moveToNext()){
                        db.delete_loveBook(cursor.getInt(0));
                    }
                    state_love = false;
                }
                else{
                    get_love.setImageResource(R.drawable.ic_heart);
                    db.add_loveBook(IDCus, Integer.valueOf(id));
                    state_love = true;
                }
            }
        });
        set_RCV_category();
    }

    public void get_setIntent(){
        if(getIntent().hasExtra("book_id")
                && getIntent().hasExtra("book_name")
                && getIntent().hasExtra("book_author")
                && getIntent().hasExtra("book_description")
                && getIntent().hasExtra("book_image")
                && getIntent().hasExtra("book_chapter")
                && getIntent().hasExtra("book_update")) {
            id = getIntent().getStringExtra("book_id");
            name = getIntent().getStringExtra("book_name");
            author = getIntent().getStringExtra("book_author");
            description = getIntent().getStringExtra("book_description");
            imagebyte = getIntent().getByteArrayExtra("book_image");
            chapter = getIntent().getStringExtra("book_chapter");
            dateRelease = getIntent().getStringExtra("book_update");
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
            get_name.setText(name);
            get_author.setText("Tác giả: "+author);
            get_chapter.setText("Số chương truyện: "+chapter);
            get_image.setImageBitmap(bitmap);
            get_date.setText("New update: "+ dateRelease);
        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    public void get_User(){
        SharedPreferences sharedPreferences = BookDetailsActivity.this.getSharedPreferences("UserInfo", MODE_PRIVATE);
        IDCus = sharedPreferences.getInt("IDCus", 0);
    }
    public void set_Love(){
        Cursor cursor = db.get_love_Book(IDCus, Integer.valueOf(id));
        boolean check = false;
        if(cursor != null){
            while (cursor.moveToNext()){
                if(cursor.getInt(1) == IDCus && cursor.getInt(2) == Integer.valueOf(id)){
                    check = true;
                }
            }
            if(check){
                get_love.setImageResource(R.drawable.ic_heart);
                state_love = true;
            }
            else{
                get_love.setImageResource(R.drawable.ic_heart_empty);
                state_love = false;
            }
        }
    }
    public void set_RCV_category(){
        Cursor cursor = db.get_category_of_book(Integer.valueOf(id));
        if(cursor != null){
            while (cursor.moveToNext()){
                CategoryModel categoryModel = new CategoryModel(cursor.getString(0), cursor.getString(1));
                arrCategory.add(categoryModel);
            }
        }
        CategoryOfBookAdapter categoryOfBookAdapter = new CategoryOfBookAdapter(BookDetailsActivity.this, arrCategory);
        recyclerView_category.setAdapter(categoryOfBookAdapter);
    }
}
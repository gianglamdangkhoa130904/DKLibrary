package com.example.finalvalue.Customer.HomeFragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalvalue.Customer.Adapter.AutoScrollAdapter;
import com.example.finalvalue.Customer.Adapter.ListTitleHomeAdapter;
import com.example.finalvalue.Customer.CategoryActivity;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.R;
import com.example.finalvalue.Customer.SearchActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainFragment extends Fragment {
    RecyclerView recyclerView, recyclerView_decutamhuyet, recyclerView_truyenmoi, recyclerView_yeuthich;
    AutoScrollAdapter autoScrollAdapter;
    ListTitleHomeAdapter listTitleHomeAdapter;
    DBHelper db;
    ArrayList<Integer> arrImage;
    ArrayList<BookModel> arrBook_decu, arrBook_truyenmoi, arrBook_yeuthich;
    TextView title_truyenmoi;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toolbar actionBar = (Toolbar) getActivity().findViewById(R.id.materialToolbar_home);
        actionBar.setTitle("Đề xuất");
        actionBar.setBackgroundColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(actionBar);
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.banner_rcv);
        recyclerView_decutamhuyet = view.findViewById(R.id.rcv_main_home_decutamhuyet);
        recyclerView_truyenmoi = view.findViewById(R.id.rcv_main_home_truyenmoi);
        recyclerView_yeuthich = view.findViewById(R.id.rcv_main_home_yeuthich);
        db = new DBHelper(getActivity());
        arrImage = new ArrayList<>();
        arrBook_decu = new ArrayList<>();
        arrBook_truyenmoi = new ArrayList<>();
        arrBook_yeuthich = new ArrayList<>();
        title_truyenmoi = view.findViewById(R.id.title_truyenmoi);
        setBanner();
        setRecyclerView_decutamhuyet();
        setRecyclerView_truyenmoi();
        setRecyclerView_yeuthich();
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_home, menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.search_toolbar_home) {
            Intent a = new Intent(getActivity(), SearchActivity.class);
            startActivity(a);
            return true;
        } else if (itemId == R.id.menu_toolbar_home) {
            Intent b = new Intent(getActivity(), CategoryActivity.class);
            startActivity(b);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setBanner(){
        arrImage.add(R.drawable.banner2);
        arrImage.add(R.drawable.banner3);
        arrImage.add(R.drawable.banner1);
        autoScrollAdapter = new AutoScrollAdapter(getActivity(), arrImage);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(autoScrollAdapter);

        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recyclerView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(linearLayoutManager.findLastCompletelyVisibleItemPosition() < (autoScrollAdapter.getItemCount() - 1)){
                    linearLayoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(),linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                } else if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == (autoScrollAdapter.getItemCount() - 1)){
                    linearLayoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(),0);
                }
            }
        }, 0, 5000);
    }
    public void setRecyclerView_decutamhuyet(){
        recyclerView_decutamhuyet.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        Cursor cursor = db.get_All_book();
        if(cursor !=null){
            int count = 0;
            while (cursor.moveToNext()){
                if(count <= 3) {
                    byte[] imagebyte = cursor.getBlob(5);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
                    BookModel bookModel = new BookModel(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            bitmap, cursor.getString(6));
                    arrBook_decu.add(bookModel);
                    count++;
                }
                else{
                    break;
                }
            }
        }
        listTitleHomeAdapter = new ListTitleHomeAdapter(getActivity(), arrBook_decu,
                R.layout.row_book_customer,
                R.id.main_book_home_layout,
                R.id.get_name_book_home,
                R.id.get_image_book_home);
        recyclerView_decutamhuyet.setAdapter(listTitleHomeAdapter);
    }
    public void setRecyclerView_truyenmoi(){
        recyclerView_truyenmoi.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        Cursor cursor = db.get_All_book();
        if(cursor != null){
            while (cursor.moveToNext()){
                LocalDate myDateObj = LocalDate.now();
                String dayRelease = cursor.getString(6);
                int day = Integer.valueOf(dayRelease.split("/")[0]);
                int month = Integer.valueOf(dayRelease.split("/")[1]);
                int year = Integer.valueOf(dayRelease.split("/")[2]);
                int distance_day = ((((myDateObj.getYear() - year) * 12 + myDateObj.getMonthValue()) - month) * 30 + myDateObj.getDayOfMonth()) - day;

                if(distance_day <= 2){
                    byte[] imagebyte = cursor.getBlob(5);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
                    BookModel bookModel = new BookModel(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            bitmap, cursor.getString(6));
                    arrBook_truyenmoi.add(bookModel);
                }
            }
        }
        if(arrBook_truyenmoi.size() == 0){
            title_truyenmoi.setVisibility(View.INVISIBLE);
        }
        listTitleHomeAdapter = new ListTitleHomeAdapter(getActivity(), arrBook_truyenmoi,
                R.layout.row_book_customer1,
                R.id.main_book_home_layout1,
                R.id.get_name_book_home1,
                R.id.get_image_book_home1);
        recyclerView_truyenmoi.setAdapter(listTitleHomeAdapter);
    }
    public void setRecyclerView_yeuthich(){
        recyclerView_yeuthich.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        ArrayList<String> arrNameBook = new ArrayList<>();
        Cursor cursor = db.get_All_viewer("DESC");
        if(cursor != null){
            while (cursor.moveToNext()){
                arrNameBook.add(cursor.getString(1));
            }
        }
        for(int i = 0; i < arrNameBook.size() && i < 6; i++){
            Cursor cursor_go = db.get_Book_by_name(arrNameBook.get(i));
            if(cursor_go != null){
                while (cursor_go.moveToNext()){
                    byte[] imagebyte = cursor_go.getBlob(5);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
                    BookModel bookModel = new BookModel(cursor_go.getInt(0),
                            cursor_go.getString(1),
                            cursor_go.getString(2),
                            cursor_go.getString(3),
                            cursor_go.getInt(4),
                            bitmap, cursor_go.getString(6));
                    arrBook_yeuthich.add(bookModel);
                }
            }
        }
        listTitleHomeAdapter = new ListTitleHomeAdapter(getActivity(), arrBook_yeuthich,
                R.layout.row_customer_book2,
                R.id.main_book_home_layout2,
                R.id.get_name_book_home2,
                R.id.get_image_book_home2);
        recyclerView_yeuthich.setAdapter(listTitleHomeAdapter);
    }
}
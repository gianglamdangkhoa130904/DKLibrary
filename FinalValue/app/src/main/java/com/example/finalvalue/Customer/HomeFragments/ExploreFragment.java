package com.example.finalvalue.Customer.HomeFragments;

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
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalvalue.Customer.Adapter.ViewerAdapter;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {
    ArrayList<Integer> arrViewer;
    ArrayList<String> arrNameBook;
    ArrayList<BookModel> arrBook;
    DBHelper db;
    RecyclerView recyclerView;
    Button btn_asc, btn_desc;
    ViewerAdapter viewerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toolbar actionBar = (Toolbar) getActivity().findViewById(R.id.materialToolbar_home);
        actionBar.setTitle("Lượt xem");
        actionBar.setBackgroundColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(actionBar);
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DBHelper(getActivity());
        recyclerView = view.findViewById(R.id.rcv_viewer);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        btn_asc = view.findViewById(R.id.viewer_asc);
        btn_desc = view.findViewById(R.id.viewer_desc);
        arrNameBook = new ArrayList<>();
        arrBook = new ArrayList<>();
        arrViewer = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        store_data("DESC");
        viewerAdapter = new ViewerAdapter(getActivity(), arrBook, arrViewer);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(viewerAdapter);
        btn_asc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store_data("ASC");
                viewerAdapter = new ViewerAdapter(getActivity(), arrBook, arrViewer);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                recyclerView.setAdapter(viewerAdapter);
            }
        });
        btn_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store_data("DESC");
                viewerAdapter = new ViewerAdapter(getActivity(), arrBook, arrViewer);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                recyclerView.setAdapter(viewerAdapter);
            }
        });
    }

    private void store_data(String state) {
        arrNameBook = new ArrayList<>();
        arrBook = new ArrayList<>();
        arrViewer = new ArrayList<>();
        Cursor cursor = db.get_All_viewer(state);
        if(cursor != null){
            while (cursor.moveToNext()){
                arrViewer.add(cursor.getInt(2));
                arrNameBook.add(cursor.getString(1));
            }
        }
        for(int i = 0; i<arrNameBook.size(); i++){
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
                    arrBook.add(bookModel);
                }
            }
        }
    }
}
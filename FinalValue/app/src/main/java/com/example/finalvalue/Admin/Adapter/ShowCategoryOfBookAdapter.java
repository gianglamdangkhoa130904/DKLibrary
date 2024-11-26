package com.example.finalvalue.Admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.Model.CategoryBookModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class ShowCategoryOfBookAdapter extends RecyclerView.Adapter<ShowCategoryOfBookAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<CategoryBookModel> arrCategoryBook;

    public ShowCategoryOfBookAdapter(Context context, ArrayList<CategoryBookModel> arrCategoryBook) {
        this.context = context;
        this.arrCategoryBook = arrCategoryBook;
    }

    @NonNull
    @Override
    public ShowCategoryOfBookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_of_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowCategoryOfBookAdapter.MyViewHolder holder, int position) {
        holder.get_category.setText(arrCategoryBook.get(position).categoryID.toUpperCase());
    }

    @Override
    public int getItemCount() {
        return arrCategoryBook == null? 0: arrCategoryBook.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView get_category;
        LinearLayout mainlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            get_category= itemView.findViewById(R.id.get_category_of_book);
            mainlayout = itemView.findViewById(R.id.main_category_of_book_layout);
        }
    }
}

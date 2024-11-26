package com.example.finalvalue.Admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalvalue.Admin.AddActivity.AddCategoryForBookActivity;
import com.example.finalvalue.Admin.AddActivity.AddChapterActivity;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class ChapterBookAdapter extends RecyclerView.Adapter<ChapterBookAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<BookModel> arrBook;

    public ChapterBookAdapter(Context context, ArrayList<BookModel> arrBook) {
        this.context = context;
        this.arrBook = arrBook;
    }

    @NonNull
    @Override
    public ChapterBookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_customer_book2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterBookAdapter.MyViewHolder holder, int position) {
        holder.get_name.setText(""+ arrBook.get(position).nameBook);
        holder.get_image.setImageBitmap(arrBook.get(position).imageBook);
        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(holder.itemView.getContext(), AddChapterActivity.class);
                a.putExtra("book_name", arrBook.get(position).nameBook);
                a.putExtra("book_id", String.valueOf(arrBook.get(position).idBook));
                holder.itemView.getContext().startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrBook == null? 0: arrBook.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView get_name;
        ImageView get_image;
        LinearLayout mainlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            get_name = itemView.findViewById(R.id.get_name_book_home2);
            get_image = itemView.findViewById(R.id.get_image_book_home2);
            mainlayout = itemView.findViewById(R.id.main_book_home_layout2);
        }
    }
}

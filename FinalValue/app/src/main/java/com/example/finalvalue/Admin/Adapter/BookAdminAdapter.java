package com.example.finalvalue.Admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalvalue.Admin.UpdateActivity.UpdateBookActivity;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class BookAdminAdapter extends RecyclerView.Adapter<BookAdminAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<BookModel> arrBook;

    public BookAdminAdapter(Context context, ArrayList<BookModel> arrBook) {
        this.context = context;
        this.arrBook = arrBook;
    }

    @NonNull
    @Override
    public BookAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_book_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdminAdapter.MyViewHolder holder, int position) {
        holder.get_id.setText("ID: " + arrBook.get(position).idBook);
        holder.get_name.setText("Name: " + arrBook.get(position).nameBook);
        holder.get_author.setText("Author: " + arrBook.get(position).authorBook);
        holder.get_chapter.setText("Chapter: " + arrBook.get(position).numberChapter);
        holder.get_image.setImageBitmap(arrBook.get(position).imageBook);
        holder.get_date.setText("Release Date: "+ arrBook.get(position).dateRelease);
        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                arrBook.get(position).imageBook.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] imageByte = stream.toByteArray();
                Intent a = new Intent(holder.itemView.getContext(), UpdateBookActivity.class);
                a.putExtra("book_id", String.valueOf(arrBook.get(position).idBook));
                a.putExtra("book_name", String.valueOf(arrBook.get(position).nameBook));
                a.putExtra("book_author", String.valueOf(arrBook.get(position).authorBook));
                a.putExtra("book_description", String.valueOf(arrBook.get(position).descriptionBook));
                a.putExtra("book_image", imageByte);
                a.putExtra("book_chapter", String.valueOf(arrBook.get(position).numberChapter));
                holder.itemView.getContext().startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrBook == null? 0: arrBook.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView get_id, get_name, get_author, get_chapter, get_date;
        ImageView get_image;
        LinearLayout mainlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            get_id = itemView.findViewById(R.id.get_book_admin_id);
            get_name = itemView.findViewById(R.id.get_book_admin_name);
            get_author = itemView.findViewById(R.id.get_book_admin_author);
            get_chapter = itemView.findViewById(R.id.get_book_admin_chapter);
            get_image = itemView.findViewById(R.id.get_book_admin_image);
            get_date = itemView.findViewById(R.id.get_book_admin_dateRelease);
            mainlayout = itemView.findViewById(R.id.admin_book_row_layout);
        }
    }
}

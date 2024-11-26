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

import com.example.finalvalue.Admin.AddActivity.AddCategoryForBookActivity;
import com.example.finalvalue.Admin.UpdateActivity.UpdateBookActivity;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CategoryBookAdapter extends RecyclerView.Adapter<CategoryBookAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<BookModel> arrBook;

    public CategoryBookAdapter(Context context, ArrayList<BookModel> arrBook) {
        this.context = context;
        this.arrBook = arrBook;
    }

    @NonNull
    @Override
    public CategoryBookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_book_for_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryBookAdapter.MyViewHolder holder, int position) {
        holder.get_name.setText(""+ arrBook.get(position).nameBook);
        holder.get_image.setImageBitmap(arrBook.get(position).imageBook);
        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(holder.itemView.getContext(), AddCategoryForBookActivity.class);
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
            get_name = itemView.findViewById(R.id.get_name_book_category);
            get_image = itemView.findViewById(R.id.get_image_book_category);
            mainlayout = itemView.findViewById(R.id.main_listbook_category_layout);
        }
    }
}

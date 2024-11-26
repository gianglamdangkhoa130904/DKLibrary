package com.example.finalvalue.Customer.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.finalvalue.Customer.BookDetailsActivity;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ListTitleHomeAdapter extends RecyclerView.Adapter<ListTitleHomeAdapter.MyViewHolder> {
    private Context context;
    ArrayList<BookModel> arrBook;
    int layout_row, idLayout, idName, idImage;
    DBHelper db;
    int IDCus;
    public ListTitleHomeAdapter(Context context, ArrayList<BookModel> arrBook, int layout_row, int idLayout, int idName, int idImage) {
        this.context = context;
        this.arrBook = arrBook;
        this.layout_row = layout_row;
        this.idLayout = idLayout;
        this.idName = idName;
        this.idImage = idImage;
        db = new DBHelper(context);
    }

    @NonNull
    @Override
    public ListTitleHomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTitleHomeAdapter.MyViewHolder holder, int position) {
        holder.get_image.setImageBitmap(arrBook.get(position).imageBook);
        holder.get_name.setText(arrBook.get(position).nameBook);
        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                arrBook.get(position).imageBook.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] imageByte = stream.toByteArray();
                Intent a = new Intent(holder.itemView.getContext(), BookDetailsActivity.class);
                a.putExtra("book_id", String.valueOf(arrBook.get(position).idBook));
                a.putExtra("book_name", String.valueOf(arrBook.get(position).nameBook));
                a.putExtra("book_author", String.valueOf(arrBook.get(position).authorBook));
                a.putExtra("book_description", String.valueOf(arrBook.get(position).descriptionBook));
                a.putExtra("book_image", imageByte);
                a.putExtra("book_chapter", String.valueOf(arrBook.get(position).numberChapter));
                a.putExtra("book_update", arrBook.get(position).dateRelease);
                db.add_viewer(IDCus, arrBook.get(position).idBook);
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
            get_name = itemView.findViewById(idName);
            get_image = itemView.findViewById(idImage);
            mainlayout = itemView.findViewById(idLayout);
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
            IDCus = sharedPreferences.getInt("IDCus", 0);
        }
    }
}

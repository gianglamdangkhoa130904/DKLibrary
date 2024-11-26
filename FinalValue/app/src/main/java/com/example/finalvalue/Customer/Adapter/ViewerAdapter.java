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

import com.example.finalvalue.Customer.BookDetailsActivity;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class ViewerAdapter extends RecyclerView.Adapter<ViewerAdapter.MyViewHolder>{
    int IDCus;
    Context context;
    ArrayList<BookModel> arrBook;
    ArrayList<Integer> arrViewer;
    DBHelper db;

    public ViewerAdapter(Context context, ArrayList<BookModel> arrBook, ArrayList<Integer> arrViewer) {
        this.context = context;
        this.arrBook = arrBook;
        this.arrViewer = arrViewer;
        db = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_viewer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewerAdapter.MyViewHolder holder, int position) {
        holder.get_image.setImageBitmap(arrBook.get(position).imageBook);
        holder.get_name.setText(arrBook.get(position).nameBook);
        holder.get_viewer_number.setText(arrViewer.get(position) + "");
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
        ImageView get_image;
        TextView get_name, get_viewer_number;
        LinearLayout mainlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            get_image = itemView.findViewById(R.id.viewer_image);
            get_name = itemView.findViewById(R.id.viewer_bookname);
            get_viewer_number = itemView.findViewById(R.id.view_number);
            mainlayout = itemView.findViewById(R.id.main_viewer_layout);
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
            IDCus = sharedPreferences.getInt("IDCus", 0);
        }
    }
}

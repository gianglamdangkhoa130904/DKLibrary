package com.example.finalvalue.Customer.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalvalue.Customer.BookDetailsActivity;
import com.example.finalvalue.Customer.DBHelper;
import com.example.finalvalue.LoginActivity;
import com.example.finalvalue.Model.BookModel;
import com.example.finalvalue.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ViewHistoryAdapter extends RecyclerView.Adapter<ViewHistoryAdapter.MyViewHolder>{
    Context context;
    ArrayList<String> arrBookName;
    ArrayList<Integer> arrViewer;
    ArrayList<Integer> arrBookID;
    ArrayList<BookModel> arrBook;
    DBHelper db;
    int IDCus;
    public ViewHistoryAdapter(Context context, ArrayList<String> arrBookName, ArrayList<Integer> arrViewer, ArrayList<Integer> arrBookID) {
        this.context = context;
        this.arrBookName = arrBookName;
        this.arrViewer = arrViewer;
        this.arrBookID = arrBookID;
    }

    @NonNull
    @Override
    public ViewHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHistoryAdapter.MyViewHolder holder, int position) {
        holder.get_image.setImageBitmap(arrBook.get(position).imageBook);
        holder.get_name.setText(arrBook.get(position).nameBook);
        holder.get_view.setText("Đã xem: " + arrViewer.get(position)+" lần");
        holder.main_layout.setOnClickListener(new View.OnClickListener() {
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
        return arrViewer == null? 0:arrViewer.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView get_name ,get_view;
        ImageView get_image;
        LinearLayout main_layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
            IDCus = sharedPreferences.getInt("IDCus", 0);
            db = new DBHelper(context);
            get_name = itemView.findViewById(R.id.get_name_history);
            get_view = itemView.findViewById(R.id.get_viewer_history);
            get_image = itemView.findViewById(R.id.get_image_history);
            main_layout = itemView.findViewById(R.id.main_history_layout);
            arrBook = new ArrayList<>();
            for(int i = 0; i < arrBookName.size(); i++){
                Cursor cursor = db.get_Book_by_name(arrBookName.get(i));
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
    }
}

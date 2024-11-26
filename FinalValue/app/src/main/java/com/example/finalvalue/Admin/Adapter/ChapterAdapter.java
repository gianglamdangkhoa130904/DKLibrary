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

import com.example.finalvalue.Admin.AddActivity.AddCategoryForBookActivity;
import com.example.finalvalue.Admin.UpdateActivity.UpdateChapterActivity;
import com.example.finalvalue.Model.ChapterModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.MyViewHolder>{
    Context context;
    ArrayList<ChapterModel> arrChapter;

    public ChapterAdapter(Context context, ArrayList<ChapterModel> arrChapter) {
        this.context = context;
        this.arrChapter = arrChapter;
    }

    @NonNull
    @Override
    public ChapterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.get_number.setText("Chương "+arrChapter.get(position).chapNumber);
        holder.mainChapterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(holder.itemView.getContext(), UpdateChapterActivity.class);
                a.putExtra("chapter_id", arrChapter.get(position).idChapter);
                a.putExtra("chapter_bookid", arrChapter.get(position).bookID);
                a.putExtra("chapter_chapNumber", arrChapter.get(position).chapNumber);
                a.putExtra("chapter_description", arrChapter.get(position).chapDescription);
                holder.itemView.getContext().startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrChapter == null? 0: arrChapter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView get_number;
        LinearLayout mainChapterLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            get_number = itemView.findViewById(R.id.get_chapter_number);
            mainChapterLayout = itemView.findViewById(R.id.chapter_mainlayout);
        }
    }
}

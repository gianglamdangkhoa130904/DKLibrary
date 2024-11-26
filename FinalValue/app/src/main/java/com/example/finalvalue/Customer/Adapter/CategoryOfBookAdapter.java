package com.example.finalvalue.Customer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalvalue.Model.CategoryModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class CategoryOfBookAdapter extends RecyclerView.Adapter<CategoryOfBookAdapter.MyViewHolder> {

    Context context;
    ArrayList<CategoryModel> arrCate;

    public CategoryOfBookAdapter(Context context, ArrayList<CategoryModel> arrCate) {
        this.context = context;
        this.arrCate = arrCate;
    }

    @NonNull
    @Override
    public CategoryOfBookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryOfBookAdapter.MyViewHolder holder, int position) {
        holder.get_category.setText(arrCate.get(position).categoryName);
    }

    @Override
    public int getItemCount() {
        return arrCate == null ? 0: arrCate.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView get_category;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            get_category = itemView.findViewById(R.id.get_category);
        }
    }
}

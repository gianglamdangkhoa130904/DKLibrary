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

import com.example.finalvalue.Admin.UpdateActivity.UpdateCategoryActivity;
import com.example.finalvalue.Model.CategoryModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class CategoryAdminAdapter extends RecyclerView.Adapter<CategoryAdminAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<CategoryModel> arrCategory;

    public CategoryAdminAdapter(Context context, ArrayList<CategoryModel> arrCategory) {
        this.context = context;
        this.arrCategory = arrCategory;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.get_id.setText("ID: "+ arrCategory.get(position).categoryID);
        holder.get_name.setText("Name: "+ arrCategory.get(position).categoryName);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(holder.itemView.getContext(), UpdateCategoryActivity.class);
                a.putExtra("category_id", String.valueOf(arrCategory.get(position).categoryID));
                a.putExtra("category_name", String.valueOf(arrCategory.get(position).categoryName));
                holder.itemView.getContext().startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCategory == null? 0: arrCategory.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView get_id, get_name;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            get_id = itemView.findViewById(R.id.get_category_admin_ID);
            get_name = itemView.findViewById(R.id.get_category_admin_name);
            mainLayout = itemView.findViewById(R.id.admin_category_row_layout);
        }
    }
}

package com.example.finalvalue.Customer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalvalue.Admin.Adapter.PageAdapter;
import com.example.finalvalue.Model.PageModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class ListPageAdapter extends RecyclerView.Adapter<ListPageAdapter.MyViewHolder>{
    Context context;
    ArrayList<PageModel> arrPage;

    public ListPageAdapter(Context context, ArrayList<PageModel> arrPage) {
        this.context = context;
        this.arrPage = arrPage;
    }

    @NonNull
    @Override
    public ListPageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_page, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPageAdapter.MyViewHolder holder, int position) {
        holder.get_image.setImageBitmap(arrPage.get(position).pageImage);
    }

    @Override
    public int getItemCount() {
        return arrPage == null? 0: arrPage.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView get_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            get_image = itemView.findViewById(R.id.get_page_image);
        }
    }
}

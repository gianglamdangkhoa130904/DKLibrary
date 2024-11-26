package com.example.finalvalue.Customer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalvalue.R;

import java.util.ArrayList;

public class AutoScrollAdapter extends RecyclerView.Adapter<AutoScrollAdapter.ViewHolder> {
    Context context;
    ArrayList<Integer> arrImage;

    public AutoScrollAdapter(Context context, ArrayList<Integer> arrImage) {
        this.context = context;
        this.arrImage = arrImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_scrollbanner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.banner.setImageResource(arrImage.get(position));
    }

    @Override
    public int getItemCount() {
        return arrImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView banner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
        }
    }
}

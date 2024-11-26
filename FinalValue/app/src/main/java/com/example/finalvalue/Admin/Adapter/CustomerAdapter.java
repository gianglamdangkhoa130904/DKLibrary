package com.example.finalvalue.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalvalue.Model.CustomerModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<CustomerModel> arrCustomer;

    public CustomerAdapter(Context context, ArrayList<CustomerModel> arrCustomer) {
        this.context = context;
        this.arrCustomer = arrCustomer;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_customer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.get_id.setText("ID: " +arrCustomer.get(position).id);
        holder.get_name.setText("Name: "+arrCustomer.get(position).name);
        holder.get_username.setText("UserName: "+arrCustomer.get(position).username);
        holder.get_pass.setText("Password: "+arrCustomer.get(position).password);
        holder.get_email.setText("Email: "+arrCustomer.get(position).email);
        holder.get_DOB.setText("DOB: "+arrCustomer.get(position).date_birth);
        if(arrCustomer.get(position).imageCus == null){
            holder.get_image.setImageResource(R.drawable.message_ic);
        }
        else{
        holder.get_image.setImageBitmap(arrCustomer.get(position).imageCus);}
    }

    @Override
    public int getItemCount() {
        return arrCustomer == null? 0: arrCustomer.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView get_id, get_name, get_username, get_pass, get_email, get_DOB;
        private ImageView get_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            get_id = itemView.findViewById(R.id.get_id_customer);
            get_name = itemView.findViewById(R.id.get_name_customer);
            get_username = itemView.findViewById(R.id.get_username_customer);
            get_pass = itemView.findViewById(R.id.get_password_customer);
            get_email = itemView.findViewById(R.id.get_email_customer);
            get_DOB = itemView.findViewById(R.id.get_DOB_customer);
            get_image = itemView.findViewById(R.id.get_image_customer);
        }
    }
}

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

import com.example.finalvalue.Admin.UpdateActivity.UpdateAdminActivity;
import com.example.finalvalue.Model.AdminModel;
import com.example.finalvalue.R;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<AdminModel> arrAdmin;

    public AdminAdapter(Context context, ArrayList<AdminModel> arrAdmin) {
        this.context = context;
        this.arrAdmin = arrAdmin;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.MyViewHolder holder, int position) {
        holder.get_id.setText("ID: "+ arrAdmin.get(position).admin_ID);
        holder.get_username.setText("Username: "+ arrAdmin.get(position).admin_username);
        holder.get_pass.setText("Password: "+ arrAdmin.get(position).admin_password);
        holder.get_role.setText("Role: "+ arrAdmin.get(position).admin_role);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(holder.itemView.getContext(), UpdateAdminActivity.class);
                a.putExtra("admin_id", String.valueOf(arrAdmin.get(position).admin_ID));
                a.putExtra("admin_name", String.valueOf(arrAdmin.get(position).admin_username));
                a.putExtra("admin_pass", String.valueOf(arrAdmin.get(position).admin_password));
                a.putExtra("admin_role", String.valueOf(arrAdmin.get(position).admin_role));
                holder.itemView.getContext().startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrAdmin == null? 0: arrAdmin.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView get_id, get_username, get_pass, get_role;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            get_id = itemView.findViewById(R.id.get_admin_ID);
            get_username = itemView.findViewById(R.id.get_admin_username);
            get_pass = itemView.findViewById(R.id.get_admin_password);
            get_role = itemView.findViewById(R.id.get_admin_role);
            mainLayout = itemView.findViewById(R.id.admin_row_layout);
        }
    }
}

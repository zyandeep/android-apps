package com.example.zyandeep.mycontactaccess;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.INotificationSideChannel;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyRVadptar extends RecyclerView.Adapter<MyRVadptar.MyViewHolder> {

    Context context;
    List<MyContactInfo> contacts;

    public MyRVadptar(Context context) {
        this.context = context;
        contacts = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.item_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(contacts.get(position).getName());
        holder.phNo.setText(contacts.get(position).getPhoneNo());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    // add the data to the list
    public void addContacts(List<MyContactInfo> data) {
        contacts.clear();
        contacts.addAll(data);

        // notify the adaptar
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, phNo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            // load image on-the-fly with glide
            Glide.with(context).load(R.drawable.contact_icon).into(imageView);

            name = itemView.findViewById(R.id.contact_name_tv);
            phNo = itemView.findViewById(R.id.contact_ph_tv);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    String ph_no = contacts.get(index).getPhoneNo();

                    // make a call to that contact
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + ph_no));

                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
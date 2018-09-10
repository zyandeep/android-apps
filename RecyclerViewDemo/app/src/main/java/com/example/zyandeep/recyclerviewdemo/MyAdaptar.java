package com.example.zyandeep.recyclerviewdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MyAdaptar extends RecyclerView.Adapter<MyAdaptar.MyViewHolder> {
    String[] names, desc;
    int[] pics;
    Context ctx;


    MyAdaptar(Context ctx, String[] s1, String[] s2, int[] p) {
        this.ctx = ctx;
        names = s1;
        desc = s2;
        pics = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.i.setImageResource(pics[position]);
        holder.h.setText(names[position]);
        holder.d.setText(desc[position]);

        holder.i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, desc[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;        //total no of items
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView i;
        TextView h, d;


        public MyViewHolder(View itemView) {
            super(itemView);
            i = itemView.findViewById(R.id.avatar);
            h = itemView.findViewById(R.id.heading);
            d = itemView.findViewById(R.id.description);
        }
    }
}

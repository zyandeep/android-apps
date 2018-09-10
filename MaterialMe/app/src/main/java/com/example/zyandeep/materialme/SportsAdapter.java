package com.example.zyandeep.materialme;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.MyViewHolder> {

    private ArrayList<Sport> data;
    private Context ctx;

    SportsAdapter(ArrayList<Sport> d, Context c) {
        this.data = d;
        this.ctx = c;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.item_layout, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //Load image into views with Glide
        Glide.with(ctx).load(data.get(position).getImageResource()).into(holder.pic);


        holder.heading.setText(data.get(position).getTitle());
        holder.info.setText(data.get(position).getInfo());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView heading;
        TextView info;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pic = itemView.findViewById(R.id.imageView);
            heading = itemView.findViewById(R.id.heading_title);
            info = itemView.findViewById(R.id.subTitle);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();

                    Sport d = data.get(index);


                    // create a new intent and start the activity
                    Intent intent = new Intent(ctx, DetailActivity.class);

                    intent.putExtra("heading", d.getTitle());
                    intent.putExtra("image", d.getImageResource());
                    intent.putExtra("detail", d.getDetail());

                    ctx.startActivity(intent);
                }
            });

        }
    }
}

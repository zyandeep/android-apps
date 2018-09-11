package com.example.zyandeep.wordlistloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyRVAdapatar extends RecyclerView.Adapter<MyRVAdapatar.MyViewHolder> {

    private List<Word> words = new ArrayList<>();
    Context ctx;

    public MyRVAdapatar(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.ctx);
        View view = inflater.inflate(R.layout.item_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.word.setText(words.get(position).getWord());
        holder.desc.setText(words.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView word, desc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            word = itemView.findViewById(R.id.word_tv);
            desc = itemView.findViewById(R.id.desc_tv);
        }
    }


    public void addNewData(List<Word> data) {
        this.words.clear();
        this.words.addAll(data);

        // notify the adapatar
        notifyDataSetChanged();
    }
}
package com.example.zyandeep.wordlistsqlite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdaptar extends RecyclerView.Adapter<SearchAdaptar.MyViewHolder> {

    Context ctx;
    List<WordItem> myList;

    public SearchAdaptar(Context ctx) {
        this.ctx = ctx;
        myList = new ArrayList<WordItem>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.ctx);
        View view = inflater.inflate(R.layout.serach_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WordItem item = myList.get(position);
        holder.tv1.setText(item.getWord());
        holder.tv2.setText(item.getDesc());
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }


    public void addNewItems(List<WordItem> items) {
        myList.clear();
        myList.addAll(items);

        this.notifyDataSetChanged();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv1;
        TextView tv2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.word_term);
            tv2 = itemView.findViewById(R.id.desc_term);
        }
    }
}

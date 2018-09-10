package com.example.zyandeep.recyclerviewlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<String> dataSet;
    private LayoutInflater inflater;
    private Context ctx;

    MyAdapter(Context c, List<String> d) {
        this.ctx = c;
        this.dataSet = d;

        // inflate the layout of an item
        inflater = LayoutInflater.from(this.ctx);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv.setText(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.word_textView);
            tv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Log.d("Item position", ""+getLayoutPosition());
            //Log.d("Item count", ""+getItemCount());

            int itemPos = getLayoutPosition();
            String data = dataSet.get(itemPos);
            dataSet.set(itemPos, "Clicked " + data);

            // notify the adapter about the change
            MyAdapter.this.notifyDataSetChanged();
        }
    }
}

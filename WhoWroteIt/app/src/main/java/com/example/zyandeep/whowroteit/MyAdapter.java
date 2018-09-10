package com.example.zyandeep.whowroteit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<JSONObject> mData;
    Context context;

    public MyAdapter(Context context) {
        this.context = context;

        // initialise an empty arrayList
        mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.item_layout, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            holder.title.setText(mData.get(position).getString("title"));
            holder.author.setText(mData.get(position).getString("authors"));
        }
        catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**** A VERY CRUCIAL MATHOD ****/

    public void updateDataset(ArrayList<JSONObject> newData) {
        // clear the old data
        this.mData.clear();
        this.mData.addAll(newData);
        notifyDataSetChanged();
    }

    public void clearDataSet() {
        this.mData.clear();
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView author;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Ask Google for more info!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

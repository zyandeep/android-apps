package com.example.amar.getcontact;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

   List<Object> log_list;
   Context context;

    public LogAdapter(List<Object> log_list, Context context){

        this.log_list = log_list;
        this.context = context;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_layout,viewGroup,false);


        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LogViewHolder logViewHolder, final int pos) {

        final String data = (String) log_list.get(pos);

        //logViewHolder.tview.setText((CharSequence) log_list.get(pos));

        logViewHolder.tview.setText(data);

        logViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(logViewHolder.layout, data, Snackbar.LENGTH_INDEFINITE)
                .setAction("Delete", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, data, Toast.LENGTH_SHORT).show();

                        // removing data from set
                        MainActivity.logs.remove(data);

                        // removing data from array list
                        log_list.remove(data);

                        notifyItemRemoved(pos);

                        //writing to shared preference file
                        MainActivity.saveLogs();
                    }
                });
                snackbar.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return log_list.size();
    }


    public class LogViewHolder extends RecyclerView.ViewHolder {

        ImageView log_icon;
        TextView tview;
        View layout;

        public LogViewHolder(@NonNull final View itemView) {
            super(itemView);
            log_icon = itemView.findViewById(R.id.myimage);
            tview = itemView.findViewById(R.id.mytext);
            layout = itemView;
        }
    }
}
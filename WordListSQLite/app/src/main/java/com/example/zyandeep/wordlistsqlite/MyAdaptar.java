package com.example.zyandeep.wordlistsqlite;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MyAdaptar extends RecyclerView.Adapter<MyAdaptar.MyViewHolder>{

    Context ctx;

    public MyAdaptar(Context ctx) {
        this.ctx = ctx;;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.ctx);
        View itemView = inflater.inflate(R.layout.item_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ContentResolver cr = ctx.getContentResolver();
        Uri uri = Uri.parse(Contract.WordEntries.CONTENT_URI.toString() + "/" + position);

        String word = "", desc = "";
        int id = -1;

        final MyViewHolder h = holder;

        Cursor cursor = cr.query(uri, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex(Contract.WordEntries.COL_ID));
                word = cursor.getString(cursor.getColumnIndex(Contract.WordEntries.COL_WORD));
                desc = cursor.getString(cursor.getColumnIndex(Contract.WordEntries.COL_DESC));
            }

            cursor.close();
        }


        holder.tv.setText(word);
        holder.desc.setText(desc);

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder myDialog = new AlertDialog.Builder(ctx);
                myDialog.setCancelable(false);
                myDialog.setTitle("Delete the word?");
                myDialog.setMessage("Do you really want to delete?");
                myDialog.setPositiveButton("Oh Yeah!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });

                myDialog.setNegativeButton("Don't you dare!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                myDialog.show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(ctx, EditWordActivity.class);
                i.putExtra("id", wordItem.getId());
                i.putExtra("word", wordItem.getWord());
                i.putExtra("word_desc", wordItem.getDesc());
                i.putExtra("position", h.getAdapterPosition());

                ((MainActivity)ctx).startActivityForResult(i, MainActivity.EDIT_REQUEST);*/
            }
        });
    }


    @Override
    public int getItemCount() {
        // how many items to display in the recyclerView
        //return 10;

        Cursor cursor = ctx.getContentResolver().query(Contract.WordEntries.ROW_COUNT_URI,
                null, null, null, null);

        int rows = 0;

        if (cursor != null) {
            cursor.moveToFirst();
            rows = cursor.getInt(0);

            cursor.close();
        }

        return rows;
    }




    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv, desc;
        Button del, edit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.textView);
            desc = itemView.findViewById(R.id.desc);
            del = itemView.findViewById(R.id.del_button);
            edit = itemView.findViewById(R.id.edit_buton);
        }
    }
}
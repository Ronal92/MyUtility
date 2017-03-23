package com.jinwoo.android.myutility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private List<String> datas = new ArrayList<>();
    private static final String TAG = "MyItemRecycler";

    public MyItemRecyclerViewAdapter(Context context, List<String> datas) {
        Log.d("Monday", "MyItemRecyclerView");
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("Monday", "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("Monday", "onBindViewHolder");
        holder.imageUri = datas.get(position);
 //       holder.imageView.setImageURI(holder.imageUri); // 터진다.( out of Memory)

        Glide.with(context).load(holder.imageUri).into(holder.imageView);
    }

    @Override
        public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public String imageUri;

        public ViewHolder(View view) {

            super(view);
            Log.d("Monday", "ViewHolder");
            imageView = (ImageView) view.findViewById(R.id.id);
            imageUri = null;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭시 큰이미지 보여주기
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    context.stratActivity(intent);
                }
            });
        }
    }
}

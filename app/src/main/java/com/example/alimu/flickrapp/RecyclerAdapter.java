package com.example.alimu.flickrapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alimu.flickrapp.util.UtilityClass;

import java.util.List;

import static com.example.alimu.flickrapp.util.UtilityClass.createAlertDialog;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ItemClickListener itemClickListener;
    private String LOG_TAG = RecyclerAdapter.class.getSimpleName();

    List<String> imageUrlList;
    Context context;
    
    RecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setAdapterData(List<String> imageUrlList){
        this.imageUrlList = imageUrlList;
    }

    public void clearAdapterData(){
        if(imageUrlList!= null && imageUrlList.size() > 0) {
            this.imageUrlList.clear();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUrl = imageUrlList.get(position);

        if(checkForConnection()) {
            Glide.with(context).load(imageUrl).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return (imageUrlList == null)? 0: imageUrlList.size();
    }

    public boolean checkForConnection(){
        if(UtilityClass.checkNetworkConnectivity(context)) {
            Log.i(LOG_TAG,"Internet Connection Available");
            return true;
        }
        else{
            Log.i(LOG_TAG,"RecyclerAdapter offline");
            createAlertDialog(context);
        }

        return false;
    }
    
    // convenience method for getting data at click position
    String getItem(int id) {
        return imageUrlList.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}

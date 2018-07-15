package com.example.alimu.flickrapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.alimu.flickrapp.util.UtilityClass;

import java.util.List;

import static com.example.alimu.flickrapp.util.UtilityClass.createAlertDialog;

class VIEW_TYPES {
    public static final int Header = 1;
    public static final int Normal = 2;
    public static final int Footer = 3;
}

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
        int resource;

        if(viewType == VIEW_TYPES.Footer) {
            resource = R.layout.footer_layout;
        } else {
            resource = R.layout.item_layout;
        }

        View mView = LayoutInflater.from(parent.getContext())
                .inflate(resource, parent, false);

        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(isEndOfList(position)) {
            if(MainPresenter.pageNumber == MainPresenter.pagesCount) {
                holder.button.setVisibility(View.INVISIBLE);
            } else {
                holder.button.setVisibility(View.VISIBLE);
                handleLoadMoreButtonAction(holder);
            }
        } else {
            String imageUrl = imageUrlList.get(position);

            if (checkForConnection()) {
                Glide.with(context).load(imageUrl).into(holder.imageView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (imageUrlList == null)? 0: imageUrlList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(isEndOfList(position)) {
            return VIEW_TYPES.Footer;
        } else {
            return VIEW_TYPES.Normal;
        }
    }

    protected void handleLoadMoreButtonAction(@NonNull final ViewHolder holder){
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOG_TAG,"Load more button clicked");
                holder.button.setVisibility(View.INVISIBLE);

                if (itemClickListener != null) {
                    itemClickListener.onButtonClicked(view, ++MainPresenter.pageNumber);
                }
            }
        });
    }

    protected boolean isEndOfList(int position){
        return (position >= imageUrlList.size()) ? true : false;
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
    
    //Convenience method for getting data at click position
    String getItem(int position) {
        if(position >= imageUrlList.size()) {
            return null;
        }

        return imageUrlList.get(position);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    //Parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClicked(View view, int position);
        void onButtonClicked(View view, int pageNumber);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        Button button;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            button = view.findViewById(R.id.loadMoreButton);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClicked(view, getAdapterPosition());
            }
        }
    }
}

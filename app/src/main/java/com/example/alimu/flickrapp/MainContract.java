package com.example.alimu.flickrapp;

import android.content.Context;

import com.example.alimu.flickrapp.components.AbstractPresenter;

import java.util.List;

public interface MainContract {
    interface View {
        void stopLoadingAnimation();
        void updateRecyclerView(List<String> responseList);
    }

    abstract class Presenter extends AbstractPresenter<View> {
        abstract void setContext(Context context);
        abstract Context getContext();
        abstract void requestAPICall();
        abstract void setView(MainContract.View view);
    }
}

package com.example.alimu.flickrapp.components;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

/*
* A base class for Presenters from MVP. Class helps to attach/detach views.
* */
public abstract class AbstractPresenter<T extends Object> {
    private T mView;

    public final void attachView(@NonNull T view){
        Objects.requireNonNull(view);
        this.mView = view;
        this.onViewAttached();
    }

    public final void detachView(){
        this.mView = null;
        this.onViewDetached();
    }

    @Nullable
    protected final T getView(){
        return mView;
    }

    protected void onViewAttached(){
        //To be overridden by sub classes
    }

    protected void onViewDetached(){
        //To be overridden by sub classes
    }
}

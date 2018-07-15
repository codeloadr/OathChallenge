package com.example.alimu.flickrapp.config;

import com.example.alimu.flickrapp.MainContract;
import com.example.alimu.flickrapp.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {
    @Provides
    @ServiceScope
    MainContract.Presenter provideMainPresenter(){
        return new MainPresenter();
    }
}

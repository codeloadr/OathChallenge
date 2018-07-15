package com.example.alimu.flickrapp.config;

import com.example.alimu.flickrapp.MainFragment;

import dagger.Component;

@Component(modules = {ServiceModule.class})
@ServiceScope
public interface ServiceComponent {
    void inject(MainFragment mainFragment);

    @Component.Builder
    interface Builder{
        ServiceComponent build();

        Builder getServiceModule(ServiceModule serviceModule);
    }
}

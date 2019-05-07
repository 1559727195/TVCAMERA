package com.massky.tvcamera.di.component;

import android.app.Activity;

import com.massky.tvcamera.MainActivity;
import com.massky.tvcamera.di.module.ActivityModule;
import com.massky.tvcamera.di.module.EntityModule;
import com.massky.tvcamera.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class,modules = {ActivityModule.class, EntityModule.class})
public interface ActivityComponent {
    Activity getActivity();
    void inject(MainActivity mainActivity);
}

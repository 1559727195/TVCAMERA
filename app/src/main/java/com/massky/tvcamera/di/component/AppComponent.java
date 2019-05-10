package com.massky.tvcamera.di.component;

import android.content.Context;

import com.massky.domain.entity.repository.post.CommonRepository;
import com.massky.domain.entity.repository.weather.WeatherRepository;
import com.massky.tvcamera.di.module.AppModule;
import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context provideContext();
    OkHttpClient provideOkhttpClient();
    WeatherRepository provideWeatherRepository();
    CommonRepository provideCommonRepository();

}

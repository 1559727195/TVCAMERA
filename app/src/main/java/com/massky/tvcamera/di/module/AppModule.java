package com.massky.tvcamera.di.module;


import android.content.Context;
import com.massky.data.api.HttpHelper;
import com.massky.data.common.CommonDataRepository;
import com.massky.data.weather.WeatherDataRepository;
import com.massky.domain.entity.repository.post.CommonRepository;
import com.massky.domain.entity.repository.weather.WeatherRepository;
import com.massky.tvcamera.Utils.App;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }


    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(HttpHelper httpHelper) {
        return httpHelper.getOkHttpClient();
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(WeatherDataRepository weatherRepository) {
        return weatherRepository;
    }

    @Provides
    @Singleton
    CommonRepository provideCommonRepository(CommonDataRepository commonDataRepository) {
        return commonDataRepository;
    }
}

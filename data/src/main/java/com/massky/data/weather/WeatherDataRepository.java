package com.massky.data.weather;

import com.google.gson.JsonObject;
import com.massky.data.api.HttpHelper;
import com.massky.data.service.WeatherService;
import com.massky.data.util.RxTransformerUtil;
import com.massky.domain.entity.repository.weather.WeatherRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;
import retrofit2.Response;

public class WeatherDataRepository implements WeatherRepository {
    private WeatherService mWeatherService;

    @Inject
    public WeatherDataRepository(HttpHelper httpHelper) {
        mWeatherService = httpHelper.getWeatherService();
    }

    @Override
    public Flowable<JsonObject> getWeatherList(String key, String city, String province) {
        return mWeatherService.getWeather_HENGYANG(key, city, province)
                .map(Response::body)
                .compose(RxTransformerUtil.normalTransformer());
    }
}

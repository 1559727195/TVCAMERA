package com.massky.domain.weather;

import android.text.TextUtils;
import com.google.gson.JsonObject;
import com.massky.domain.constant.CodeConstant;
import com.massky.domain.entity.repository.weather.WeatherRepository;
import com.massky.domain.entity.weather.WeatherXinZhiEntity;
import com.massky.domain.exception.ApiException;
import com.massky.domain.util.JsonUtil;

import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherUseCase extends UseCase<WeatherXinZhiEntity.FinalEntity,WeatherUseCase.Params>{
    private final WeatherRepository mWeatherRepository;

    @Inject
    public WeatherUseCase(WeatherRepository weatherRepository) {
        mWeatherRepository = weatherRepository;
    }

    @Override
    protected Flowable<WeatherXinZhiEntity.FinalEntity> buildUseCaseObservable(Params params) {
        return mWeatherRepository.getWeatherList(params.key,params.city,params.province)
            .observeOn(Schedulers.io())
                .flatMap(this::handleData)
                .observeOn(AndroidSchedulers.mainThread());
    }


    private Publisher<WeatherXinZhiEntity.FinalEntity> handleData(JsonObject jsonObject) {
        if (jsonObject == null) {
            return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
        }
        if (jsonObject.has("results")) {
            // 请求成功，转换数据
            List<WeatherXinZhiEntity.ResultsEntity> results = JsonUtil.fromJsonList(jsonObject.get("results").toString(), WeatherXinZhiEntity.ResultsEntity.class);
            if (results == null || results.isEmpty()) {
                return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
            }
            WeatherXinZhiEntity.ResultsEntity temWeather = results.get(0);
            WeatherXinZhiEntity.ResultsEntity.LocationEntity locationEntity = temWeather.getLocation();
            String id = locationEntity == null ? String.valueOf(System.currentTimeMillis()) : locationEntity.getId();
            String location = locationEntity == null ? "— —" : locationEntity.getName();
            WeatherXinZhiEntity.ResultsEntity.NowEntity nowEntity = temWeather.getNow();
            String text = nowEntity == null ? "— —" : nowEntity.getText();
            String temperature = nowEntity == null ? "— —" : String.format("%s ℃", nowEntity.getTemperature());
            String weatherLastUpdate = temWeather.getLast_update();
            int indexOf = weatherLastUpdate.indexOf("T");
            String lastUpdate = TextUtils.isEmpty(weatherLastUpdate) ? "— —" : weatherLastUpdate.substring(indexOf + 1, indexOf + 6);
            String code = nowEntity == null ? "99" : nowEntity.getCode();
            return Flowable.just(new WeatherXinZhiEntity.FinalEntity(id, location, text, temperature, lastUpdate, code));
        }
        if (jsonObject.has("status")) {
            // 请求错误，提示用户
            return Flowable.error(new ApiException(CodeConstant.CODE_DATA_ERROR, jsonObject.get("status").toString()));
        }
        return Flowable.error(new ApiException(CodeConstant.CODE_DATA_ERROR, "数据错误，没法快乐玩耍！"));
    }

    public static final class Params {

        private static final String KEY = "1a65fe6cfd250";
        private static final String DEFAULT_LANGUAGE = "zh-Hans";
        private static final String DEFAULT_UNIT = "c";

        private final String key;

        public String city;
        public String province;

        private Params(String key, String city, String province) {
            this.key = key;
            this.city = city;
            this.province = province;
        }

        //public static Params get(String location) {
        //    return new Params(KEY, location, DEFAULT_LANGUAGE, DEFAULT_UNIT);
        //}

        public static Params get(String city,String province) {
            return new Params(KEY,city,province);
        }
    }
}

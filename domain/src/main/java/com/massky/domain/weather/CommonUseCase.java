package com.massky.domain.weather;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.massky.domain.constant.CodeConstant;
import com.massky.domain.entity.repository.post.CommonRepository;
import com.massky.domain.entity.repository.weather.WeatherRepository;
import com.massky.domain.entity.weather.WeatherXinZhiEntity;
import com.massky.domain.exception.ApiException;
import com.massky.domain.util.JsonUtil;

import org.reactivestreams.Publisher;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommonUseCase extends UseCase<WeatherXinZhiEntity.CommonEntity, HashMap> {
    private final CommonRepository mWeatherRepository;

    @Inject
    public CommonUseCase(CommonRepository weatherRepository) {
        mWeatherRepository = weatherRepository;
    }

    @Override
    protected Flowable<WeatherXinZhiEntity.CommonEntity> buildUseCaseObservable(HashMap map) {
        return mWeatherRepository.getCommonList(map)
                .observeOn(Schedulers.io())
                .flatMap(this::handleData)
                .observeOn(AndroidSchedulers.mainThread());
    }


    private Publisher<WeatherXinZhiEntity.CommonEntity> handleData(JsonObject jsonObject) {
        if (jsonObject == null) {
            return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
        }
        if (jsonObject.has("result")) {
            // 请求成功，转换数据
            String json = jsonObject.toString();
           WeatherXinZhiEntity.CommonEntity results = JSON.parseObject(json, WeatherXinZhiEntity.CommonEntity.class);
            if (results == null) {
                return Flowable.error(new ApiException(CodeConstant.CODE_EMPTY, "数据为空，请求个毛线！"));
            }
            results.setId(String.valueOf(System.currentTimeMillis()));
            return Flowable.just(results);
        }
        if (jsonObject.has("status")) {
            // 请求错误，提示用户
            return Flowable.error(new ApiException(CodeConstant.CODE_DATA_ERROR, jsonObject.get("status").toString()));
        }
        return Flowable.error(new ApiException(CodeConstant.CODE_DATA_ERROR, "数据错误，没法快乐玩耍！"));
    }
}

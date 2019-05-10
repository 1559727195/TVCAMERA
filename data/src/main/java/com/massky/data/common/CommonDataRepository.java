package com.massky.data.common;

import com.google.gson.JsonObject;
import com.massky.data.api.HttpHelper;
import com.massky.data.service.CommonPostService;
import com.massky.data.service.WeatherService;
import com.massky.data.util.RxTransformerUtil;
import com.massky.domain.entity.repository.post.CommonRepository;
import com.massky.domain.entity.repository.weather.WeatherRepository;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Flowable;
import retrofit2.Response;

public class CommonDataRepository implements CommonRepository {
    private CommonPostService commonPostService;

    @Inject
    public CommonDataRepository(HttpHelper httpHelper) {
        commonPostService = httpHelper.getCommonPostService();
    }


    @Override
    public Flowable<JsonObject> getCommonList(HashMap map) {
        return commonPostService.post(map)
                .map(Response::body)
                .compose(RxTransformerUtil.normalTransformer());
    }
}

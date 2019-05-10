package com.massky.data.service;

import com.google.gson.JsonObject;

import java.util.HashMap;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommonPostService {
    //    String HOST = "https://api.seniverse.com/";\
    String HOST = "https://app.sraum.com/";

    /**
     * 缓存时间为1分钟
     */
    @POST("SmartHome/getPM25")
    Flowable<Response<JsonObject>> post(@Body HashMap map);


}

package com.massky.domain.entity.repository.post;

import com.google.gson.JsonObject;

import java.util.HashMap;

import io.reactivex.Flowable;

public interface CommonRepository {
    Flowable<JsonObject> getCommonList(HashMap map);
}

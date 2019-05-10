/*
  Copyright 2017 Sun Jian
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.massky.tvcamera.presenter;

import com.massky.domain.entity.weather.WeatherXinZhiEntity;
import com.massky.domain.weather.CommonUseCase;
import com.massky.domain.weather.WeatherUseCase;
import com.massky.tvcamera.base.BasePresenter;
import com.massky.tvcamera.base.BaseSubscriber;
import com.massky.tvcamera.di.scope.ActivityScope;
import com.massky.tvcamera.presenter.contract.HomeContract;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


/**
 * @author: sunjian
 * created on: 2017/9/19 下午5:05
 * description: https://github.com/crazysunj/CrazyDaily
 */
@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {


    private WeatherUseCase mWeatherUseCase;
    private CommonUseCase commonUseCase;

    @Inject
    HomePresenter(WeatherUseCase weatherUseCase, CommonUseCase commonUseCase) {
        this.mWeatherUseCase = weatherUseCase;
        this.commonUseCase = commonUseCase;
    }

    @Override
    public void getWeather(String city,String province) {
        mWeatherUseCase.execute(WeatherUseCase.Params.get(city,province),new BaseSubscriber<WeatherXinZhiEntity.FinalEntity>() {
            @Override
            public void onNext(WeatherXinZhiEntity.FinalEntity weatherEntity) {
                if (mView != null) {
                    mView.showWeather(weatherEntity);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (mView != null) {
                    mView.showError(e.getMessage());
                }
            }
        });
    }

    @Override
    public void post(Map map) {
        commonUseCase.execute((HashMap) map, new BaseSubscriber<WeatherXinZhiEntity.CommonEntity>() {
            @Override
            public void onNext(WeatherXinZhiEntity.CommonEntity commonEntity) {
                if (mView != null) {
//                    mView.showError(e.getMessage());
                    mView.showPost(commonEntity);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (mView != null) {
                    mView.showError(e.getMessage());
                }
            }
        });
    }

}


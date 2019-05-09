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
package com.massky.domain.entity.weather;

import com.massky.domain.entity.base.MultiTypeIdEntity;

import java.util.List;

/**
 * @author: sunjian
 * created on: 2018/10/29 下午1:51
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class WeatherXinZhiEntity {

      public static class FinalEntity extends MultiTypeIdEntity {

        public static final int TYPE_WEATHER = 2;

        /**
         * id
         */
        private String id;
        /**
         * 地名
         */
        private int pm25;



          public int getPm25() {
              return pm25;
          }

          public String getHumidity() {
              return humidity;
          }

          public String getTemperature() {
              return temperature;
          }

          /**
         * 天气名
         */
        private String humidity;
        /**
         * 温度
         */
        private String temperature;


        public FinalEntity(String id, int  pm25, String humidity, String temperature) {
            this.id = id;
            this.pm25 = pm25;
            this.humidity = humidity;
            this.temperature = temperature;
        }


        @Override
        public String getStringId() {
            return id;
        }

        @Override
        public int getItemType() {
            return TYPE_WEATHER;
        }
    }

     public static class ResultsEntity {

         /**
          * msg : success
          * result : [{"airCondition":"优","airQuality":{"aqi":46,"city":"衡阳","district":"衡阳","fetureData":[{"aqi":42,"date":"2019-05-10","quality":"优"},{"aqi":39,"date":"2019-05-11","quality":"优"},{"aqi":48,"date":"2019-05-12","quality":"优"},{"aqi":42,"date":"2019-05-13","quality":"优"}],"hourData":[{"aqi":46,"dateTime":"2019-05-09 08:00:00"},{"aqi":42,"dateTime":"2019-05-09 07:00:00"},{"aqi":39,"dateTime":"2019-05-09 06:00:00"},{"aqi":38,"dateTime":"2019-05-09 05:00:00"},{"aqi":37,"dateTime":"2019-05-09 04:00:00"},{"aqi":39,"dateTime":"2019-05-09 03:00:00"},{"aqi":42,"dateTime":"2019-05-09 02:00:00"},{"aqi":45,"dateTime":"2019-05-09 01:00:00"},{"aqi":44,"dateTime":"2019-05-09 00:00:00"},{"aqi":48,"dateTime":"2019-05-08 23:00:00"},{"aqi":53,"dateTime":"2019-05-08 22:00:00"},{"aqi":55,"dateTime":"2019-05-08 21:00:00"},{"aqi":51,"dateTime":"2019-05-08 20:00:00"},{"aqi":51,"dateTime":"2019-05-08 19:00:00"},{"aqi":42,"dateTime":"2019-05-08 18:00:00"},{"aqi":46,"dateTime":"2019-05-08 17:00:00"},{"aqi":49,"dateTime":"2019-05-08 16:00:00"},{"aqi":53,"dateTime":"2019-05-08 15:00:00"},{"aqi":50,"dateTime":"2019-05-08 14:00:00"},{"aqi":50,"dateTime":"2019-05-08 13:00:00"},{"aqi":51,"dateTime":"2019-05-08 12:00:00"},{"aqi":34,"dateTime":"2019-05-08 11:00:00"},{"aqi":33,"dateTime":"2019-05-08 10:00:00"},{"aqi":32,"dateTime":"2019-05-08 09:00:00"}],"no2":27,"pm10":46,"pm25":24,"province":"湖南","quality":"优","so2":12,"updateTime":"2019-05-09 08:00:00"},"city":"衡阳","coldIndex":"低发期","date":"2019-05-09","distrct":"衡阳","dressingIndex":"单衣类","exerciseIndex":"非常适宜","future":[{"date":"2019-05-09","dayTime":"多云","night":"多云","temperature":"23°C / 16°C","week":"今天","wind":"北风 小于3级"},{"date":"2019-05-10","dayTime":"多云","night":"阵雨","temperature":"25°C / 19°C","week":"星期五","wind":"北风 小于3级"},{"date":"2019-05-11","dayTime":"阵雨","night":"小雨","temperature":"26°C / 20°C","week":"星期六","wind":"北风 小于3级"},{"date":"2019-05-12","dayTime":"中雨","night":"中雨","temperature":"25°C / 20°C","week":"星期日","wind":"北风 小于3级"},{"date":"2019-05-13","dayTime":"小雨","night":"多云","temperature":"26°C / 23°C","week":"星期一","wind":"北风 小于3级"}],"humidity":"湿度：88%","pollutionIndex":"46","province":"湖南","sunrise":"07:19","sunset":"17:48","temperature":"16℃","time":"08:52","updateTime":"20190509091330","washIndex":"","weather":"多云","week":"周四","wind":"北风 小于3级"}]
          * retCode : 200
          */

             private String airCondition;
             private AirQualityBean airQuality;
             private String city;
             private String coldIndex;
             private String date;
             private String distrct;
             private String dressingIndex;
             private String exerciseIndex;
             private String humidity;
             private String pollutionIndex;
             private String province;
             private String sunrise;
             private String sunset;
             private String temperature;
             private String time;
             private String updateTime;
             private String washIndex;
             private String weather;
             private String week;
             private String wind;
             private List<FutureBean> future;

             public String getAirCondition() {
                 return airCondition;
             }

             public void setAirCondition(String airCondition) {
                 this.airCondition = airCondition;
             }

             public AirQualityBean getAirQuality() {
                 return airQuality;
             }

             public void setAirQuality(AirQualityBean airQuality) {
                 this.airQuality = airQuality;
             }

             public String getCity() {
                 return city;
             }

             public void setCity(String city) {
                 this.city = city;
             }

             public String getColdIndex() {
                 return coldIndex;
             }

             public void setColdIndex(String coldIndex) {
                 this.coldIndex = coldIndex;
             }

             public String getDate() {
                 return date;
             }

             public void setDate(String date) {
                 this.date = date;
             }

             public String getDistrct() {
                 return distrct;
             }

             public void setDistrct(String distrct) {
                 this.distrct = distrct;
             }

             public String getDressingIndex() {
                 return dressingIndex;
             }

             public void setDressingIndex(String dressingIndex) {
                 this.dressingIndex = dressingIndex;
             }

             public String getExerciseIndex() {
                 return exerciseIndex;
             }

             public void setExerciseIndex(String exerciseIndex) {
                 this.exerciseIndex = exerciseIndex;
             }

             public String getHumidity() {
                 return humidity;
             }

             public void setHumidity(String humidity) {
                 this.humidity = humidity;
             }

             public String getPollutionIndex() {
                 return pollutionIndex;
             }

             public void setPollutionIndex(String pollutionIndex) {
                 this.pollutionIndex = pollutionIndex;
             }

             public String getProvince() {
                 return province;
             }

             public void setProvince(String province) {
                 this.province = province;
             }

             public String getSunrise() {
                 return sunrise;
             }

             public void setSunrise(String sunrise) {
                 this.sunrise = sunrise;
             }

             public String getSunset() {
                 return sunset;
             }

             public void setSunset(String sunset) {
                 this.sunset = sunset;
             }

             public String getTemperature() {
                 return temperature;
             }

             public void setTemperature(String temperature) {
                 this.temperature = temperature;
             }

             public String getTime() {
                 return time;
             }

             public void setTime(String time) {
                 this.time = time;
             }

             public String getUpdateTime() {
                 return updateTime;
             }

             public void setUpdateTime(String updateTime) {
                 this.updateTime = updateTime;
             }

             public String getWashIndex() {
                 return washIndex;
             }

             public void setWashIndex(String washIndex) {
                 this.washIndex = washIndex;
             }

             public String getWeather() {
                 return weather;
             }

             public void setWeather(String weather) {
                 this.weather = weather;
             }

             public String getWeek() {
                 return week;
             }

             public void setWeek(String week) {
                 this.week = week;
             }

             public String getWind() {
                 return wind;
             }

             public void setWind(String wind) {
                 this.wind = wind;
             }

             public List<FutureBean> getFuture() {
                 return future;
             }

             public void setFuture(List<FutureBean> future) {
                 this.future = future;
             }

             public static class AirQualityBean {
                 /**
                  * aqi : 46
                  * city : 衡阳
                  * district : 衡阳
                  * fetureData : [{"aqi":42,"date":"2019-05-10","quality":"优"},{"aqi":39,"date":"2019-05-11","quality":"优"},{"aqi":48,"date":"2019-05-12","quality":"优"},{"aqi":42,"date":"2019-05-13","quality":"优"}]
                  * hourData : [{"aqi":46,"dateTime":"2019-05-09 08:00:00"},{"aqi":42,"dateTime":"2019-05-09 07:00:00"},{"aqi":39,"dateTime":"2019-05-09 06:00:00"},{"aqi":38,"dateTime":"2019-05-09 05:00:00"},{"aqi":37,"dateTime":"2019-05-09 04:00:00"},{"aqi":39,"dateTime":"2019-05-09 03:00:00"},{"aqi":42,"dateTime":"2019-05-09 02:00:00"},{"aqi":45,"dateTime":"2019-05-09 01:00:00"},{"aqi":44,"dateTime":"2019-05-09 00:00:00"},{"aqi":48,"dateTime":"2019-05-08 23:00:00"},{"aqi":53,"dateTime":"2019-05-08 22:00:00"},{"aqi":55,"dateTime":"2019-05-08 21:00:00"},{"aqi":51,"dateTime":"2019-05-08 20:00:00"},{"aqi":51,"dateTime":"2019-05-08 19:00:00"},{"aqi":42,"dateTime":"2019-05-08 18:00:00"},{"aqi":46,"dateTime":"2019-05-08 17:00:00"},{"aqi":49,"dateTime":"2019-05-08 16:00:00"},{"aqi":53,"dateTime":"2019-05-08 15:00:00"},{"aqi":50,"dateTime":"2019-05-08 14:00:00"},{"aqi":50,"dateTime":"2019-05-08 13:00:00"},{"aqi":51,"dateTime":"2019-05-08 12:00:00"},{"aqi":34,"dateTime":"2019-05-08 11:00:00"},{"aqi":33,"dateTime":"2019-05-08 10:00:00"},{"aqi":32,"dateTime":"2019-05-08 09:00:00"}]
                  * no2 : 27
                  * pm10 : 46
                  * pm25 : 24
                  * province : 湖南
                  * quality : 优
                  * so2 : 12
                  * updateTime : 2019-05-09 08:00:00
                  */

                 private int aqi;
                 private String city;
                 private String district;
                 private int no2;
                 private int pm10;
                 private int pm25;
                 private String province;
                 private String quality;
                 private int so2;
                 private String updateTime;
                 private List<FetureDataBean> fetureData;
                 private List<HourDataBean> hourData;

                 public int getAqi() {
                     return aqi;
                 }

                 public void setAqi(int aqi) {
                     this.aqi = aqi;
                 }

                 public String getCity() {
                     return city;
                 }

                 public void setCity(String city) {
                     this.city = city;
                 }

                 public String getDistrict() {
                     return district;
                 }

                 public void setDistrict(String district) {
                     this.district = district;
                 }

                 public int getNo2() {
                     return no2;
                 }

                 public void setNo2(int no2) {
                     this.no2 = no2;
                 }

                 public int getPm10() {
                     return pm10;
                 }

                 public void setPm10(int pm10) {
                     this.pm10 = pm10;
                 }

                 public int getPm25() {
                     return pm25;
                 }

                 public void setPm25(int pm25) {
                     this.pm25 = pm25;
                 }

                 public String getProvince() {
                     return province;
                 }

                 public void setProvince(String province) {
                     this.province = province;
                 }

                 public String getQuality() {
                     return quality;
                 }

                 public void setQuality(String quality) {
                     this.quality = quality;
                 }

                 public int getSo2() {
                     return so2;
                 }

                 public void setSo2(int so2) {
                     this.so2 = so2;
                 }

                 public String getUpdateTime() {
                     return updateTime;
                 }

                 public void setUpdateTime(String updateTime) {
                     this.updateTime = updateTime;
                 }

                 public List<FetureDataBean> getFetureData() {
                     return fetureData;
                 }

                 public void setFetureData(List<FetureDataBean> fetureData) {
                     this.fetureData = fetureData;
                 }

                 public List<HourDataBean> getHourData() {
                     return hourData;
                 }

                 public void setHourData(List<HourDataBean> hourData) {
                     this.hourData = hourData;
                 }

                 public static class FetureDataBean {
                     /**
                      * aqi : 42
                      * date : 2019-05-10
                      * quality : 优
                      */

                     private int aqi;
                     private String date;
                     private String quality;

                     public int getAqi() {
                         return aqi;
                     }

                     public void setAqi(int aqi) {
                         this.aqi = aqi;
                     }

                     public String getDate() {
                         return date;
                     }

                     public void setDate(String date) {
                         this.date = date;
                     }

                     public String getQuality() {
                         return quality;
                     }

                     public void setQuality(String quality) {
                         this.quality = quality;
                     }
                 }

                 public static class HourDataBean {
                     /**
                      * aqi : 46
                      * dateTime : 2019-05-09 08:00:00
                      */

                     private int aqi;
                     private String dateTime;

                     public int getAqi() {
                         return aqi;
                     }

                     public void setAqi(int aqi) {
                         this.aqi = aqi;
                     }

                     public String getDateTime() {
                         return dateTime;
                     }

                     public void setDateTime(String dateTime) {
                         this.dateTime = dateTime;
                     }
                 }
             }

             public static class FutureBean {
                 /**
                  * date : 2019-05-09
                  * dayTime : 多云
                  * night : 多云
                  * temperature : 23°C / 16°C
                  * week : 今天
                  * wind : 北风 小于3级
                  */

                 private String date;
                 private String dayTime;
                 private String night;
                 private String temperature;
                 private String week;
                 private String wind;

                 public String getDate() {
                     return date;
                 }

                 public void setDate(String date) {
                     this.date = date;
                 }

                 public String getDayTime() {
                     return dayTime;
                 }

                 public void setDayTime(String dayTime) {
                     this.dayTime = dayTime;
                 }

                 public String getNight() {
                     return night;
                 }

                 public void setNight(String night) {
                     this.night = night;
                 }

                 public String getTemperature() {
                     return temperature;
                 }

                 public void setTemperature(String temperature) {
                     this.temperature = temperature;
                 }

                 public String getWeek() {
                     return week;
                 }

                 public void setWeek(String week) {
                     this.week = week;
                 }

                 public String getWind() {
                     return wind;
                 }

                 public void setWind(String wind) {
                     this.wind = wind;
                 }
             }
         }

}

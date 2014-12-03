package kr.co.aroundthetruck.admin.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sehonoh on 14. 12. 3..
 */
public class HourlyWeatherModel {
    @SerializedName("hourly")
    private List<WeatherModel> weatherList;

    public List<WeatherModel> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<WeatherModel> weatherList) {
        this.weatherList = weatherList;
    }
}

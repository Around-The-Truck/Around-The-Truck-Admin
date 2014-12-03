package kr.co.aroundthetruck.admin.loader;

import kr.co.aroundthetruck.admin.model.CurrentWeatherModel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by sehonoh on 14. 12. 3..
 */
public interface WeatherLoader {

    static final String[] WEATHER_PLANET_HEADER = {
            "Accept:application/json",
            "appKey:07f8befa-8de2-3b00-9c3c-fdfd051bec30"
    };

    @Headers({
            "Accept:application/json",
            "appKey:07f8befa-8de2-3b00-9c3c-fdfd051bec30"
    })
    @GET("/current/hourly")
    void loadWeather(@Query("version") String version, @Query("lat") String lat, @Query("lon") String lon, Callback<CurrentWeatherModel> callback);

    /*
        http://apis.skplanetx.com/weather/current/hourly?version=1&lat=37.5714000000&lon=126.9658000000
     */
}

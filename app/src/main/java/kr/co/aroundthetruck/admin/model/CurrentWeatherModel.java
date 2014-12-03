package kr.co.aroundthetruck.admin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sehonoh on 14. 12. 3..
 */
public class CurrentWeatherModel {
    /*
        {
            "result": {
                "message": "성공",
                "code": 9200,
                "requestUrl": "/weather/current/hourly?lon=126.9658000000&version=1&lat=37.5714000000"
            },
            "common": {
                "alertYn": "Y",
                "stormYn": "N"
            },
            "weather": {
                "hourly": [
                    {
                        "grid": {
                            "latitude": "37.5798800000",
                            "longitude": "126.9893600000",
                            "city": "서울",
                            "county": "중구",
                            "village": "을지로2가"
                        },
                        "wind": {
                            "wdir": "272.00",
                            "wspd": "2.80"
                        },
                        "precipitation": {
                            "type": "0",
                            "sinceOntime": "0.00"
                        },
                        "sky": {
                            "name": "구름많음",
                            "code": "SKY_O03"
                        },
                        "temperature": {
                            "tc": "-2.10",
                            "tmax": "0.00",
                            "tmin": "-5.00"
                        },
                        "humidity": "66.00",
                        "lightning": "0",
                        "timeRelease": "2014-12-03 19:00:00"
                    }
                ]
            }
        }
     */

    @SerializedName("weather")
    private HourlyWeatherModel weather;

    public HourlyWeatherModel getWeather() {
        return weather;
    }

    public void setWeather(HourlyWeatherModel weather) {
        this.weather = weather;
    }
}

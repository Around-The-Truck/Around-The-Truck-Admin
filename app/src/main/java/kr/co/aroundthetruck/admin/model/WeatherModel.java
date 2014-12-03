package kr.co.aroundthetruck.admin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sehonoh on 14. 12. 3..
 */
public class WeatherModel {


    /*
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
     */

    // 지역
    @SerializedName("grid")
    private GridModel grid;

    // 하늘 상태
    @SerializedName("sky")
    private SkyModel sky;

    // 기온
    @SerializedName("temperature")
    private TemperatureModel temperature;

    public GridModel getGrid() {
        return grid;
    }

    public void setGrid(GridModel grid) {
        this.grid = grid;
    }

    public SkyModel getSky() {
        return sky;
    }

    public void setSky(SkyModel sky) {
        this.sky = sky;
    }

    public TemperatureModel getTemperature() {
        return temperature;
    }

    public void setTemperature(TemperatureModel temperature) {
        this.temperature = temperature;
    }
}

package kr.co.aroundthetruck.admin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sehonoh on 14. 12. 3..
 */
public class TemperatureModel {
    /*
        "temperature": {
            "tc": "-2.60",
            "tmax": "0.00",
            "tmin": "-5.00"
        }
    */

    // 현재 기온
    @SerializedName("tc")
    private String tc;

    // 오늘 최고 기온
    @SerializedName("tmax")
    private String tmax;

    // 오늘 최저 기온
    @SerializedName("tmin")
    private String tmin;

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getTmax() {
        return tmax;
    }

    public void setTmax(String tmax) {
        this.tmax = tmax;
    }

    public String getTmin() {
        return tmin;
    }

    public void setTmin(String tmin) {
        this.tmin = tmin;
    }

    // 내일/모레 날씨
    // 기온, 기상 상태, 오늘보다 +- 몇도
}

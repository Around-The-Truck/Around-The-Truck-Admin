package kr.co.aroundthetruck.admin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sehonoh on 14. 12. 3..
 */
public class GridModel {
    /*
        "grid": {
            "latitude": "37.5798800000",
            "longitude": "126.9893600000",
            "city": "서울",
            "county": "중구",
            "village": "을지로2가"
        }
    */

    // 위도
    @SerializedName("latitude")
    private String latitude;

    // 경도
    @SerializedName("longitude")
    private String longitude;

    // 시
    @SerializedName("city")
    private String city;

    // 구
    @SerializedName("country")
    private String country;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

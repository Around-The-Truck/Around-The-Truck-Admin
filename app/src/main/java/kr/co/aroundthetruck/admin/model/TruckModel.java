package kr.co.aroundthetruck.admin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sehonoh on 2015. 1. 4..
 */
public class TruckModel {

    @SerializedName("idx")
    private int idx;

    @SerializedName("name")
    private String name;

    @SerializedName("gps_latitude")
    private double latitude;

    @SerializedName("gps_longitude")
    private double longitude;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /*
    {
"idx": 1,
"name": "맛있는새우트럭",
"phone_num": null,
"gps_longitude": 126.980444,
"gps_latitude": 37.494529,
"gps_altitude": 30.94,
"gps_address": "한국 서울특별시 동작구 동작동 63-22",
"todays_sum": null,
"start_yn": 0,
"start_time": "NaN-NaN-NaN NaN:NaN:NaN",
"follow_count": 0,
"photo_id": null,
"main_position": null,
"category_id": null,
"category_small": null,
"takeout_yn": null,
"cansit_yn": null,
"card_yn": null,
"reserve_yn": null,
"group_order_yn": null,
"always_open_yn": null,
"reg_date": "1970-01-01 09:00:00",
"open_date": "1970-01-01 09:00:00"
}
     */
}

package kr.co.aroundthetruck.admin.common;

/**
 * Created by sehonoh on 14. 12. 3..
 */
public class URL {
    public static final String PROTOCOL = "http";

    public static final String SERVER = "165.194.35.161:3000";

    public static final String WEATHER_SERVER = "apis.skplanetx.com/weather";
    public static final String WEATHER_SERVER_API = PROTOCOL + "://" + WEATHER_SERVER;

    public static final String getApi(String url) {
        return PROTOCOL + "://" + SERVER + url;
    }

    public static final String getWeatherServerUrl() {
        return PROTOCOL + ":// " + WEATHER_SERVER;
    }

    public static final String getWeatherServerApi(String url) {
        return PROTOCOL + "://" + WEATHER_SERVER + url;
    }
}

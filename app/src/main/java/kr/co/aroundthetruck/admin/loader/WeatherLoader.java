package kr.co.aroundthetruck.admin.loader;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import kr.co.aroundthetruck.admin.callback.WeatherLoadCallback;
import kr.co.aroundthetruck.admin.common.URL;

/**
 * Created by sehonoh on 14. 12. 3..
 */
public class WeatherLoader {
    private static final String TAG = WeatherLoader.class.getSimpleName();

    private static WeatherLoader loader;
    private AsyncHttpClient client;

    public static WeatherLoader getLoader() {
        if (null == loader) {
            synchronized (WeatherLoader.class) {
                loader = new WeatherLoader();
            }
        }

        return loader;
    }

    public void loadWeatherHouly(int version, double lat, double lon, final WeatherLoadCallback callback) {
        final String endPoint = URL.getWeatherServerApi("/current/hourly?version=" + version + "&lat=" + lat + "&lon=" + lon);
        client = new AsyncHttpClient();

        client.addHeader("Accept", "application/json");
        client.addHeader("appKey", "07f8befa-8de2-3b00-9c3c-fdfd051bec30");

        client.get(endPoint, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                callback.onWeatherLoadSuccess(bytes);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e(TAG, "Error : " + throwable.getLocalizedMessage());
                Log.i(TAG, "URL : " + endPoint);
            }
        });
    }
    /*
        http://apis.skplanetx.com/weather/current/hourly?version=1&lat=37.5714000000&lon=126.9658000000
     */
}

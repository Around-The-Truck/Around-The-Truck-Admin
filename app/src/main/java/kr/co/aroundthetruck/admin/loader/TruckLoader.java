package kr.co.aroundthetruck.admin.loader;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import kr.co.aroundthetruck.admin.callback.TruckListLoadCallback;
import kr.co.aroundthetruck.admin.common.URL;

/**
 * Created by sehonoh on 2015. 1. 4..
 */
public class TruckLoader {
    private static final String TAG = TruckLoader.class.getSimpleName();

    private static TruckLoader loader;
    private AsyncHttpClient client;

    public static TruckLoader getLoader() {
        if (null == loader) {
            synchronized (TruckLoader.class) {
                loader = new TruckLoader();
            }
        }

        return loader;
    }

    public void getTruckListOnMap(double latitude, double longitude, final TruckListLoadCallback callback) {
        final String url = URL.getApi("/getTruckList?latitude=" + latitude + "&longitude=" + longitude);

        Log.i(TAG, "URL : " + url);

        client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] bytes) {
                callback.onTruckListLoadSuccess(statusCode, bytes);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] bytes, Throwable throwable) {
                callback.onTruckListLoadFail(statusCode, bytes, throwable);
            }
        });
    }

    public void cancelRequest() {
        if (null != client) {
            client.cancelAllRequests(true);
        }
    }
}

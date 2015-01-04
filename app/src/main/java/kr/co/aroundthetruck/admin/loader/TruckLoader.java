package kr.co.aroundthetruck.admin.loader;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by sehonoh on 2015. 1. 4..
 */
public class TruckLoader {

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

}

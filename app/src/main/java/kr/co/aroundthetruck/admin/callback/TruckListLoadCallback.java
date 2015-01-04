package kr.co.aroundthetruck.admin.callback;

/**
 * Created by sehonoh on 2015. 1. 4..
 */
public interface TruckListLoadCallback {

    public void onTruckListLoadSuccess(int statusCode, byte[] bytes);

    public void onTruckListLoadFail(int statusCode, byte[] bytes, Throwable throwable);

}

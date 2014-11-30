package kr.co.aroundthetruck.admin;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by sehonoh on 14. 11. 30..
 */
public class AroundTheTruckApplication extends Application {
    private static final String TAG = AroundTheTruckApplication.class.getSimpleName();
    public static Typeface nanumGothicULT, nanumGothicLight, nanumGothic, nanumGothicBold;

    @Override
    public void onCreate() {
        super.onCreate();

        nanumGothicULT = Typeface.createFromAsset(getAssets(), "NanumBarunGothicUltraLight.otf");
        nanumGothicLight = Typeface.createFromAsset(getAssets(), "NanumBarunGothicLight.otf");
        nanumGothic = Typeface.createFromAsset(getAssets(), "NanumBarunGothic.otf");
        nanumGothicBold = Typeface.createFromAsset(getAssets(), "NanumBarunGothicBold.otf");
    }
}
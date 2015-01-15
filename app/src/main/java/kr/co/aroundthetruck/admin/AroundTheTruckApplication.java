package kr.co.aroundthetruck.admin;

import android.app.Application;
import android.graphics.Typeface;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by sehonoh on 14. 11. 30..
 */
public class AroundTheTruckApplication extends Application {
    private static final String TAG = AroundTheTruckApplication.class.getSimpleName();
    public static Typeface nanumGothicULT, nanumGothicLight, nanumGothic, nanumGothicBold;

    @Override
    public void onCreate() {
        super.onCreate();

        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 3);
        MemoryCache memoryCache = new LruMemoryCache(memoryCacheSize);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(memoryCache)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheSize(memoryCacheSize)
                .diskCache(new UnlimitedDiscCache(getCacheDir()))
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .denyCacheImageMultipleSizesInMemory()
                .defaultDisplayImageOptions(defaultOptions)
                .writeDebugLogs() // Not necessary in common
                .build();

        ImageLoader.getInstance().init(config);

//        nanumGothicULT = Typeface.createFromAsset(getAssets(), "NanumBarunGothicUltraLight.otf");
        nanumGothicLight = Typeface.createFromAsset(getAssets(), "NanumBarunGothicLight.otf");
        nanumGothic = Typeface.createFromAsset(getAssets(), "NanumBarunGothic.otf");
        nanumGothicBold = Typeface.createFromAsset(getAssets(), "NanumBarunGothicBold.otf");
    }
}
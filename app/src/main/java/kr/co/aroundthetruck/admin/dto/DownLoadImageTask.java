package kr.co.aroundthetruck.admin.dto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 윤석 on 2015-01-11.
 */
public class DownLoadImageTask extends AsyncTask<ImageView, Void, Bitmap> {

    ImageView imageView = null;

    @Override
    protected Bitmap doInBackground(ImageView... imageViews){
        this.imageView = imageViews[0];
        return download_image((String)imageView.getTag());
    }

    @Override
    protected void onPostExecute(Bitmap result){
        // 전달받은 imageView에 이미지를 셋팅한다.
        imageView.setImageBitmap(result);
    }

    private Bitmap download_image(String url){
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);

            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        }catch(IOException e){
            Log.d("YoonTag", "IOException : " + e);
        }catch(Exception e){
            Log.d("YoonTag", "urlError");
        }

        return bm;
    }
}

package kr.co.aroundthetruck.admin.dto;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 윤석 on 2015-01-11.
 */
public class YSNetwork {

    public AdminInformationData getTruckInformation(String articleIdx){
        Log.d("YoonTag", "서버 통신 시작");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        Log.d("YoonTag", "서버 통신");

        try {
            param.put("articleIdx", articleIdx);

        } catch (Exception e){
            e.printStackTrace();
            Log.d("YoonTag", "param Exception" + e);
        }

        AdminInformationData data = new AdminInformationData();

        try {
            client.post("http://165.194.35.161:3000/getReplyList", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("YoonTag", bytes.toString());
                    Log.d("YoonTag", new String(bytes));

                    try {
                        JSONObject jsonObject = new JSONObject(new String(bytes));
                        JSONArray arr = new JSONArray(new String(jsonObject.getString("result")));
                        for (int j=0; j<arr.length(); j++) {
                            ArticleReply adata = new ArticleReply(arr.getJSONObject(j).getString("idx"),
                                    arr.getJSONObject(j).getString("contents"), arr.getJSONObject(j).getString("writer"),
                                    arr.getJSONObject(j).getString("writer_type"), arr.getJSONObject(j).getString("article_idx"),
                                    arr.getJSONObject(j).getString("reg_date"));
//                            articleList.add(adata);
                        }
                    } catch (Exception e) {
                        Log.d("YoonTag", "Json Error : " + e);
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.d("YoonTag", new String(bytes));
                    Log.d("YoonTag", "에러러러러");
                }
            });
        }
        catch (Exception e){
//            Toast.makeText(MainActivity.this, "서버 접속 에러 ", Toast.LENGTH_SHORT).show();
            Log.d("YoonTag", "서버 접속 에러");
        }

        return data;
    }

    public List<ArticleReply> replyRequest(String articleIdx){
        Log.d("YoonTag", "서버 통신 시작");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        Log.d("YoonTag", "서버 통신");

        try {
            param.put("articleIdx", articleIdx);

        } catch (Exception e){
            e.printStackTrace();
            Log.d("YoonTag", "param Exception" + e);
        }
        final List<ArticleReply> articleList = new ArrayList<>();

        try {
            client.post("http://165.194.35.161:3000/getReplyList", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("YoonTag", bytes.toString());
                    Log.d("YoonTag", new String(bytes));

                    try {
                        JSONObject jsonObject = new JSONObject(new String(bytes));
                        JSONArray arr = new JSONArray(new String(jsonObject.getString("result")));
                        for (int j=0; j<arr.length(); j++) {
                            ArticleReply adata = new ArticleReply(arr.getJSONObject(j).getString("idx"),
                                    arr.getJSONObject(j).getString("contents"), arr.getJSONObject(j).getString("writer"),
                                    arr.getJSONObject(j).getString("writer_type"), arr.getJSONObject(j).getString("article_idx"),
                                    arr.getJSONObject(j).getString("reg_date"));
                            articleList.add(adata);
                        }
                    } catch (Exception e) {
                        Log.d("YoonTag", "Json Error : " + e);
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.d("YoonTag", new String(bytes));
                    Log.d("YoonTag", "에러러러러");
                }
            });
        }
        catch (Exception e){
//            Toast.makeText(MainActivity.this, "서버 접속 에러 ", Toast.LENGTH_SHORT).show();
            Log.d("YoonTag", "서버 접속 에러");
        }

        return articleList;
    }


    public void addReply(String articleIdx, String writer, String writerType, String contents){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        Log.d("YoonTag", "서버 통신");

        try {
            param.put("articleIdx", articleIdx);
            param.put("writer", writer);
            param.put("writerType", writerType);
            param.put("contents", contents);

        } catch (Exception e){
            e.printStackTrace();
            Log.d("YoonTag", "param Exception" + e);
        }

        try {
            client.post("http://165.194.35.161:3000/addReply", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("YoonTag", bytes.toString());
                    Log.d("YoonTag", new String(bytes));
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.d("YoonTag", new String(bytes));
                    Log.d("YoonTag", "Error ... OTL");
                }
            });
        }
        catch (Exception e){
            Log.d("YoonTag", "서버 접속 에러");
        }
    }

    public void addCharge(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        Log.d("YoonTag", "서버 통신");

        try {
//            param.put("articleIdx", articleIdx);

        } catch (Exception e){
            e.printStackTrace();
            Log.d("YoonTag", "param Exception" + e);
        }
        final List<ArticleReply> articleList = new ArrayList<>();

        try {
            client.post("http://165.194.35.161:3000/getReplyList", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("YoonTag", bytes.toString());
                    Log.d("YoonTag", new String(bytes));

                    try {
                        JSONObject jsonObject = new JSONObject(new String(bytes));
                        JSONArray arr = new JSONArray(new String(jsonObject.getString("result")));
                        for (int j=0; j<arr.length(); j++) {
                            ArticleReply adata = new ArticleReply(arr.getJSONObject(j).getString("idx"),
                                    arr.getJSONObject(j).getString("contents"), arr.getJSONObject(j).getString("writer"),
                                    arr.getJSONObject(j).getString("writer_type"), arr.getJSONObject(j).getString("article_idx"),
                                    arr.getJSONObject(j).getString("reg_date"));
                            articleList.add(adata);
                        }
                    } catch (Exception e) {
                        Log.d("YoonTag", "Json Error : " + e);
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.d("YoonTag", new String(bytes));
                    Log.d("YoonTag", "에러러러러");
                }
            });
        }
        catch (Exception e){
//            Toast.makeText(MainActivity.this, "서버 접속 에러 ", Toast.LENGTH_SHORT).show();
            Log.d("YoonTag", "서버 접속 에러");
        }
    }

//    idx : 메뉴 idx (무쓸모일듯)
//    name : 메뉴 이름
//    price : 가격
//    truck_idx : 트럭 idx
//    photo_filename : 그림파일명
//    description : 메뉴 설명
//    ingredients : 재료..?

    public List<FoodMenuData> getFoodmenuList(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        Log.d("YoonTag", "서버 통신");

        try {
            param.put("truckIdx", "1");

        } catch (Exception e){
            e.printStackTrace();
            Log.d("YoonTag", "param Exception" + e);
        }
        final List<FoodMenuData> menuList = new ArrayList<>();

        try {
            client.post("http://165.194.35.161:3000/getMenuList", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("YoonTag", bytes.toString());
                    Log.d("YoonTag", new String(bytes));

                    try {
                        JSONObject jsonObject = new JSONObject(new String(bytes));
                        JSONArray arr = new JSONArray(new String(jsonObject.getString("result")));
                        for (int j=0; j<arr.length(); j++) {

                            FoodMenuData adata = new FoodMenuData(arr.getJSONObject(j).getString("name"),
                                    arr.getJSONObject(j).getInt("price"), arr.getJSONObject(j).getString("photo_filename"),
                                    arr.getJSONObject(j).getString("description"));
                            menuList.add(adata);
                        }


                    } catch (Exception e) {
                        Log.d("YoonTag", "Json Error : " + e);
                        e.printStackTrace();


                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.d("YoonTag", new String(bytes));
                    Log.d("YoonTag", "에러러러러");

                }
            });
        }
        catch (Exception e){
//            Toast.makeText(MainActivity.this, "서버 접속 에러 ", Toast.LENGTH_SHORT).show();
            Log.d("YoonTag", "서버 접속 에러");
        }

        return menuList;
    }


}

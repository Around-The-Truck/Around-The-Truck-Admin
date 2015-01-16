package kr.co.aroundthetruck.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.YSUtility;
import kr.co.aroundthetruck.admin.ui.ATTActivity;
import kr.co.aroundthetruck.admin.util.RoundedTransformation;


public class SignUpActivity extends ATTActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();

    private EditText editVerifyCode;
    private Button btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setLayout();
        initialize();
    }

    @Override
    public void setLayout() {
        editVerifyCode = (EditText) findViewById(R.id.activity_main_edit_verify_code);
        btnApply = (Button) findViewById(R.id.activity_main_btn_apply);
    }

    @Override
    public void initialize() {
//        btnApply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AsyncHttpClient client = new AsyncHttpClient();
//                RequestParams param = new RequestParams();
//                param.put("num", btnApply.getText());
//                try{
//                    client.get("http://165.194.35.161:3000/truckNumCheck", param, new AsyncHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                            Log.d("YoonTag", new String(bytes));
//                            try{
////                        org.json.JSONArray arr = new org.json.JSONArray(new String(bytes));
//                                JSONObject jsonObject = new JSONObject(new String(bytes));
//                                String code = new String(jsonObject.getString("code"));
//
//                                if (code.equals("110")){
//                                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
//                                    finish();
//                                }
//
//                            }catch(JSONException e){
//
//                            }catch(Exception e){
//
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                            Log.d("YoonTag", "서버 에러...ㅠㅠ");
//                        }
//                    });
//                } catch(Exception e){
//
//                }

//                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
//                finish();
//
//            }
//        });
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        finish();
    }
}

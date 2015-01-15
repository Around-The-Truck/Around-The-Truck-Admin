package kr.co.aroundthetruck.admin.activity.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.internal.mc;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Date;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.YSUtility;
import kr.co.aroundthetruck.admin.dto.AdminInformationData;
import kr.co.aroundthetruck.admin.dto.FoodMenuData;
import kr.co.aroundthetruck.admin.ui.ATTActivity;
import kr.co.aroundthetruck.admin.util.RoundedTransformation;

public class RegisterAdminActivity extends ATTActivity {

    private ImageButton finishButton;

    private Spinner spinnerCat;
    private Spinner spinnerSub;

    private String[] categoryItems = {"한식 ", "중식", "일식", "양식", "한식 ", "중식", "일식", "양식"};
    private String[] subCategoryItems = {"한식_서브", "중식_서브", "일식_서브", "양식_서브", "한식_서브", "중식_서브", "일식_서브", "양식_서브"};

    private ImageView imageView;
    private final int REQUEST_IMAGE = 002;

    private EditText brandNameEditText;

    private Uri selectPhotoUri;

    private ImageButton[] optionImageButton = new ImageButton[6];
    private int[] optionClicked = new int[6];

    private DatePicker datePicker;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);
        mContext = this;

        initialize();
        setLayout();
    }

    @Override
    public void setLayout() {

    }

    @Override
    public void initialize() {

        optionImageButton[0] = (ImageButton) findViewById(R.id.activity_register_admin_option_imagebutton1);
        optionImageButton[1] = (ImageButton) findViewById(R.id.activity_register_admin_option_imagebutton2);
        optionImageButton[2] = (ImageButton) findViewById(R.id.activity_register_admin_option_imagebutton3);
        optionImageButton[3] = (ImageButton) findViewById(R.id.activity_register_admin_option_imagebutton4);
        optionImageButton[4] = (ImageButton) findViewById(R.id.activity_register_admin_option_imagebutton5);
        optionImageButton[5] = (ImageButton) findViewById(R.id.activity_register_admin_option_imagebutton6);

        for (int i = 0; i < optionClicked.length; i++) {
            optionClicked[i] = 0;
        }

        optionImageButton[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionClicked[0] == 0) {
                    Picasso.with(RegisterAdminActivity.this)
                            .load(R.drawable.register_card_r)
                            .skipMemoryCache()
                            .skipMemoryCache().fit()
                            .into(optionImageButton[0]);
                    optionClicked[0] = 1;
                } else {
                    Picasso.with(RegisterAdminActivity.this)
                            .load(R.drawable.register_card_g)
                            .skipMemoryCache().fit()
                            .into(optionImageButton[0]);
                    optionClicked[0] = 0;
                }

            }
        });

        optionImageButton[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionClicked[1] == 0) {
                    Picasso.with(RegisterAdminActivity.this)
                            .load(R.drawable.register_seat_r)
                            .skipMemoryCache().fit()
                            .into(optionImageButton[1]);
                    optionClicked[1] = 1;
                } else {
                    Picasso.with(RegisterAdminActivity.this)
                            .load(R.drawable.register_seat_g)
                            .skipMemoryCache().fit()
                            .into(optionImageButton[1]);
                    optionClicked[1] = 0;
                }
            }
        });

        optionImageButton[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionClicked[2] == 0) {
                    Picasso.with(RegisterAdminActivity.this)
                            .load(R.drawable.register_open_r)
                            .skipMemoryCache().fit()
                            .into(optionImageButton[2]);
                    optionClicked[2] = 1;
                } else {
                    Picasso.with(RegisterAdminActivity.this)
                            .load(R.drawable.register_open_g)
                            .skipMemoryCache().fit()
                            .into(optionImageButton[2]);
                    optionClicked[2] = 0;
                }
            }
        });

        optionImageButton[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionClicked[3] == 0) {
                    Picasso.with(RegisterAdminActivity.this)
                            .load(R.drawable.register_takeout_r)
                            .skipMemoryCache().fit()
                            .into(optionImageButton[3]);
                    optionClicked[3] = 1;
                } else {
                    Picasso.with(RegisterAdminActivity.this)
                            .load(R.drawable.register_takeout_g)
                            .skipMemoryCache().fit()
                            .into(optionImageButton[3]);
                    optionClicked[3] = 0;
                }
            }
        });

        optionImageButton[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionClicked[4] == 0) {
                    Picasso.with(RegisterAdminActivity.this)
                            .load(R.drawable.register_group_r)
                            .skipMemoryCache().fit()
                            .into(optionImageButton[4]);
                    optionClicked[4] = 1;
                } else {
                    Picasso.with(RegisterAdminActivity.this)
                            .load(R.drawable.register_group_g)
                            .skipMemoryCache().fit()
                            .into(optionImageButton[4]);
                    optionClicked[4] = 0;
                }
            }
        });

        optionImageButton[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionClicked[5] == 0) {
                    Picasso.with(RegisterAdminActivity.this)
                            .load(R.drawable.register_reser_r)
                            .skipMemoryCache().fit()
                            .into(optionImageButton[5]);
                    optionClicked[5] = 1;
                } else {
                    Picasso.with(RegisterAdminActivity.this)
                            .load(R.drawable.register_reser_g)
                            .skipMemoryCache().fit()
                            .into(optionImageButton[5]);
                    optionClicked[5] = 0;
                }
            }
        });

        finishButton = (ImageButton) findViewById(R.id.activity_register_admin_button_finsih);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishButtonIsClicked();
            }
        });

        /*
        Spinner
         */
        spinnerCat = (Spinner) findViewById(R.id.activity_register_admin_category_spinner);
        spinnerSub = (Spinner) findViewById(R.id.activity_register_admin_category_spinner2);

        // spinnerSub
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, categoryItems);
        adapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line);

        spinnerCat.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, subCategoryItems);
        adapter2.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line);
        spinnerSub.setAdapter(adapter2);

        // ImageView
        imageView = (ImageView) findViewById(R.id.activity_register_admin_imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                RegisterAdminActivity.this.startActivityForResult(intent, REQUEST_IMAGE);

            }
        });

        //
        brandNameEditText = (EditText) findViewById(R.id.activity_register_admin_edittext_name);


        //
        datePicker = (DatePicker) findViewById(R.id.activity_register_admin_datePicker);
        datePicker.setMaxDate(new Date().getTime());
//        EditText edittext = (EditText) datePicker.findViewById(Resources.getSystem().getIdentifier("datepicker_input", "id",  "android"));
//
//        edittext.setTextColor(Color.BLUE);

    }

    private void finishButtonIsClicked() {

        EditText editText1 = (EditText) findViewById(R.id.activity_register_admin_edittext_phone_number);


        String date = datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth();
        AdminInformationData adminData = new AdminInformationData(brandNameEditText.getText().toString(), editText1.getText().toString(), date, (String) spinnerCat.getSelectedItem(), (String) spinnerSub.getSelectedItem());
        adminData.optionClicked = optionClicked;

        adminData.setSelectPhotoUri(selectPhotoUri.toString());
        request(adminData);

        Intent intent = new Intent(getBaseContext(), RegisterFoodMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("adminData", adminData);

        startActivityForResult(intent, 1001);

    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void request(AdminInformationData data) {

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams param = new RequestParams();
        // 이렇게 인자를 넘겨야 될 때
        // 이름을 정해주고 추가하면 됨!
        //param.put("id", "rlaace423");
        //param.put("file", new File(fullPath));

        try {
            param.put("truckName", data.getBrandName());
            param.put("phone", data.getPhoneNumber());
            param.put("open_date", data.getOpenData());
            param.put("category_big", 1);
            param.put("category_small", 1);

            param.put("takeout_yn", data.optionClicked[3]);
            param.put("cansit_yn", data.optionClicked[1]);
            param.put("card_yn", data.optionClicked[0]);
            param.put("reserve_yn", data.optionClicked[5]);
            param.put("group_order_yn", data.optionClicked[4]);
            param.put("always_open_yn", data.optionClicked[2]);
            param.put("idx", "6");

            Log.d("YoonTag", data.getSelectPhotoUri().toString());
            Uri selectPhotoUri = Uri.parse((String) data.getSelectPhotoUri());
            param.put("file", new File(fullPath));

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("YoonTag", "Errorrorror");
        }


        client.post("http://165.194.35.161:3000/truckJoin", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("YoonTag", bytes.toString());
                Log.d("YoonTag", new String(bytes));
//                try {
//                    org.json.JSONArray arr = new org.json.JSONArray(new String(bytes));
//                    for (int i=0; i<arr.length(); i++) {
//                        MainActivity.items.add(new CustomerItem(arr.getJSONObject(i).getString("image1_id"),arr.getJSONObject(i).getString("name"),arr.getJSONObject(i).getString("category"),arr.getJSONObject(i).getString("price")));
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("YoonTag", new String(bytes));
                Log.d("YoonTag", "에러러러러");
            }
        });
    }

    private String fullPath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            Log.d("YoonTag", "====== OnActivityResult is start ========= \n");

            Uri selPhotoUri = data.getData();
            try {
                selectPhotoUri = selPhotoUri;

                Picasso.with(RegisterAdminActivity.this)
                        .load(selectPhotoUri)
                        .skipMemoryCache().fit()
                        .transform(new RoundedTransformation(211))
                        .into(imageView);


                // sangho
                fullPath = getRealPathFromURI(this, selPhotoUri);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    // 서버 송수신
//    private class UploadTask extends AsyncTask<void, void, String> {
//
//        ProgressDialog mDialog;
//
//        @Override
//        protected void onPreExecute(){
//
//            super.onPreExecute();;
//            mDialog = new ProgressDialog(mContext);
//            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            mDialog.setMessage("사진 전송중");
//            mDialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(String result){
//
//            if(mDialog != null){
//                mDialog.cancel();
//                mDialog.dismiss();
//                mDialog = null;
//
//                Log.d("YoonTag", "전송 완료");
////                Intent intent = new Intent(getBaseContext(), Seer)
//            }
//        }
//
//        @Override
//        protected String donInBackground(Void... arg0){
//
//            try{
//                HttpClient httpClient = new DefaultHttpClient();
//
//                String url = "서버 URL";
//                HttpPost post = new HttpPost(url);
//
//                File img = new File("주소 주소주소");
//                FileBody body = new FileBody(img);
//
//                MultipartEntity multipart = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//
//                multipart.addPart("file", body);
//
//                post.setEntity(multipart);
//                HttpResponse response = httpClient.execute(post);
//                HttpEntity resEntity = response.getEntity();
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//            return "";
//        }
//    }
}

package kr.co.aroundthetruck.admin.activity.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.internal.mc;

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
import java.nio.charset.Charset;
import java.util.Date;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.YSUtility;
import kr.co.aroundthetruck.admin.dto.AdminInformationData;
import kr.co.aroundthetruck.admin.dto.FoodMenuData;
import kr.co.aroundthetruck.admin.ui.ATTActivity;

public class RegisterAdminActivity extends ATTActivity {

    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;

    private Button nextButton1;
    private Button nextButton2;
    private Button finishButton;

    private Spinner spinnerCat;
    private Spinner spinnerSub;

    private String[] categoryItems = {"한식 ", "중식", "일식", "양식", "한식 ", "중식", "일식", "양식"};
    private String[] subCategoryItems = {"한식_서브", "중식_서브", "일식_서브", "양식_서브", "한식_서브", "중식_서브", "일식_서브", "양식_서브"};

    private ImageView imageView;
    private final int REQUEST_IMAGE = 002;

    private GridView gridview;

    private EditText brandNameEditText;

    private Uri selectPhotoUri;

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
        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.INVISIBLE);
        layout3.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initialize() {
        layout1 = (LinearLayout) findViewById(R.id.activity_register_admin_layout1);
        layout2 = (LinearLayout) findViewById(R.id.activity_register_admin_layout2);
        layout3 = (LinearLayout) findViewById(R.id.activity_register_admin_layout3);

        nextButton1 = (Button) findViewById(R.id.activity_register_admin_button_next1);
        nextButton2 = (Button) findViewById(R.id.activity_register_admin_button_next2);
        finishButton = (Button) findViewById(R.id.activity_register_admin_button_fisish);

        nextButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout2.setVisibility(View.VISIBLE);
                nextButton1.setVisibility(View.INVISIBLE);
            }
        });

        nextButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout3.setVisibility(View.VISIBLE);
                nextButton2.setVisibility(View.INVISIBLE);
            }
        });

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

        gridview = (GridView) findViewById(R.id.activity_register_admin_gridview);
        gridview.setAdapter(new ImageAdapter(this));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(RegisterAdminActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }

        });

        //
        brandNameEditText = (EditText) findViewById(R.id.activity_register_admin_edittext_name);


    }

    private void finishButtonIsClicked() {

        EditText editText1 = (EditText) findViewById(R.id.activity_register_admin_edittext_phone_number);
        DatePicker datePicker = (DatePicker) findViewById(R.id.activity_register_admin_datePicker);

        String date = datePicker.getYear() + "/" + datePicker.getMonth() + "/" + datePicker.getDayOfMonth();
        AdminInformationData adminData = new AdminInformationData(brandNameEditText.getText().toString(), editText1.getText().toString(), date, (String) spinnerCat.getSelectedItem(), (String) spinnerSub.getSelectedItem());

        adminData.setSelectPhotoUri(selectPhotoUri.toString());
        request(adminData);

        Intent intent = new Intent(getBaseContext(), RegisterFoodMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("adminData", adminData);

        startActivityForResult(intent, 1001);

    }

    private void request(AdminInformationData data) {
//
//        final String urlStr = "http://165.194.35.161:3000/truckJoin";
//        Log.d("YoonTag", "server : " + urlStr);
////        StringBuilder output = new StringBuilder();
//        try {
//
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create().setCharset(Charset.forName("UTF-8")).setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//
//            builder.addTextBody("truckName", data.getBrandName());
//
//            HttpClient client = AndroidHttpClient.newInstance("Android");
//            HttpPost post = new HttpPost(urlStr);
//
////            post.setEntity(builder.build());
//
//            HttpResponse httpRes = client.execute(post);
//
//            HttpEntity httpEntity = httpRes.getEntity();
//            if(httpEntity != null) {
//                Log.d("YoonTag", EntityUtils.toString(httpEntity));
//            }
//
////            HttpClient client = new DefaultHttpClient();
////            HttpPost httppost = new HttpPost(urlStr);
////
////            // 파일 Body 생성
////            File truckImg = new File(data.getSelectPhotoUri().toString());
////            FileBody bin0 = new FileBody(truckImg);
////
////
////            // MultipartEntityBuilder
////            MultipartEntityBuilder meb = MultipartEntityBuilder.create();
////
////            //Builder 설정하기
////            // 선언할때 넣는게 아니라 선언 후 메소드로 설정한다.
////            meb.setBoundary("==============");
////            meb.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
////            meb.setCharset(Charset.defaultCharset());
////
////            //문자열을 보내려면 addPart와 StringBody가 아닌 addTextBody를 사용한다.
////            meb.addTextBody("truckName", data.getBrandName());
////            meb.addTextBody("phone", data.getPhoneNumber());
////            meb.addTextBody("open_data", data.getOpenData());
////
////            meb.addTextBody("category_big", data.getCategory());
////            meb.addTextBody("category_small", data.getSubCategory());
////
//////            meb.addPart("file", bin0);
////
////            Log.d("YoonTag", "==================0");
////            //HttpEntity를 빌드하고 HttpPost 객체에 삽입한다.
////            HttpEntity entity = meb.build();
////            httppost.setEntity(entity);
////            Log.d("YoonTag", "==================1");
//////            Log.d("YoonTag", entity.toString());
////            HttpResponse response = client.execute(httppost);
////            Log.d("YoonTag", "server : " + urlStr);
////            HttpEntity resEntity = response.getEntity();
////
////            Log.d("YoonTag", resEntity.toString());
////
//////
////            InputStream instream =response.getEntity().getContent();
////            BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
//
//
//        } catch(ClientProtocolException e){
//            e.printStackTrace();
//        } catch (IOException e ){
//            e.printStackTrace();
//        } catch(Exception e){
//            Log.d("YoonTag", "======== Exception 발생 ");
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            Log.d("YoonTag", "====== OnActivityResult is start ========= \n");

            Uri selPhotoUri = data.getData();
            try {
                Bitmap selPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), selPhotoUri);
                Bitmap roundPhoto = YSUtility.GetBitmapClippedCircle(selPhoto);
                imageView.setImageBitmap(roundPhoto);
                selectPhotoUri = selPhotoUri;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
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

    class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private Integer[] mThumbIds = {
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher
        };

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

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

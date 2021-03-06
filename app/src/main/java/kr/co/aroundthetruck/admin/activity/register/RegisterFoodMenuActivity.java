package kr.co.aroundthetruck.admin.activity.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.YSUtility;
import kr.co.aroundthetruck.admin.dto.AdminInformationData;
import kr.co.aroundthetruck.admin.dto.FoodMenuData;
import kr.co.aroundthetruck.admin.dto.RegisterFoodMenuData;


public class RegisterFoodMenuActivity extends Activity {

    private ListView mlistView;
    private FoodMenuRegisterAdapter adapter;

    private ImageView truckImageView;
    private TextView brandNameTextView;

    private int targetPosition = 0;
    private final int REQUEST_IMAGE = 002;

    private ImageButton finishImageButton;

    private String truckIdx = "5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_food_menu);

        initialize();
        setLayout();
    }

    public void initialize(){
        truckImageView = (ImageView) findViewById(R.id.activity_register_food_menu_imageView2);
        mlistView = (ListView) findViewById(R.id.activity_register_food_menu_listview);
        adapter = new FoodMenuRegisterAdapter(getLayoutInflater());
        mlistView.setAdapter(adapter);
        adapter.setImageSelectListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetPosition = (Integer) v.getTag();
                Log.v("YoonTag", targetPosition +"번 이미지뷰 선택됨");

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE);

//                Toast.makeText(this, "이미지 띄움", Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivity(intent);
//                mContext.startActivityForResult(intent, REQUEST_IMAGE);
            }
        });

        brandNameTextView = (TextView) findViewById(R.id.activity_register_food_menu_brandname_textview);

        finishImageButton = (ImageButton) findViewById(R.id.activity_register_food_menu_finish_imagebutton);
        finishImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishButtonClicked();
            }
        });

    }

    public void setLayout(){

        Intent intent = getIntent();

        AdminInformationData adminData = (AdminInformationData) intent.getSerializableExtra("adminData");
        brandNameTextView.setText(adminData.getBrandName());
        TextView textView = (TextView) findViewById(R.id.activity_register_admin_textView8);
        textView.setText(adminData.getPhoneNumber());
        textView = (TextView) findViewById(R.id.activity_register_admin_textView9);
        textView.setText(adminData.getOpenData());
        textView = (TextView) findViewById(R.id.activity_register_admin_textView10);
        textView.setText(adminData.getcategoryAll());

        try {
            Uri selectPhotoUri = Uri.parse((String) adminData.getSelectPhotoUri());
            Bitmap selPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), selectPhotoUri);
            Bitmap roundPhoto = YSUtility.GetBitmapClippedCircle(selPhoto);
            truckImageView.setImageBitmap(roundPhoto);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    private void finishButtonClicked(){
        request();

    }

    private void request(){
//        [{"photoFieldName":"file0",
//                "name":"맛있는 군인들",
//                "price":"1000",
//                "description":"건강에 좋습니다.",
//                "ingredients":"군인1, 군인2, 군인3"},
//        {"photoFieldName":"file1", "name":"특급전사", "price":"1000", "description":"화끈한 특급전사", "ingredients":"진이형"}]

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams param = new RequestParams();
        // 이렇게 인자를 넘겨야 될 때
        // 이름을 정해주고 추가하면 됨!
        //param.put("id", "rlaace423");
        //param.put("file", new File(fullPath));

        try {
            List<RegisterFoodMenuData> foodmenuList = adapter.getFoodMenuListServer();
            String json = new Gson().toJson(foodmenuList);
            Log.d("YoonTag", json);

            param.put("data", json);
//            param.put("truckName", data.getBrandName());
//            param.put("phone", data.getPhoneNumber());
//            param.put("open_date", data.getOpenData());
//            param.put("category_big", 1);
//            param.put("category_small", 1);
//
//            param.put("takeout_yn", data.optionClicked[3]);
//            param.put("cansit_yn", data.optionClicked[1]);
//            param.put("card_yn", data.optionClicked[0]);
//            param.put("reserve_yn", data.optionClicked[5]);
//            param.put("group_order_yn", data.optionClicked[4]);
//            param.put("always_open_yn", data.optionClicked[2]);
//            param.put("idx", "6");
//
//            Log.d("YoonTag", data.getSelectPhotoUri().toString());
//            Uri selectPhotoUri = Uri.parse((String) data.getSelectPhotoUri());
//            param.put("file", new File(fullPath));

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("YoonTag", "Errorrorror");
        }


        client.post("http://165.194.35.161:3000/addMenuList", param, new AsyncHttpResponseHandler() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Log.d("YoonTag", "====== OnActivityResult is start ========= \n");

            Uri selPhotoUri = data.getData();
            try {
                Bitmap selPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), selPhotoUri);
                Bitmap roundPhoto = YSUtility.GetBitmapClippedCircle(selPhoto);
                adapter.addImage(targetPosition, roundPhoto);
//                imageView.setImageBitmap(roundPhoto);
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

    class FoodMenuRegisterAdapter extends BaseAdapter {
        private Context mContext;

        private LayoutInflater inflater;
        private List<FoodMenuData> mItems = new ArrayList<>();
        private List<Bitmap> mImageItems = new ArrayList<>();

        private int totalPrice = 0;

        private View.OnClickListener imageSelectListener;

        public FoodMenuRegisterAdapter(Context context){
            mContext = context;
            mItems.add(new FoodMenuData());

        }

        public FoodMenuRegisterAdapter(LayoutInflater inflater){
            this.inflater = inflater;
            mItems.add(new FoodMenuData());
        }

        public void setImageSelectListener(View.OnClickListener listener){
            imageSelectListener = listener;
        }

        public void addImage(int position, Bitmap image){
            mImageItems.add(position, image);
            mItems.add(new FoodMenuData());
            notifyDataSetChanged();
        }

        public List<FoodMenuData> getFoodMenuList(){
            return mItems;
        }

        public List<RegisterFoodMenuData> getFoodMenuListServer(){
            List<RegisterFoodMenuData> listItem = new ArrayList<>();
            for (int i = 0; i<mItems.size() -1; i++){
                listItem.add(new RegisterFoodMenuData("file"+i, mItems.get(i).getMenuName(), "" + mItems.get(i).getMenuPrice(), mItems.get(i).getDesciption(), ""));
            }
            return listItem;
        }

        public int getTotalPrice(){
            return totalPrice;
        }

        public int getCount(){
            return mItems.size();
        }

        public View getView(int position, View convertView, ViewGroup parent){
            ViewHolder holder;

            if (convertView == null){
                convertView = inflater.inflate(R.layout.activity_register_food_menu_listitem, null);

                holder = new ViewHolder();
                holder.menuImageView = (ImageView) convertView.findViewById(R.id.activity_register_food_menu_listitem_imageview);
                holder.menuNameTextView = (TextView) convertView.findViewById(R.id.fragment_pos_main_list_textview_menuname);
                holder.priceTextView = (TextView) convertView.findViewById(R.id.fragment_pos_main_list_textview_price);
                holder.removeButton = (Button) convertView.findViewById(R.id.fragment_pos_main_list_button_remove);

                holder.menuImageView.setOnClickListener(imageSelectListener);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.menuImageView.setTag(Integer.valueOf(position));


            try{
                holder.menuImageView.setImageBitmap(mImageItems.get(position));
            }
            catch ( Exception e){

            }

//            holder.menuNameTextView.setText("Position "+position);

            return convertView;
        }

        public Object getItem(int position){
            return null;
        }
        public long getItemId(int position){
            return 0;
        }
    }

    private static class ViewHolder{
        public ImageView menuImageView;
        public TextView menuNameTextView;
        public TextView priceTextView;
        public Button removeButton;

    }
}
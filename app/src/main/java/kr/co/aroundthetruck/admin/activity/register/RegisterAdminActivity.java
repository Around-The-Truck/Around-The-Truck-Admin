package kr.co.aroundthetruck.admin.activity.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.YSUtility;
import kr.co.aroundthetruck.admin.dto.AdminInformationData;

public class RegisterAdminActivity extends Activity {

    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;

    private Button nextButton1;
    private Button nextButton2;
    private Button finishButton;

    private Spinner spinnerCat;
    private Spinner spinnerSub;

    private String [] categoryItems = { "한식 ", "중식", "일식", "양식", "한식 ", "중식", "일식", "양식"};
    private String [] subCategoryItems = { "한식_서브", "중식_서브", "일식_서브", "양식_서브", "한식_서브", "중식_서브", "일식_서브", "양식_서브"};

    private ImageView imageView;
    private final int REQUEST_IMAGE = 002;

    private GridView gridview;

    private EditText brandNameEditText;

    private Uri selectPhotoUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        initialize();
        setLayout();
    }

    private void setLayout() {
        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.INVISIBLE);
        layout3.setVisibility(View.INVISIBLE);
    }

    private void initialize(){
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
                this, android.R.layout.simple_spinner_item, categoryItems );
        adapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line   );

        spinnerCat.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, subCategoryItems );
        adapter2.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line   );
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
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(RegisterAdminActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }

        });

        //
        brandNameEditText = (EditText) findViewById(R.id.activity_register_admin_edittext_name);


    }

    private void finishButtonIsClicked(){

        EditText editText1 = (EditText) findViewById(R.id.activity_register_admin_edittext_phone_number);
        DatePicker datePicker = (DatePicker) findViewById(R.id.activity_register_admin_datePicker);

        String date = datePicker.getYear() +"/" + datePicker.getMonth() +"/"+ datePicker.getDayOfMonth();
        AdminInformationData adminData = new AdminInformationData(brandNameEditText.getText().toString(), editText1.getText().toString(), date  , (String) spinnerCat.getSelectedItem(), (String) spinnerSub.getSelectedItem());

        adminData.setSelectPhotoUri(selectPhotoUri.toString());

        Intent intent = new Intent(getBaseContext(), RegisterFoodMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("adminData", adminData);

        startActivityForResult(intent, 1001);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register__admin, menu);
        return true;
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

    class ImageAdapter extends BaseAdapter{
        private Context mContext;
        private Integer[] mThumbIds = {
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher
        };

        public ImageAdapter(Context c){
            mContext = c;
        }
        public int getCount(){
            return mThumbIds.length;
        }
        public Object getItem(int position){
            return null;
        }
        public long getItemId(int position){
            return 0;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            ImageView imageView;
            if(convertView == null){
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            }
            else{
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

    }
}

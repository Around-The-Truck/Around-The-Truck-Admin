package kr.co.aroundthetruck.admin.fragment;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;

import kr.co.aroundthetruck.admin.Dialog.MenubarStateChangeDialog;
import kr.co.aroundthetruck.admin.Dialog.PosPaymentDialog;
import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.YSUtility;
import kr.co.aroundthetruck.admin.activity.MainActivity;
import kr.co.aroundthetruck.admin.activity.register.RegisterAdminActivity;
import kr.co.aroundthetruck.admin.common.URL;
import kr.co.aroundthetruck.admin.dto.DownLoadImageTask;
import kr.co.aroundthetruck.admin.util.RoundedTransformation;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link kr.co.aroundthetruck.admin.fragment.MenuBarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MenuBarFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public MenuBarFragment() {
        // Required empty public constructor
    }

    private int truckstate = 0;

    private Button menuSizeButton;
    private ImageButton largeSizeButton;
    private int menuBarState = 0; // 0-> 좁은거, 1-> 넓은거

    private LinearLayout smallMenuLayout;
    private LinearLayout largeMenuLayout;
    private LinearLayout containerLayout;

    private ImageButton homeImageButton;
    private ImageButton settingImageButton;
    private ImageButton exitImageButton;

    private ImageView truckImageView;

    private TextView largeBrandNameTextview;
    private TextView largeCategoryTextview;

    private ImageButton truckStateImageButton;

    private ImageButton largeNewsfeedImageButton;
    private ImageButton largeModifyMenuImageButton;
    private ImageButton largeSettingButton;
    private ImageButton largeExitImageButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root =inflater.inflate(R.layout.fragment_menu_bar, container, false);

        initialize(root);

        setLayout(inflater);

        return root;
    }

    private void initialize(View view){

        smallMenuLayout = (LinearLayout) view.findViewById(R.id.fragment_menu_bar_small_layout);
        largeMenuLayout = (LinearLayout) view.findViewById(R.id.fragment_menu_bar_large_layout);
        containerLayout = (LinearLayout) view.findViewById(R.id.fragment_menu_bar_container_layout);



        menuSizeButton = (Button) view.findViewById(R.id.fragment_menu_bar_small_button);
        menuSizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout)smallMenuLayout.getParent()).removeView(smallMenuLayout);
                containerLayout.addView(largeMenuLayout);

                LayoutTransition transition = new LayoutTransition();
                containerLayout.setLayoutTransition(transition);
            }
        });
        largeSizeButton = (ImageButton) view.findViewById(R.id.fragment_menu_bar_large_imagebutton);
        largeSizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout)largeMenuLayout.getParent()).removeView(largeMenuLayout);
                containerLayout.addView(smallMenuLayout);

                LayoutTransition transition = new LayoutTransition();
                containerLayout.setLayoutTransition(transition);
            }
        });

        truckStateImageButton = (ImageButton) view.findViewById(R.id.fragment_menu_bar_large_state_imagebutton);
        truckStateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MenubarStateChangeDialog dialog = new MenubarStateChangeDialog(getActivity(), truckstate);

                if (truckstate == 0){
//                    Log.d("YoonTag", "latitude : "+((MainActivity)getActivity()).getLocation().getLatitude() + ", longitude : "+((MainActivity)getActivity()).getLocation().getLongitude() );

                    dialog.setBackgroundImageview(getResources().getDrawable(R.drawable.dialog_open));
                } else if(truckstate == 1){
                    dialog.setBackgroundImageview(getResources().getDrawable(R.drawable.dialog_closed));
                }

                dialog.show();
                View.OnClickListener listener1 = new View.OnClickListener() {  // 변경
                    @Override
                    public void onClick(View v) {
                        changeTruckState();
                        dialog.dismiss();
                    }
                };

                dialog.setOnclickListenr1(listener1);

            }
        });


        truckImageView = (ImageView) view.findViewById(R.id.fragment_menu_bar_large_imageview);

        largeBrandNameTextview = (TextView) view.findViewById(R.id.fragment_menu_bar_large_brandname_textview);
        largeCategoryTextview = (TextView) view.findViewById(R.id.fragment_menu_bar_large_category_textview);

        largeNewsfeedImageButton = (ImageButton) view.findViewById(R.id.fragment_menu_bar_large_imagebutton_newsfeed);
        largeNewsfeedImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_container, NewsFeedFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        largeModifyMenuImageButton = (ImageButton) view.findViewById(R.id.fragment_menu_bar_large_imagebutton_menumodi);
        largeModifyMenuImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_container, ModifyFoodMenuFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        largeSettingButton = (ImageButton) view.findViewById(R.id.fragment_menu_bar_large_setting_imagebutton);
        largeExitImageButton = (ImageButton) view.findViewById(R.id.fragment_menu_bar_large_end_imagebutton);

        ((LinearLayout)largeMenuLayout.getParent()).removeView(largeMenuLayout);

        Log.v("ystag", "initalize");

        homeImageButton = (ImageButton) view.findViewById(R.id.fragment_menu_bar_home_imagebutton);
        homeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_container,  PosMainFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        settingImageButton = (ImageButton) view.findViewById(R.id.fragment_menu_bar_setting_imagebutton);
        settingImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.activity_main_container, NewsFeedFragment.newInstance())
//                        .addToBackStack(null)
//                        .commit();
//                startActivity(new Intent(getActivity(), RegisterAdminActivity.class));
                  getFragmentManager().beginTransaction()
                            .replace(R.id.activity_main_container, CalendarPickFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
            }
        });

        exitImageButton = (ImageButton) view.findViewById(R.id.fragment_menu_bar_exit_imagebutton);
        exitImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_container, ModifyFoodMenuFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });


    }

    private void setLayout(LayoutInflater inflater)
    {
        request();
    }

    private void truckClose(){

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("idx", ((MainActivity)getActivity()).truckIdx);

        try{
            client.post("http://165.194.35.161:3000/truckEnd", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("YoonTag", new String(bytes));
                    try{
//                        org.json.JSONArray arr = new org.json.JSONArray(new String(bytes));
                        JSONObject jsonObject = new JSONObject(new String(bytes));

                    }catch(JSONException e){

                    }catch(Exception e){

                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.d("YoonTag", "서버 에러...ㅠㅠ");
                }
            });
        } catch(Exception e){

        }

    }

    private double latSample[] = {37.5164693,37.5189551, 37.5097313, 37.5175011, 37.5070726, 37.5120673, 37.5121959, 37.5191053, 37.5089976, 37.5162734, 37.5148515, 37.5135527, 37.5159136, 37.5194311};
    private double lngSample[] = {     127.1136096, 127.1140785, 127.1032415, 127.11453, 127.1055246, 127.1074787, 127.1162812, 127.1073195, 127.1109453, 127.1079845, 127.1099813, 127.1158213, 127.1227586, 127.1187741};


    private void truckOpen(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        param.put("idx", ((MainActivity)getActivity()).truckIdx);
        int n = (int) Math.random() %14;
        param.put("lat", latSample[n]);
        param.put("lng", lngSample[n]);
//        param.put("lat", );

        try{
            client.post("http://165.194.35.161:3000/truckStart", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("YoonTag", new String(bytes));
                    try{
//                        org.json.JSONArray arr = new org.json.JSONArray(new String(bytes));
                        JSONObject jsonObject = new JSONObject(new String(bytes));

                    }catch(JSONException e){

                    }catch(Exception e){

                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.d("YoonTag", "서버 에러...ㅠㅠ");
                }
            });
        } catch(Exception e){

        }
    }

    private void changeTruckState(){

        if (truckstate == 0){ // 클로즈 상태
            truckOpen();
            truckStateImageButton.setImageDrawable(getResources().getDrawable(R.drawable.menubar_large_open) );
            truckstate = 1;
        }else if (truckstate == 1){ // 오픈 상태
            truckClose();
            truckStateImageButton.setImageDrawable(getResources().getDrawable(R.drawable.menubar_large_closed) );
            truckstate = 0;
        }
    }

    private void request(){
        Log.d("YoonTag", "서버 통신 시작");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        Log.d("YoonTag", "서버 통신");

        try {
            param.put("truckIdx", 5);

        } catch (Exception e){
            e.printStackTrace();
            Log.d("YoonTag", "Errorrorror");
        }

        try{
            client.get("http://165.194.35.161:3000/getTruckInfo", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("YoonTag", new String(bytes));
                    try{
//                        org.json.JSONArray arr = new org.json.JSONArray(new String(bytes));
                        JSONObject jsonObject = new JSONObject(new String(bytes));
                        Log.d("YoonTag", "~~~~~~~!!!!!~~~~~~~"+jsonObject.getString("result"));
                        JSONArray resultJsonObject = new JSONArray(new String(jsonObject.getString("result")));

                        Log.d("YoonTag", "~~~~~~~!!!!!~~~~~~~"+resultJsonObject.getJSONObject(0).getString("name"));
                        Log.d("YoonTag", "~~~~~~~!!!!!~~~~~~~"+resultJsonObject.getJSONObject(0).getString("photo_filename"));


                        Picasso.with(getActivity()).load(YSUtility.addressImageStorage + URLEncoder.encode(resultJsonObject.getJSONObject(0).getString("photo_filename"), "UTF-8")).fit().transform(new RoundedTransformation(86)).into(truckImageView);
                        largeBrandNameTextview.setText(resultJsonObject.getJSONObject(0).getString("name"));
                        largeCategoryTextview.setText(resultJsonObject.getJSONObject(0).getString("cat_name_big") + " / " + resultJsonObject.getJSONObject(0).getString("cat_name_small"));

                    }catch(JSONException e){

                    }catch(Exception e){

                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.d("YoonTag", "서버 에러...ㅠㅠ");
                }
            });
        } catch(Exception e){

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

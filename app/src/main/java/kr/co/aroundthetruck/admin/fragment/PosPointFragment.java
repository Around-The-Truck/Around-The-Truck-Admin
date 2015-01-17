package kr.co.aroundthetruck.admin.fragment;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.YSUtility;
import kr.co.aroundthetruck.admin.activity.MainActivity;
import kr.co.aroundthetruck.admin.dto.YSNetwork;
import kr.co.aroundthetruck.admin.util.RoundedTransformation;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PosPointFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PosPointFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private Button[] keyboardButton = new Button[10];
    private String phoneNumber;

    private TextView phoneNumberTextView ;

    private TextView payPriceTextView;
    private TextView chagePointTextView;

    private Button keyboardokButton;
    private Button keyboardclearButton;

    private LinearLayout pointBoxLayout;
    private TextView pointboxTextview;
    private ImageButton pointboxImageButton;

    private int payPrice;

    private String menuArr;

    public PosPointFragment() {
        // Required empty public constructor
    }

    public static PosPointFragment newInstance() {
        PosPointFragment fragment = new PosPointFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pos_point, container, false);

        Log.v("ystag", "로그 테스트중입니당 onCreateView");
        initialize(root);

        setLayout(inflater);

        return root;
    }

    private void initialize(View view){
        keyboardButton[0] = (Button) view.findViewById(R.id.fragment_pos_point_keyboard_button0);
        keyboardButton[1] = (Button) view.findViewById(R.id.fragment_pos_point_keyboard_button1);
        keyboardButton[2] = (Button) view.findViewById(R.id.fragment_pos_point_keyboard_button2);
        keyboardButton[3] = (Button) view.findViewById(R.id.fragment_pos_point_keyboard_button3);
        keyboardButton[4] = (Button) view.findViewById(R.id.fragment_pos_point_keyboard_button4);
        keyboardButton[5] = (Button) view.findViewById(R.id.fragment_pos_point_keyboard_button5);
        keyboardButton[6] = (Button) view.findViewById(R.id.fragment_pos_point_keyboard_button6);
        keyboardButton[7] = (Button) view.findViewById(R.id.fragment_pos_point_keyboard_button7);
        keyboardButton[8] = (Button) view.findViewById(R.id.fragment_pos_point_keyboard_button8);
        keyboardButton[9] = (Button) view.findViewById(R.id.fragment_pos_point_keyboard_button9);

        phoneNumber = "";
        for (int i = 0; i<keyboardButton.length; i++){
            keyboardButton[i].setTag(i);
            keyboardButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int n = (int) v.getTag();
                    makePhoneNumber(n);
                }
            });
        }

        phoneNumberTextView = (TextView) view.findViewById(R.id.fragment_pos_point_point_textview);

        keyboardokButton = (Button) view.findViewById(R.id.fragment_pos_point_keyboard_button_ok);
        keyboardokButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });

        keyboardclearButton = (Button) view.findViewById(R.id.fragment_pos_point_keyboard_button_cancel);
        keyboardclearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = "";
                phoneNumberTextView.setText(phoneNumber);
            }
        });

        String price = getArguments().getString("totalPay");
        payPrice = Integer.parseInt(price);
        payPriceTextView = (TextView) view.findViewById(R.id.fragment_pos_point_pay_cash_textview);
        payPriceTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Multicolore.otf"));
        payPriceTextView.setText(price + " 원");
        chagePointTextView = (TextView) view.findViewById(R.id.fragment_pos_point_charge_point_textview);
        chagePointTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Multicolore.otf"));
        chagePointTextView.setText("" + (payPrice / 20) +" 포인트");

        menuArr = getArguments().getString("menuArr");

        pointBoxLayout = (LinearLayout) view.findViewById(R.id.fragment_pos_point_point_box_layout);

        pointboxTextview = (TextView) view.findViewById(R.id.fragment_pos_point_mypoint_textview);
        pointboxTextview.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Multicolore.otf"));

        pointboxImageButton = (ImageButton) view.findViewById(R.id.fragment_pos_point_ok_imagebutton);
        pointboxImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YSNetwork ysNetwork = new YSNetwork();
                ysNetwork.setPayment(menuArr, ((MainActivity)getActivity()).truckIdx, phoneNumber);
                getFragmentManager().popBackStackImmediate();

            }
        });

        pointBoxLayout.setVisibility(View.INVISIBLE);
//        ((LinearLayout)smallMenuLayout.getParent()).removeView(smallMenuLayout);

    }

    private void setLayout(LayoutInflater inflater){

    }

    private void makePhoneNumber(int i){

        phoneNumber += i;
        phoneNumberTextView.setText(phoneNumber);
    }

    private void request(){
        Log.d("YoonTag", "서버 통신 시작");

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams param = new RequestParams();

        Log.d("YoonTag", "서버 통신");

        try {
            param.put("customerPhone", phoneNumber);
//            param.put("customerPhone", "01044550423");

        } catch (Exception e){
            e.printStackTrace();
            Log.d("YoonTag", "Errorrorror");
        }

        try{
            client.post("http://165.194.35.161:3000/getCustomerInfo", param, new AsyncHttpResponseHandler() {
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

                        pointboxTextview.setText(resultJsonObject.getJSONObject(0).getString("point"));
                        pointBoxLayout.setVisibility(View.VISIBLE);

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

//    private void uploadArticle(){
//        AsyncHttpClient client = new AsyncHttpClient();
//
//        RequestParams param = new RequestParams();
//        // 이렇게 인자를 넘겨야 될 때
//        // 이름을 정해주고 추가하면 됨!
//        //param.put("id", "rlaace423");
//        //param.put("file", new File(fullPath));
//
//        try {
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
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("YoonTag", "Errorrorror");
//        }
//
//
//        client.post("http://165.194.35.161:3000/truckJoin", param, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                Log.d("YoonTag", bytes.toString());
//                Log.d("YoonTag", new String(bytes));
////                try {
////                    org.json.JSONArray arr = new org.json.JSONArray(new String(bytes));
////                    for (int i=0; i<arr.length(); i++) {
////                        MainActivity.items.add(new CustomerItem(arr.getJSONObject(i).getString("image1_id"),arr.getJSONObject(i).getString("name"),arr.getJSONObject(i).getString("category"),arr.getJSONObject(i).getString("price")));
////                    }
////
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                Log.d("YoonTag", new String(bytes));
//                Log.d("YoonTag", "에러러러러");
//            }
//        });
//    }

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

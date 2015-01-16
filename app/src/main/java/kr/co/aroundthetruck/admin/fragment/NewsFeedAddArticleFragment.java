package kr.co.aroundthetruck.admin.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.YSUtility;
import kr.co.aroundthetruck.admin.activity.MainActivity;
import kr.co.aroundthetruck.admin.util.RoundedTransformation;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFeedAddArticleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class NewsFeedAddArticleFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private LayoutInflater inflater;

    private ImageView profileImageview;
    private TextView profileTextView;
    private ImageView selectImageView;
    private EditText articleEditText;

    private ImageButton registerImageButton;
    private ImageButton cancelImageButton;

    private final int REQUEST_IMAGE = 002;

    public NewsFeedAddArticleFragment() {
        // Required empty public constructor
    }

    public static NewsFeedAddArticleFragment newInstance() {
        NewsFeedAddArticleFragment fragment = new NewsFeedAddArticleFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root =inflater.inflate(R.layout.fragment_news_feed_add_article, container, false);
        this.inflater = inflater;
        initialize(root);

        setLayout(inflater);
        return root;
    }

    private void initialize(View view){
        profileImageview = (ImageView) view.findViewById(R.id.fragment_news_feed_add_article_profile_imageview);
        profileTextView = (TextView) view.findViewById(R.id.fragment_news_feed_add_artcle_profile_textview);
        selectImageView = (ImageView) view.findViewById(R.id.fragment_news_feed_add_article_imageview);
        articleEditText = (EditText) view.findViewById(R.id.fragment_news_feed_add_article_edittext);

        selectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                getActivity().startActivityForResult(intent, REQUEST_IMAGE);
//                getActivity().startActivityForResult(intent, REQUEST_IMAGE);
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });

        registerImageButton = (ImageButton) view.findViewById(R.id.fragment_news_feed_add_article_imagebutton);
        registerImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelImageButton = (ImageButton) view.findViewById(R.id.fragment_news_feed_add_article_cancel_imagebutton);
        cancelImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        request();
    }

    private void setLayout(LayoutInflater inflater){

    }

    private void request(){
        Log.d("YoonTag", "서버 통신 시작");

        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams param = new RequestParams();

        Log.d("YoonTag", "서버 통신");

        try {
            param.put("truckIdx", ((MainActivity)getActivity()).truckIdx);

        } catch (Exception e){
            e.printStackTrace();
            Log.d("YoonTag", "Errorrorror");
        }

        try{
            client.post("http://165.194.35.161:3000/getTruckShort", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("YoonTag", new String(bytes));
                    try {
//                        org.json.JSONArray arr = new org.json.JSONArray(new String(bytes));
                        JSONObject jsonObject = new JSONObject(new String(bytes));
                        Log.d("YoonTag", "~~~~~~~!!!!!~~~~~~~" + jsonObject.getString("result"));
                        JSONArray resultJsonObject = new JSONArray(new String(jsonObject.getString("result")));


                        Log.d("YoonTag", "~~~~~~~!!!!!~~~~~~~" + resultJsonObject.getJSONObject(0).getString("name"));
                        Log.d("YoonTag", "~~~~~~~!!!!!~~~~~~~" + resultJsonObject.getJSONObject(0).getString("photo_filename"));

                        Picasso.with(getActivity()).load(YSUtility.addressImageStorage + URLEncoder.encode(resultJsonObject.getJSONObject(0).getString("photo_filename"), "UTF-8")).fit().transform(new RoundedTransformation(86)).into(profileImageview);
                        profileTextView.setText(resultJsonObject.getJSONObject(0).getString("name"));

                    } catch (JSONException e) {

                    } catch (Exception e) {

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK && data != null)
        {
            Log.d("YoonTag", "====== OnActivityResult is start ========= \n");

            Uri selPhotoUri = data.getData();
            try {
                Bitmap selPhoto = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selPhotoUri);
//                Bitmap roundPhoto = YSUtility.GetBitmapClippedCircle(selPhoto);
                Picasso.with(getActivity()).load(selPhotoUri).fit().into(selectImageView);
//                selectImageView.setImageBitmap(selPhoto);
//                adapter.addImage(targetPosition, roundPhoto);
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

}

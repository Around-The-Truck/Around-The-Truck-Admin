package kr.co.aroundthetruck.admin.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.activity.MainActivity;
import kr.co.aroundthetruck.admin.dto.ArticleData;
import kr.co.aroundthetruck.admin.dto.FoodMenuData;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class NewsFeedFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private NewsFeedListAdapter adapter;

    private ImageButton writeNewButton;

    private LayoutInflater inflater;

    private LinearLayout view1;
    private LinearLayout view2;

    private LinearLayout itemView;

    public NewsFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_news_feed, container, false);
        this.inflater = inflater;
        initialize(root);

        setLayout(inflater);

        return root;
    }

    private void initialize(View view) {

        writeNewButton = (ImageButton) view.findViewById(R.id.fragment_news_feed_write_imageButton);
        writeNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });

        view1 = (LinearLayout) view.findViewById(R.id.fragment_news_feed_view1);
        view2 = (LinearLayout) view.findViewById(R.id.fragment_news_feed_view2);

        itemView = (LinearLayout) view.findViewById(R.id.fragment_news_feed_item_layout);
    }

    private void setLayout(LayoutInflater inflater){
        adapter = new NewsFeedListAdapter(inflater);



        request();
    }

    public static NewsFeedFragment newInstance() {
        NewsFeedFragment fragment = new NewsFeedFragment();

        return fragment;
    }

    public void setListLayout(){
        Log.d("YoonTag", "ListView Reset~~~~~~~~~~~~~~~~");
        Log.d("YoonTag", "ListView Reset~~~~~~~~~~~~~~~~" + adapter.getCount());
        int view1h = 0, view2h = 0;
        for (int i = 0; i< adapter.getCount(); i++){
            View adapterView = adapter.getView(i, null, null);
            adapterView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

            int height = adapterView.getMeasuredHeight();
//            Log.d("YoonTag", "" + height);
            if (view1h <= view2h){
                view1.addView(adapter.getView(i, null, null));
                view1h += height;
            } else {
                view2.addView(adapter.getView(i, null, null));
                view2h += height;
            }
        }

//        view1.notifyAll();
//        view2.notifyAll();
    }

    private void request(){

        Log.d("YoonTag", "서버 통신 시작");
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams param = new RequestParams();
        // 이렇게 인자를 넘겨야 될 때
        // 이름을 정해주고 추가하면 됨!
        //param.put("id", "rlaace423");
        //param.put("file", new File(fullPath));

        Log.d("YoonTag", "서버 통신");

        try {
            param.put("writer", 1);
            param.put("writer_type", 1);

        } catch (Exception e){
            e.printStackTrace();
            Log.d("YoonTag", "Errorrorror");
        }

        Log.d("YoonTag", "서버 통신");

        try {


            client.post("http://165.194.35.161:3000/getArticleList", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("YoonTag", bytes.toString());
                    Log.d("YoonTag", new String(bytes));
                    final List<ArticleData> articleList = new ArrayList<>();
                    try {
                        org.json.JSONArray arr = new org.json.JSONArray(new String(bytes));
                        for (int j=0; j<arr.length(); j++) {
                            ArticleData adata = new ArticleData(arr.getJSONObject(j).getString("filename"),
                                    arr.getJSONObject(j).getInt("writer"), arr.getJSONObject(j).getInt("writer_type"),
                                    arr.getJSONObject(j).getString("contents"), arr.getJSONObject(j).getString("like"),
                                    arr.getJSONObject(j).getString("belong_to"), arr.getJSONObject(j).getString("reg_date"));
                            Log.d("YoonTag", ""+adata.writer);
                            articleList.add(adata);
//                            articleList.add(new ArticleData(arr.getJSONObject(j).getString("filename"),
//                                    arr.getJSONObject(j).getInt("writer"), arr.getJSONObject(j).getInt("writer_type"),
//                                    arr.getJSONObject(j).getString("contents"), arr.getJSONObject(j).getString("like"),
//                                    arr.getJSONObject(j).getString("belong_to"), arr.getJSONObject(j).getString("reg_date") )
//                            );
    //                        "idx":2,"filename":"default_image.jpg","writer":"1",
    //                                "writer_type":1,"contents":"맛없다..","like":"like","belong_to":"1",
    //                                "reg_date":"2014-04-09 16:07:02"
    //                        articleList.add(new ArticleData(arr.getJSONObject(i).getString("image1_id"), arr.getJSONObject(i).getString("name"), arr.getJSONObject(i).getString("category"), arr.getJSONObject(i).getString("price")));
                        }
                        Log.d("YoonTag", "ListView Reset~~~~~~~~~~~~~~~~" + arr.length());
                    } catch (Exception e) {
                        Log.d("YoonTag", "Json Error");
                        e.printStackTrace();
                    }

                    adapter.setmItems(articleList);
                    setListLayout();

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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

    class NewsFeedListAdapter extends BaseAdapter {
        private Context mContext;

        private LayoutInflater inflater;
        private List<ArticleData> mItems = new ArrayList<>();
//        private List<NewsFeedFragment> mItems = new ArrayList<>();

        private int totalPrice = 0;

        private View.OnClickListener removeButtonListener;

        final View.OnClickListener addReplyButtonListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                // do something here as well!!
                notifyDataSetChanged();
            }
        };

        public NewsFeedListAdapter(Context context){
            mContext = context;
//            mItems.add(new FoodMenuData());
        }

        public NewsFeedListAdapter(LayoutInflater inflater){
            this.inflater = inflater;
//            mItems.add(new FoodMenuData());
//            mItems.add(new FoodMenuData());
//            mItems.add(new FoodMenuData());
//            mItems.add(new FoodMenuData());
//            mItems.add(new FoodMenuData());

        }

        public void setmItems(List<ArticleData> items){
            mItems = items;
            this.notifyDataSetChanged();
        }

        public void setremoveButtonListener(View.OnClickListener listener){
            removeButtonListener = listener;
        }

        public int getCount(){
            return mItems.size();
        }

        public View getView(int position, View convertView, ViewGroup parent){
            ViewHolderCounter holder;

            if (convertView == null){
                convertView = inflater.inflate(R.layout.fragment_news_feed_item, null);

                holder = new ViewHolderCounter();

                holder.profileImageView = (ImageView) convertView.findViewById(R.id.fragment_news_feed_item_profile_imageview);
                holder.nameTextView = (TextView) convertView.findViewById(R.id.fragment_news_feed_item_name_textview);
                holder.mainPhotoImageView = (ImageView) convertView.findViewById(R.id.fragment_news_feed_item_photo_imageview);
                holder.hertTextView = (TextView) convertView.findViewById(R.id.fragment_news_feed_item_hert_textview);
                holder.replyNumTextView = (TextView) convertView.findViewById(R.id.fragment_news_feed_item_reply_textview);
                holder.contentTextView = (TextView) convertView.findViewById(R.id.fragment_news_feed_item_content_textview);
                holder.timeTextView = (TextView) convertView.findViewById(R.id.fragment_news_feed_item_time_textview);

                holder.replyEditTextView = (EditText) convertView.findViewById(R.id.fragment_news_feed_item_reply_edittext);


//                holder.addReplyImageButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        notifyDataSetChanged();
//                    }
//                });

                holder.addReplyImageButton = (ImageButton) convertView.findViewById(R.id.fragment_news_feed_item_add_reply_imagebutton);
                holder.addReplyImageButton.setOnClickListener(addReplyButtonListener);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolderCounter) convertView.getTag();
            }

//            Bitmap photo =
//            holder.mainPhotoImageView.setImageBitmap();
            ArticleData data = mItems.get(position);
            holder.hertTextView.setText(data.like);
            holder.contentTextView.setText(data.contents);


            holder.nameTextView.setText("position   "+position);
            String teststring = "";
            for(int i = 0; i < position; i++){
                teststring += "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
            }
            holder.replyEditTextView.setText(teststring);

            holder.addReplyImageButton.setTag(Integer.valueOf(position));
//            holder.removeButton.setTag(Integer.valueOf(position));
//
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

    private static class ViewHolderCounter{
        public TextView menuNameTextView;
        public TextView priceTextView;
        public Button removeButton;

        public ImageView profileImageView;
        public TextView nameTextView;
        public TextView timeTextView;
        public ImageView mainPhotoImageView;
        public TextView contentTextView;
        public TextView hertTextView;
        public TextView replyNumTextView;

        public EditText replyEditTextView;
        public ImageButton addReplyImageButton;
    }
}

package kr.co.aroundthetruck.admin.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.dto.FoodMenuData;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class NewsFeedFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private GridView newsFeedGridView;
    private NewsFeedListAdapter adapter;

    private ImageButton writeNewButton;

    public NewsFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_news_feed, container, false);

        initialize(root);

        setLayout(inflater);

        return root;
    }

    private void initialize(View view) {

        newsFeedGridView = (GridView) view.findViewById(R.id.fragment_news_feed_gridView);

        writeNewButton = (ImageButton) view.findViewById(R.id.fragment_news_feed_write_imageButton);
        writeNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setLayout(LayoutInflater inflater){
        adapter = new NewsFeedListAdapter(inflater);
        newsFeedGridView.setAdapter(adapter);
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
        private List<FoodMenuData> mItems = new ArrayList<>();

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
            mItems.add(new FoodMenuData());
        }

        public NewsFeedListAdapter(LayoutInflater inflater){
            this.inflater = inflater;
            mItems.add(new FoodMenuData());
            mItems.add(new FoodMenuData());
            mItems.add(new FoodMenuData());
            mItems.add(new FoodMenuData());
            mItems.add(new FoodMenuData());

        }

        public void setremoveButtonListener(View.OnClickListener listener){
            removeButtonListener = listener;
        }

        public void addItem(FoodMenuData data){
            mItems.add(data);
            totalPrice += data.getMenuPrice();
            this.notifyDataSetInvalidated();
        }

        public void removeItem(int position){
            FoodMenuData data = mItems.remove(position);
            totalPrice -= data.getMenuPrice();
            this.notifyDataSetInvalidated();
        }

        public int getTotalPrice(){
            return totalPrice;
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

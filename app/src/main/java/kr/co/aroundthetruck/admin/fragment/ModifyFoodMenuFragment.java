package kr.co.aroundthetruck.admin.fragment;

import android.app.Activity;
import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.YSUtility;
import kr.co.aroundthetruck.admin.dto.FoodMenuData;
import kr.co.aroundthetruck.admin.dto.YSNetwork;
import kr.co.aroundthetruck.admin.util.RoundedTransformation;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link kr.co.aroundthetruck.admin.fragment.ModifyFoodMenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ModifyFoodMenuFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private LayoutInflater inflater;

    private ListView listview;
    private FoodMenuRegisterAdapter adapter;

    private int targetPosition = 0;
    private final int REQUEST_IMAGE = 002;

    public ModifyFoodMenuFragment() {
        // Required empty public constructor
    }

    public static ModifyFoodMenuFragment newInstance() {
        ModifyFoodMenuFragment fragment = new ModifyFoodMenuFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_modify_food_menu, container, false);
        this.inflater = inflater;
        initialize(root);

        setLayout(inflater);
        return root;
    }

    private void initialize(View view) {
        listview = (ListView) view.findViewById(R.id.fragment_modify_food_menu_listview);
    }

    private void setLayout(LayoutInflater inflater){
        adapter = new FoodMenuRegisterAdapter(inflater, getActivity());
        listview.setAdapter(adapter);

        request();


        adapter.setImageSelectListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetPosition = (Integer) v.getTag();
                Log.v("YoonTag", targetPosition + "번 이미지뷰 선택됨");

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
    }

    private void request(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        Log.d("YoonTag", "서버 통신");

        try {
            param.put("truckIdx", "1");

        } catch (Exception e){
            e.printStackTrace();
            Log.d("YoonTag", "param Exception" + e);
        }
        final List<FoodMenuData> menuList = new ArrayList<>();

        try {
            client.post("http://165.194.35.161:3000/getMenuList", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("YoonTag", bytes.toString());
                    Log.d("YoonTag", new String(bytes));

                    try {
                        JSONObject jsonObject = new JSONObject(new String(bytes));
                        JSONArray arr = new JSONArray(new String(jsonObject.getString("result")));
                        for (int j=0; j<arr.length(); j++) {

                            FoodMenuData adata = new FoodMenuData(arr.getJSONObject(j).getString("name"),
                                    arr.getJSONObject(j).getInt("price"), arr.getJSONObject(j).getString("photo_filename"),
                                    arr.getJSONObject(j).getString("description"));
                            menuList.add(adata);
                        }

                        adapter.setmItems(menuList);

                    } catch (Exception e) {
                        Log.d("YoonTag", "Json Error : " + e);
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                    Log.d("YoonTag", new String(bytes));
                    Log.d("YoonTag", "에러러러러");
                }
            });
        }
        catch (Exception e){
//            Toast.makeText(MainActivity.this, "서버 접속 에러 ", Toast.LENGTH_SHORT).show();
            Log.d("YoonTag", "서버 접속 에러");
        }
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

        public FoodMenuRegisterAdapter(LayoutInflater inflater, Context context){
            this.inflater = inflater;
            this.mContext = context;
            mItems.add(new FoodMenuData());
        }

        public void setmItems(List<FoodMenuData> itemList){
            this.mItems = itemList;
            this.mItems.add(new FoodMenuData());
            this.notifyDataSetChanged();
        }

        public void setImageSelectListener(View.OnClickListener listener){
            imageSelectListener = listener;
        }

        public void addImage(int position, Bitmap image){
            mImageItems.add(position, image);
            mItems.add(new FoodMenuData());
            notifyDataSetChanged();
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
            ViewHolder holder;

            if (convertView == null){
                convertView = inflater.inflate(R.layout.fragment_modify_food_menu_item, null);

                holder = new ViewHolder();
                holder.menuImageView = (ImageView) convertView.findViewById(R.id.fragment_modify_food_menu_item_imageview);
                holder.menuNameTextView = (TextView) convertView.findViewById(R.id.fragment_modify_food_menu_item_menuname_textview);
                holder.priceTextView = (TextView) convertView.findViewById(R.id.fragment_modify_food_menu_item_price_textview);
                holder.removeButton = (Button) convertView.findViewById(R.id.fragment_pos_main_list_button_remove);

                holder.menuImageView.setOnClickListener(imageSelectListener);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            FoodMenuData data = mItems.get(position);
            holder.menuNameTextView.setText(data.getMenuName());
            holder.priceTextView.setText(data.getMenuPrice() + " 원");
            holder.menuImageView.setTag(Integer.valueOf(position));

            Picasso.with(mContext).load(YSUtility.addressImageStorage + data.getPhoto_filename()).fit().transform(new RoundedTransformation(100)).into(holder.menuImageView);
//            try{
//                holder.menuImageView.setImageBitmap(mImageItems.get(position));
//            }
//            catch ( Exception e){
//
//            }

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

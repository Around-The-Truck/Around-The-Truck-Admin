package kr.co.aroundthetruck.admin.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.co.aroundthetruck.admin.Dialog.PosPaymentDialog;
import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.activity.MainActivity;
import kr.co.aroundthetruck.admin.dto.FoodMenuData;
import kr.co.aroundthetruck.admin.ui.ATTFragment;

public class PosMainFragment extends ATTFragment {


    public PosMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pos_main, container, false);

        Log.v("ystag", "로그 테스트중입니당 onCreateView");
        initialize(root);

        setLayout(inflater);

        return root;
    }


    private GridView menuSelectGridView;
    private ListView menuResultListView;

    private Button countPointButton;
    private Button countCancelButton;
    private Button countFinishButton;

    private FoodMenuListAdapter adapterSelect;
    private FoodMenuCountListAdapter adapterCounter;

    private TextView counterTextView;

    private void initialize(View view){
        menuSelectGridView = (GridView) view.findViewById(R.id.fragment_pos_main_menu_gridview);
        menuResultListView = (ListView) view.findViewById(R.id.fragment_pos_main_count_Listview);

        counterTextView = (TextView) view.findViewById(R.id.fragment_pos_main_count_textview);
        changeCounterPrice(0);

        countCancelButton = (Button) view.findViewById(R.id.fragment_pos_main_cancel_button);
        countPointButton = (Button) view.findViewById(R.id.fragment_pos_main_point_button);
        countFinishButton = (Button) view.findViewById(R.id.fragment_pos_main_payment_button);

        MainActivity test = (MainActivity) getActivity();
        Log.d("YoonTag", ""+test.testString);

    }

    private void setLayout(LayoutInflater inflater){
        adapterSelect = new FoodMenuListAdapter(inflater);
        menuSelectGridView.setAdapter(adapterSelect);
        menuSelectGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("ystag", position +"번 선택됨");
                FoodMenuData fdata = (FoodMenuData) adapterSelect.getItem(position);
                adapterCounter.addItem(fdata);
                changeCounterPrice(adapterCounter.getTotalPrice());
            }
        });

        adapterCounter = new FoodMenuCountListAdapter(inflater);
        menuResultListView.setAdapter(adapterCounter);
        final View.OnClickListener removeButtonListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                // do something here as well!!
                adapterCounter.removeItem(position);
                changeCounterPrice(adapterCounter.getTotalPrice());
            }
        };
        adapterCounter.setremoveButtonListener(removeButtonListener);

        request();

        countCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCounter.removeAllItems();
                changeCounterPrice(0);
            }
        });

        countPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arguments = new Bundle();
                arguments.putString("totalPay", "" + adapterCounter.getTotalPrice());

                PosPointFragment pointFragment = new PosPointFragment();
                pointFragment.setArguments(arguments);

                getFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_container, pointFragment )
                        .addToBackStack(null)
                        .commit();
            }
        });

        countFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PosPaymentDialog dialog = new PosPaymentDialog(getActivity());
                dialog.show();
                dialog.setOnclickListenr1(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                        Bundle arguments = new Bundle();
                        arguments.putString("totalPay", "" + adapterCounter.getTotalPrice());

                        PosPointFragment pointFragment = new PosPointFragment();

                        pointFragment.setArguments(arguments);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.activity_main_container,  pointFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });

            }
        });
    }

    private void changeCounterPrice(int price){
        counterTextView.setText(""+price);
    }

    @Override
    public void setLayout(View v){

    }

    @Override
    public void initialize(){

    }

    public static PosMainFragment newInstance() {
        PosMainFragment fragment = new PosMainFragment();

        return fragment;
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

                        adapterSelect.setmItems(menuList);

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


    class FoodMenuListAdapter extends BaseAdapter {
        private Context mContext;

        private LayoutInflater inflater;
        private List<FoodMenuData> mItems = new ArrayList<>();

        public FoodMenuListAdapter(Context context){
            mContext = context;
            mItems.add(new FoodMenuData());
        }

        public void setmItems(List<FoodMenuData> itemList){
            this.mItems = itemList;
            this.notifyDataSetChanged();
        }

        public FoodMenuListAdapter(LayoutInflater inflater){
            this.inflater = inflater;
        }

        public int getCount(){
            return mItems.size();
        }

        public View getView(int position, View convertView, ViewGroup parent){
            ViewHolder holder;

            if (convertView == null){
                convertView = inflater.inflate(R.layout.fragment_pos_main_gridview, null);

                holder = new ViewHolder();
                holder.menuNameTextView = (TextView) convertView.findViewById(R.id.fragment_pos_main_gridview_textview_menuName);
                holder.priceTextView = (TextView) convertView.findViewById(R.id.fragment_pos_main_gridview_textview_price);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            FoodMenuData data = mItems.get(position);
            holder.menuNameTextView.setText(data.getMenuName());
            holder.priceTextView.setText("" + data.getMenuPrice());

            return convertView;
        }

        public Object getItem(int position){
            return mItems.get(position);
        }
        public long getItemId(int position){
            return 0;
        }
    }

    private static class ViewHolder{
        public TextView menuNameTextView;
        public TextView priceTextView;
    }

    class FoodMenuCountListAdapter extends BaseAdapter {
        private Context mContext;

        private LayoutInflater inflater;
        private List<FoodMenuData> mItems = new ArrayList<>();

        private int totalPrice = 0;

        private View.OnClickListener removeButtonListener;

        public FoodMenuCountListAdapter(Context context){
            mContext = context;
            mItems.add(new FoodMenuData());
        }

        public FoodMenuCountListAdapter(LayoutInflater inflater){
            this.inflater = inflater;
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

        public void removeAllItems(){
            mItems.clear();
            totalPrice = 0;
            this.notifyDataSetChanged();
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
                convertView = inflater.inflate(R.layout.fragment_pos_main_list, null);

                holder = new ViewHolderCounter();
                holder.menuNameTextView = (TextView) convertView.findViewById(R.id.fragment_pos_main_list_textview_menuname);
                holder.priceTextView = (TextView) convertView.findViewById(R.id.fragment_pos_main_list_textview_price);
                holder.removeButton = (Button) convertView.findViewById(R.id.fragment_pos_main_list_button_remove);


                holder.removeButton.setOnClickListener(removeButtonListener);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolderCounter) convertView.getTag();
            }

            FoodMenuData data = mItems.get(position);

            holder.removeButton.setTag(Integer.valueOf(position));

            holder.menuNameTextView.setText(data.getMenuName());
            holder.priceTextView.setText(""+data.getMenuPrice());

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
    }
}


//class FoodMenuPosView extends LinearLayout {
//
//    private Context mContext;
//    ImageView mImageView;
//
//    public FoodMenuPosView(Context context, FoodMenuData aItem) {
//        super(context);
//
//        mContext = context;
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(R.layout.activity_register_food_menu_listitem, this, true);
//
//        mImageView = (ImageView) findViewById(R.id.activity_register_food_menu_listitem_imageview);
//        mImageView.setImageResource(R.drawable.ic_launcher);
//        mImageView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "이미지 띄움", Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                mContext.startActivity(intent);
////                mContext.startActivityForResult(intent, REQUEST_IMAGE);
//            }
//        });
////        mImageView.setImageDrawable(aItem.getIcon());
//    }
//
//    public void setText(int index, String data){
//
//    }
//
//    public void setIcon(Drawable icon){
//
//    }
//}
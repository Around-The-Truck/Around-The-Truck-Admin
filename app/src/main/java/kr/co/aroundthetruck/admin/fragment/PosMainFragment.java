package kr.co.aroundthetruck.admin.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.dto.FoodMenuData;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PosMainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PosMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PosMainFragment extends Fragment {


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
    }

    private void changeCounterPrice(int price){
        counterTextView.setText(price + " 원");
    }



    class FoodMenuListAdapter extends BaseAdapter {
        private Context mContext;

        private LayoutInflater inflater;
        private List<FoodMenuData> mItems = new ArrayList<>();

        public FoodMenuListAdapter(Context context){
            mContext = context;
            mItems.add(new FoodMenuData());
        }

        public FoodMenuListAdapter(LayoutInflater inflater){
            this.inflater = inflater;
            mItems.add(new FoodMenuData());
            mItems.add(new FoodMenuData());
            mItems.add(new FoodMenuData());
            mItems.add(new FoodMenuData());
            mItems.add(new FoodMenuData());
            mItems.add(new FoodMenuData());
            mItems.add(new FoodMenuData());
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

            holder.menuNameTextView.setText("Position "+position);

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

            holder.removeButton.setTag(Integer.valueOf(position));

            holder.menuNameTextView.setText("Position "+position);

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
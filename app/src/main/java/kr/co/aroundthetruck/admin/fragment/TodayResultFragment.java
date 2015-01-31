package kr.co.aroundthetruck.admin.fragment;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import kr.co.aroundthetruck.admin.AroundTheTruckApplication;
import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.dto.CalculatorData;
import kr.co.aroundthetruck.admin.model.CurrentWeatherModel;
import kr.co.aroundthetruck.admin.ui.ATTFragment;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayResultFragment extends ATTFragment {
    private static final String TAG = TodayResultFragment.class.getSimpleName();
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private double latitude, longitude;

    private Location currentLocation;

    private TextView tvDate, tvWorkingTime, tvTotalSales, tvTotalCustomerCount, tvCostPerPerson, tvDateDay, tvPointuse, tvPointget;
//    private TextView tvTitle, tvWorkingTimeTitle, tvTotalSalesTitle, tvTotalCustomerCountTitle, tvCostPerPersonTitle;

    private TextView paycashTextview, payCardTextview;

    public static final int graph_color1 = Color.parseColor("#f27070");
    public static final int graph_color2 = Color.parseColor("#ef453e");
    public static final int graph_color3 = Color.parseColor("#d62b2b");

    // 성별 차트
    private PieChart pcSex;

    // 연령대 차트
    private BarChart bcAge;

    //시간대별 매출
    private LineChart lcTime;

    private ListView menuLanking;

//    private TextView tvRegion;
//    private TextClock tcTime;

    private SimpleDateFormat sdf;
    private Calendar cal;
    private SimpleDateFormat sdf2;
    private Calendar cal2;

    private CalculatorData info;

    private ImageButton calendarImageButton, mapImageButton, otherImageButton;

    private Callback<CurrentWeatherModel> cbCurrentWeatherModel = new Callback<CurrentWeatherModel>() {
        @Override
        public void success(CurrentWeatherModel currentWeatherModel, Response response) {
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            Log.w(TAG, "Error : " + retrofitError.getMessage() + ", URL : " + retrofitError.getUrl());
        }
    };


    public TodayResultFragment() {
        // Required empty public constructor
    }

    public static TodayResultFragment newInstance() {
        TodayResultFragment fragment = new TodayResultFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today_result, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLayout(view);
        initialize();
        getServerInfo();
        setTypeface();

    }

    @Override
    public void setLayout(View view) {
        tvDate = (TextView) view.findViewById(R.id.fragment_today_result_tv_date);
        tvWorkingTime = (TextView) view.findViewById(R.id.fragment_today_result_tv_working_time);
        tvTotalSales = (TextView) view.findViewById(R.id.fragment_today_result_tv_total_sales);
        tvTotalCustomerCount = (TextView) view.findViewById(R.id.fragment_today_result_tv_total_customer_count);
        tvCostPerPerson = (TextView) view.findViewById(R.id.fragment_today_result_tv_cost_per_person);
        tvDateDay = (TextView) view.findViewById(R.id.fragment_today_result_tv_day);

        paycashTextview = (TextView) view.findViewById(R.id.fragment_today_result_pay_cash_textview);
        payCardTextview = (TextView) view.findViewById(R.id.fragment_today_reuslt_pay_card_textview);

        tvPointget = (TextView) view.findViewById(R.id.fragment_today_result_tv_pointget);
        tvPointuse = (TextView) view.findViewById(R.id.fragment_today_result_tv_pointuse);

//        tvTitle = (TextView) view.findViewById(R.id.fragment_today_result_tv_title);
//        tvWorkingTimeTitle = (TextView) view.findViewById(R.id.fragment_today_result_tv_working_time_title);
//        tvTotalSalesTitle = (TextView) view.findViewById(R.id.fragment_today_result_tv_total_sales_title);
//        tvTotalCustomerCountTitle = (TextView) view.findViewById(R.id.fragment_today_result_tv_total_customer_count_title);
//        tvCostPerPersonTitle = (TextView) view.findViewById(R.id.fragment_today_result_tv_cost_per_person_title);

        pcSex = (PieChart) view.findViewById(R.id.fragment_today_result_pc_sex);
        bcAge = (BarChart) view.findViewById(R.id.fragment_today_result_bc_age);
        lcTime = (LineChart) view.findViewById(R.id.fragment_today_result_lc_time);

        menuLanking = (ListView) view.findViewById(R.id.fragment_today_lank_listview);


//        tcTime = (TextClock) view.findViewById(R.id.fragment_today_result_tc_time);
//        tvRegion = (TextView) view.findViewById(R.id.fragment_today_result_tv_region);

        calendarImageButton = (ImageButton) view.findViewById(R.id.fragment_calendar_pick_calendarView);
        calendarImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_container,  CalendarPickFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        mapImageButton = (ImageButton) view.findViewById(R.id.fragment_today_result_map_imagebutton);
        mapImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        otherImageButton = (ImageButton) view.findViewById(R.id.fragment_today_result_other_imagebutton);
        otherImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void initialize() {

        sdf = new SimpleDateFormat("yyyy/MM/dd");
        cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        sdf2 = new SimpleDateFormat("EEE");
        cal2 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

        tvDate.setText(sdf.format(cal.getTime()));
        tvDateDay.setText(sdf2.format(cal.getTime()));
    }

    private void setTypeface() {
        tvDate.setTypeface(AroundTheTruckApplication.nanumGothic);
        tvWorkingTime.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Multicolore.otf"));
        tvTotalSales.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Multicolore.otf"));
        tvTotalCustomerCount.setTypeface(AroundTheTruckApplication.nanumGothic);
        tvCostPerPerson.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Multicolore.otf"));

//        tvTitle.setTypeface(AroundTheTruckApplication.nanumGothic);
//        tvWorkingTimeTitle.setTypeface(AroundTheTruckApplication.nanumGothic);
//        tvTotalSalesTitle.setTypeface(AroundTheTruckApplication.nanumGothic);
//        tvTotalCustomerCountTitle.setTypeface(AroundTheTruckApplication.nanumGothic);
//        tvCostPerPersonTitle.setTypeface(AroundTheTruckApplication.nanumGothic);

        pcSex.setValueTypeface(AroundTheTruckApplication.nanumGothic);
        bcAge.setValueTypeface(AroundTheTruckApplication.nanumGothic);
        lcTime.setValueTypeface(AroundTheTruckApplication.nanumGothic);


//        tvRegion.setTypeface(AroundTheTruckApplication.nanumGothicLight);
    }

    private void setTextData() throws ParseException {
        tvWorkingTime.setText(info.getWorkingTime());
        tvTotalSales.setText(Integer.toString(info.getTodoys_sum()));
        tvTotalCustomerCount.setText(Integer.toString(info.getTotalCustomerCount()));
        tvCostPerPerson.setText(Integer.toString(info.getSalesPerPerson()));
    }

    private void setListData() {
        menuLanking.setAdapter(new MyCustomAdapter(TodayResultFragment.this.getActivity(), R.layout.text_row, info.getLankingMenu()));

    }

    private void setChartData() throws ParseException {
        ArrayList<Entry> yVals = new ArrayList<>();

        yVals.add(new Entry((float) info.getMen(), 0));
        yVals.add(new Entry((float) info.getWomen(), 1));


        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("  ");
        xVals.add(" ");

        PieDataSet set1 = new PieDataSet(yVals, " ");
        set1.setSliceSpace(3f);


        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(graph_color1);
        colors.add(graph_color2);
        set1.setColors(colors);

        PieData data = new PieData(xVals, set1);
        pcSex.setData(data);

        pcSex.setDrawHoleEnabled(true);
        pcSex.setDescription("");
        pcSex.setUsePercentValues(true);

        // undo all highlights
        pcSex.highlightValues(null);
        pcSex.invalidate();
        pcSex.animateX(1800);


///////////////////////
        ArrayList<String> xVals1 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            xVals1.add("");
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        float val = (float) (info.getHistoryAge(0));
        yVals1.add(new BarEntry(val, 1));


        ArrayList<BarEntry> yVals2 = new ArrayList<>();
        val = (float) (info.getHistoryAge(1));
        yVals2.add(new BarEntry(val, 2));


        ArrayList<BarEntry> yVals3 = new ArrayList<>();
        val = (float) (info.getHistoryAge(2));
        yVals3.add(new BarEntry(val, 3));

        ArrayList<BarEntry> yVals4 = new ArrayList<>();
        val = (float) (info.getHistoryAge(3));
        yVals4.add(new BarEntry(val, 4));

        BarDataSet bcSet1 = new BarDataSet(yVals1, "10대");
        BarDataSet bcSet2 = new BarDataSet(yVals2, "20대");
        BarDataSet bcSet3 = new BarDataSet(yVals3, "30대");
        BarDataSet bcSet4 = new BarDataSet(yVals4, "40대 이상");

        bcSet1.setColor(graph_color1);
        bcSet2.setColor(graph_color2);
        bcSet3.setColor(graph_color3);
        bcSet4.setColor(graph_color3);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(bcSet1);
        dataSets.add(bcSet2);
        dataSets.add(bcSet3);
        dataSets.add(bcSet4);

        BarData bcData = new BarData(xVals1, dataSets);

        bcAge.setDrawGridBackground(false);
        bcAge.setDrawHorizontalGrid(false);
        bcAge.setDrawVerticalGrid(false);
        bcAge.setDescription("");
        bcAge.setDrawBarShadow(false); // erase greybar
        bcAge.setData(bcData);

        ////////////////////////////////////////////////
        ArrayList<String> xBar1 = new ArrayList<>();
        for (String a : info.getTimeSperator()) {
            xBar1.add(a);
        }

        ArrayList<Entry> yBals1 = new ArrayList<Entry>();

        int i = 0;
        for (int a : info.getTimeCount()) {
            yBals1.add(new Entry(a, i));
            i++;

        }


        LineDataSet lcset1 = new LineDataSet(yBals1, "시간대별 매출");

        lcset1.setLineWidth(1.75f);
        lcset1.setCircleSize(3f);
        lcset1.setColor(graph_color1);
        lcset1.setCircleColor(graph_color1);
        lcset1.setHighLightColor(graph_color1);


        ArrayList<LineDataSet> lcdataSets = new ArrayList<LineDataSet>();
        lcdataSets.add(lcset1); // add the datasets

        LineData lcdata = new LineData(xBar1, lcdataSets);


        lcTime.setDrawGridBackground(false);
        lcTime.setDrawHorizontalGrid(false);
        lcTime.setDrawVerticalGrid(false);
        lcTime.setDescription("");

        lcTime.setData(lcdata);

    }

    public class MyCustomAdapter extends ArrayAdapter<String> {

        String[] objects;
        int textViewResourceId;
        Context mcontext;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               String[] objects) {
            super(context, textViewResourceId, objects);

            this.mcontext = context;
            this.objects = objects;
            this.textViewResourceId = textViewResourceId;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            //return super.getView(position, convertView, parent);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View row = inflater.inflate(textViewResourceId, parent, false);
            TextView label = (TextView) row.findViewById(R.id.ranking_menu);
            label.setTypeface(AroundTheTruckApplication.nanumGothic);
            label.setText(objects[position]);

            return row;
        }
    }


    public void getServerInfo() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        Log.d("YoonTag", "서버 통신");

        try {
            param.put("truckIdx", "5");

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("YoonTag", "param Exception" + e);
        }


        try {
            client.get("http://165.194.35.161:3000/calculate", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("YoonTag", bytes.toString());
                    Log.d("YoonTag", new String(bytes));

                    try {
                        JSONObject jsonObject = new JSONObject(new String(bytes));
                        JSONArray arr = new JSONArray(new String(jsonObject.getString("result")));


                        int j = arr.length() - 1;

                        CalculatorData calculatorData = new CalculatorData(arr.getJSONObject(j).getString("start"), arr.getJSONObject(j).getString("end"),
                                arr.getJSONObject(j).getInt("todays_sum"), arr.getJSONObject(j).getInt("salesPerPerson"),
                                arr.getJSONObject(j).getString("historyAge"), arr.getJSONObject(j).getString("historyGender"), arr.getJSONObject(j).getString("historyMenuName"),
                                arr.getJSONObject(j).getString("historyCardCashPoint"), arr.getJSONObject(j).getString("timeSeperator"), arr.getJSONObject(j).getString("timeCnt"), arr.getJSONObject(j).getString("pointGetUse"));

                        info = calculatorData;
                        setChartData();
                        setTextData();
                        setListData();

                        payCardTextview.setText(calculatorData.getCardcash().get(0));
                        paycashTextview.setText(calculatorData.getCardcash().get(1));

                        tvPointget.setText(calculatorData.getPointGetUse().get(0));
                        tvPointuse.setText(calculatorData.getPointGetUse().get(1));


                    } catch (Exception e) {
                        Log.d("YoonTag", "Json Error : " + e);
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.d("YoonTag", "에러러러러");
                }

            });
        } catch (Exception e) {
//            Toast.makeText(MainActivity.this, "서버 접속 에러 ", Toast.LENGTH_SHORT).show();
            Log.d("YoonTag", "서버 접속 에러");
        }


    }


}
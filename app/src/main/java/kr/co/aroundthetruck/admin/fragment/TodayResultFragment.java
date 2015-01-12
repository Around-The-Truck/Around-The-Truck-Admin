package kr.co.aroundthetruck.admin.fragment;


import android.app.Fragment;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import kr.co.aroundthetruck.admin.AroundTheTruckApplication;
import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.dto.CalculatorData;
import kr.co.aroundthetruck.admin.dto.FoodMenuData;
import kr.co.aroundthetruck.admin.model.CurrentWeatherModel;
import kr.co.aroundthetruck.admin.model.GridModel;
import kr.co.aroundthetruck.admin.model.WeatherModel;
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

    private TextView tvDate, tvWorkingTime, tvTotalSales, tvTotalCustomerCount, tvCostPerPerson;
    private TextView tvTitle, tvWorkingTimeTitle, tvTotalSalesTitle, tvTotalCustomerCountTitle, tvCostPerPersonTitle;

    // 성별 차트
    private PieChart pcSex;

    // 연령대 차트
    private BarChart bcAge;

    //시간대별 매출
    private LineChart lcTime;

//    private TextView tvRegion;
//    private TextClock tcTime;

    private SimpleDateFormat sdf;
    private Calendar cal;

    private Callback<CurrentWeatherModel> cbCurrentWeatherModel = new Callback<CurrentWeatherModel>() {
        @Override
        public void success(CurrentWeatherModel currentWeatherModel, Response response) {
            if (null != currentWeatherModel) {
                if (null != currentWeatherModel.getWeather()) {
                    if (null != currentWeatherModel.getWeather().getWeatherList()) {
                        if (null != currentWeatherModel.getWeather().getWeatherList().get(0)) {
                            WeatherModel weatherModel = currentWeatherModel.getWeather().getWeatherList().get(0);

                            if (null != weatherModel.getGrid()) {
                                GridModel grid = weatherModel.getGrid();
                                StringBuilder regionBuilder = new StringBuilder();

                                if (null != grid.getCity()) {
                                    regionBuilder.append(grid.getCity()).append(" ");
                                }

                                if (null != grid.getCounty()) {
                                    regionBuilder.append(grid.getCounty());
                                }

                                if (regionBuilder.length() < 1) {
//                                        tvRegion.setText("위치 정보를 가져올 수 없습니다.");
                                } else {
//                                        tvRegion.setText(regionBuilder.toString());
                                }
                            }
                        }
                    }
                }
            }
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
        setTypeface();
        setChartData();
    }

    @Override
    public void setLayout(View view) {
        tvDate = (TextView) view.findViewById(R.id.fragment_today_result_tv_date);
        tvWorkingTime = (TextView) view.findViewById(R.id.fragment_today_result_tv_working_time);
        tvTotalSales = (TextView) view.findViewById(R.id.fragment_today_result_tv_total_sales);
        tvTotalCustomerCount = (TextView) view.findViewById(R.id.fragment_today_result_tv_total_customer_count);
        tvCostPerPerson = (TextView) view.findViewById(R.id.fragment_today_result_tv_cost_per_person);

        tvTitle = (TextView) view.findViewById(R.id.fragment_today_result_tv_title);
        tvWorkingTimeTitle = (TextView) view.findViewById(R.id.fragment_today_result_tv_working_time_title);
        tvTotalSalesTitle = (TextView) view.findViewById(R.id.fragment_today_result_tv_total_sales_title);
        tvTotalCustomerCountTitle = (TextView) view.findViewById(R.id.fragment_today_result_tv_total_customer_count_title);
        tvCostPerPersonTitle = (TextView) view.findViewById(R.id.fragment_today_result_tv_cost_per_person_title);

        pcSex = (PieChart) view.findViewById(R.id.fragment_today_result_pc_sex);
        bcAge = (BarChart) view.findViewById(R.id.fragment_today_result_bc_age);
        lcTime = (LineChart) view.findViewById(R.id.fragment_today_result_lc_time);
//        tcTime = (TextClock) view.findViewById(R.id.fragment_today_result_tc_time);
//        tvRegion = (TextView) view.findViewById(R.id.fragment_today_result_tv_region);
    }

    @Override
    public void initialize() {
        sdf = new SimpleDateFormat(getResources().getString(R.string.weekday));
        cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

        tvDate.setText(sdf.format(cal.getTime()));
    }

    private void setTypeface() {
        tvDate.setTypeface(AroundTheTruckApplication.nanumGothic);
        tvWorkingTime.setTypeface(AroundTheTruckApplication.nanumGothic);
        tvTotalSales.setTypeface(AroundTheTruckApplication.nanumGothic);
        tvTotalCustomerCount.setTypeface(AroundTheTruckApplication.nanumGothic);
        tvCostPerPerson.setTypeface(AroundTheTruckApplication.nanumGothic);

        tvTitle.setTypeface(AroundTheTruckApplication.nanumGothic);
        tvWorkingTimeTitle.setTypeface(AroundTheTruckApplication.nanumGothic);
        tvTotalSalesTitle.setTypeface(AroundTheTruckApplication.nanumGothic);
        tvTotalCustomerCountTitle.setTypeface(AroundTheTruckApplication.nanumGothic);
        tvCostPerPersonTitle.setTypeface(AroundTheTruckApplication.nanumGothic);

        pcSex.setValueTypeface(AroundTheTruckApplication.nanumGothic);
        bcAge.setValueTypeface(AroundTheTruckApplication.nanumGothic);
        lcTime.setValueTypeface(AroundTheTruckApplication.nanumGothic);
//        tvRegion.setTypeface(AroundTheTruckApplication.nanumGothicLight);
    }

    private void setChartData() {
        ArrayList<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            yVals.add(new Entry((float) (Math.random() * 5) + 1, i));
        }

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("남자");
        xVals.add("여자");

        PieDataSet set1 = new PieDataSet(yVals, "성별 비율");
        set1.setSliceSpace(3f);


        ArrayList<Integer> colors = new ArrayList<>();


        for (int a : ColorTemplate.JOYFUL_COLORS){
            colors.add(a);

        }

        for(int a: ColorTemplate.LIBERTY_COLORS){

            colors.add(a);
        }


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


        ArrayList<String> xVals1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            xVals1.add("");
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        float val = (float) (Math.random() * 3);
        yVals1.add(new BarEntry(val, 1));


        ArrayList<BarEntry> yVals2 = new ArrayList<>();
        val = (float) (Math.random() * 3);
        yVals2.add(new BarEntry(val, 2));


        ArrayList<BarEntry> yVals3 = new ArrayList<>();
        val = (float) (Math.random() * 3);
        yVals3.add(new BarEntry(val, 3));

        BarDataSet bcSet1 = new BarDataSet(yVals1, "10대");
        BarDataSet bcSet2 = new BarDataSet(yVals2, "20대");
        BarDataSet bcSet3 = new BarDataSet(yVals3, "30대");
        bcSet1.setColor(colors.get(0));
        bcSet2.setColor(colors.get(1));
        bcSet3.setColor(colors.get(2));

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(bcSet1);
        dataSets.add(bcSet2);
        dataSets.add(bcSet3);

        BarData bcData = new BarData(xVals1, dataSets);

        bcAge.setDrawGridBackground(false);
        bcAge.setDrawHorizontalGrid(false);
        bcAge.setDrawVerticalGrid(false);
        bcAge.setDescription("");
        bcAge.setData(bcData);

        ////////////////////////////////////////////////
        ArrayList<String> xBar1 = new ArrayList<>();
        xBar1.add("12");
        xBar1.add("1");
        xBar1.add("2");
        xBar1.add("3");
        xBar1.add("4");
        xBar1.add("5");
        xBar1.add("6");
        xBar1.add("7");

        ArrayList<Entry> yBals1 = new ArrayList<Entry>();

        for(int i  = 0 ; i < 8 ; i++) {
            val = (float) (Math.random() * 3);
            yBals1.add(new Entry(val, i));

        }

        LineDataSet lcset1 = new LineDataSet(yBals1, "시간대별 매출");

            lcset1.setLineWidth(1.75f);
            lcset1.setCircleSize(3f);
            lcset1.setColor(Color.BLACK);
            lcset1.setCircleColor(Color.BLACK);
            lcset1.setHighLightColor(Color.BLACK);


        ArrayList<LineDataSet> lcdataSets = new ArrayList<LineDataSet>();
        lcdataSets.add(lcset1); // add the datasets

        LineData lcdata = new LineData(xBar1, lcdataSets);


        lcTime.setDrawGridBackground(false);
        lcTime.setDrawHorizontalGrid(false);
        lcTime.setDrawVerticalGrid(false);
        lcTime.setDescription("");

        lcTime.setData(lcdata);

    }


    public List<CalculatorData> getServerInfo(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams param = new RequestParams();
        Log.d("YoonTag", "서버 통신");

        try {
            param.put("truckIdx", "1");

        } catch (Exception e){
            e.printStackTrace();
            Log.d("YoonTag", "param Exception" + e);
        }
        final List<CalculatorData> info = new ArrayList<>();

        try {
            client.post("http://165.194.35.161:3000/Ca", param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Log.d("YoonTag", bytes.toString());
                    Log.d("YoonTag", new String(bytes));

                    try {
                        JSONObject jsonObject = new JSONObject(new String(bytes));
                        JSONArray arr = new JSONArray(new String(jsonObject.getString("result")));
                        for (int j=0; j<arr.length(); j++) {

                            ColorTemplate colorTemplate = new ColorTemplate(arr.getJSONObject(j).getString("name"))



//                            FoodMenuData adata = new FoodMenuData(arr.getJSONObject(j).getString("name"),
//                                    arr.getJSONObject(j).getInt("price"), arr.getJSONObject(j).getString("photo_filename"),
////                                    arr.getJSONObject(j).getString("description"));
////                            menuList.add(adata);
                        }
                    } catch (Exception e) {
                        Log.d("YoonTag", "Json Error : " + e);
                        e.printStackTrace();
                    }

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

        return info;
    }


}

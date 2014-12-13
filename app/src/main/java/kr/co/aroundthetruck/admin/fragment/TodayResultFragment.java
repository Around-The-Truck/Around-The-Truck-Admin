package kr.co.aroundthetruck.admin.fragment;


import android.app.Fragment;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import kr.co.aroundthetruck.admin.AroundTheTruckApplication;
import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.common.URL;
import kr.co.aroundthetruck.admin.loader.WeatherLoader;
import kr.co.aroundthetruck.admin.model.CurrentWeatherModel;
import kr.co.aroundthetruck.admin.model.GridModel;
import kr.co.aroundthetruck.admin.model.WeatherModel;
import kr.co.aroundthetruck.admin.ui.ATTFragment;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayResultFragment extends ATTFragment implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {
    private static final String TAG = TodayResultFragment.class.getSimpleName();
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private double latitude, longitude;

    private LocationClient locationClient;
    private Location currentLocation;

    private TextView tvDate, tvWorkingTime, tvTotalSales, tvTotalCustomerCount, tvCostPerPerson;
    private TextView tvTitle, tvWorkingTimeTitle, tvTotalSalesTitle, tvTotalCustomerCountTitle, tvCostPerPersonTitle;

    private PieChart pcSex;
    private BarChart bcAge;

//    private TextView tvRegion;
//    private TextClock tcTime;

    private SimpleDateFormat sdf;
    private Calendar cal;

    @Override
    public void onConnected(Bundle dataBundle) {
        // Display the connection status
        Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT).show();
        currentLocation = locationClient.getLastLocation();

        if (null != currentLocation) {
            Log.i(TAG, "Latitude : " + currentLocation.getLatitude() + ", Longitude : " + currentLocation.getLongitude());
            latitude = currentLocation.getLatitude();
            longitude = currentLocation.getLongitude();
        } else {
            Log.w(TAG, "Location is NULL");

            // 사당역
            latitude = 37.47732;
            longitude = 126.98167;
        }

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(URL.WEATHER_SERVER_API)
                .build();

        WeatherLoader service = restAdapter.create(WeatherLoader.class);
        service.loadWeather("1", String.valueOf(latitude), String.valueOf(longitude), new Callback<CurrentWeatherModel>() {
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
        });

    }

    @Override
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(getActivity(), "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        getActivity(),
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Toast.makeText(getActivity(), "Error Code : " + connectionResult.getErrorCode(), Toast.LENGTH_SHORT).show();
//            showErrorDialog(connectionResult.getErrorCode());
        }
    }


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
//        tcTime = (TextClock) view.findViewById(R.id.fragment_today_result_tc_time);
//        tvRegion = (TextView) view.findViewById(R.id.fragment_today_result_tv_region);
    }

    @Override
    public void initialize() {
        sdf = new SimpleDateFormat(getResources().getString(R.string.weekday));
        cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        locationClient = new LocationClient(getActivity(), this, this);

        tvDate.setText(sdf.format(cal.getTime()));
    }

    @Override
    public void onResume() {
        super.onStart();
        // Connect the client.
        locationClient.connect();
    }

    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    public void onPause() {
        // Disconnecting the client invalidates it.
        locationClient.disconnect();
        super.onStop();
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

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        set1.setColors(colors);

        PieData data = new PieData(xVals, set1);
        pcSex.setData(data);

        pcSex.setDrawHoleEnabled(false);
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
    }

}

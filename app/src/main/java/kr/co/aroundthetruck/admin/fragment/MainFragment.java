package kr.co.aroundthetruck.admin.fragment;


import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import kr.co.aroundthetruck.admin.AroundTheTruckApplication;
import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.callback.WeatherLoadCallback;
import kr.co.aroundthetruck.admin.common.UserSession;
import kr.co.aroundthetruck.admin.constant.BroadcastReceiverConstants;
import kr.co.aroundthetruck.admin.loader.WeatherLoader;
import kr.co.aroundthetruck.admin.model.CurrentWeatherModel;
import kr.co.aroundthetruck.admin.model.GridModel;
import kr.co.aroundthetruck.admin.model.HourlyWeatherModel;
import kr.co.aroundthetruck.admin.model.WeatherModel;
import kr.co.aroundthetruck.admin.ui.ATTFragment;
import kr.co.aroundthetruck.admin.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends ATTFragment implements WeatherLoadCallback {
    private static final String TAG = MainFragment.class.getSimpleName();
    private BroadcastReceiver dataReceiver;

    private BroadcastReceiver broadcastReceiver;
    private SimpleDateFormat sdfDate, sdfEee, sdfAmPm, sdfHour, sdfMinute;
    private TextView tvDate, tvEee, tvAmPm, tvHour, tvColon, tvMinute, tvCurrentRegion;
    private TextView tvTimeTitle;

    private CurrentWeatherModel currentWeatherModel;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();

        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                    Date currentTime = new Date();
                    tvAmPm.setText(sdfAmPm.format(currentTime));
                    tvEee.setText(sdfEee.format(currentTime));
                    tvHour.setText(sdfHour.format(currentTime));
                    tvMinute.setText(sdfMinute.format(currentTime));
                }
            }
        };

        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (broadcastReceiver != null)
            getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLayout(view);
        initialize();
        setTypeface();
    }

    @Override
    public void setLayout(View view) {
        tvDate = (TextView) view.findViewById(R.id.fragment_main_tv_date);
        tvEee = (TextView) view.findViewById(R.id.fragment_main_tv_eee);
        tvAmPm = (TextView) view.findViewById(R.id.fragment_main_tv_am_pm);
        tvHour = (TextView) view.findViewById(R.id.fragment_main_tv_hour);
        tvColon = (TextView) view.findViewById(R.id.fragment_main_tv_colon);
        tvMinute = (TextView) view.findViewById(R.id.fragment_main_tv_minute);
        tvCurrentRegion = (TextView) view.findViewById(R.id.fragment_main_tv_current_region);

        tvTimeTitle = (TextView) view.findViewById(R.id.fragment_main_tv_current_time_title);
    }

    @Override
    public void initialize() {
        sdfDate = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        sdfEee = new SimpleDateFormat("EEE", Locale.US);
        sdfAmPm = new SimpleDateFormat("a", Locale.US);
        sdfHour = new SimpleDateFormat("hh", Locale.KOREA);
        sdfMinute = new SimpleDateFormat("mm", Locale.KOREA);

        Date currentTime = new Date();
        tvDate.setText(sdfDate.format(currentTime));
        tvEee.setText(sdfEee.format(currentTime));
        tvAmPm.setText(sdfAmPm.format(currentTime));
        tvHour.setText(sdfHour.format(currentTime));
        tvMinute.setText(sdfMinute.format(currentTime));

        dataReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (null != intent) {
                    if (null != intent.getAction()) {
                        if (BroadcastReceiverConstants.LOCATION_DATA.equals(intent.getAction())) {
                            WeatherLoader.getLoader().loadWeatherHouly(1, UserSession.getInstance().getLatitude(), UserSession.getInstance().getLongitude(), MainFragment.this);
                        }
                    } else {
                        Toast.makeText(getActivity(), "intent.getAction() is null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "intent.getAction() is null", Toast.LENGTH_SHORT).show();
                }
            }
        };

        getActivity().registerReceiver(dataReceiver, new IntentFilter(BroadcastReceiverConstants.LOCATION_DATA));

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.activity_main_container, TruckMapFragment.newInstance())
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
    }

    private void setTypeface() {
        tvDate.setTypeface(AroundTheTruckApplication.multicolore);
        tvEee.setTypeface(AroundTheTruckApplication.multicolore);
        tvAmPm.setTypeface(AroundTheTruckApplication.multicolore);
        tvHour.setTypeface(AroundTheTruckApplication.multicolore);
        tvColon.setTypeface(AroundTheTruckApplication.multicolore);
        tvMinute.setTypeface(AroundTheTruckApplication.multicolore);

        tvTimeTitle.setTypeface(AroundTheTruckApplication.nanumGothicBold);
    }


    @Override
    public void onWeatherLoadSuccess(byte[] bytes) {
        String raw = new String(bytes);

        currentWeatherModel = Util.getGson().fromJson(raw, CurrentWeatherModel.class);
        HourlyWeatherModel hourlyWeatherModel = currentWeatherModel.getWeather();
        ArrayList<WeatherModel> weatherList = (ArrayList) hourlyWeatherModel.getWeatherList();

        if (null != currentWeatherModel) {
            if (null != weatherList) {
                if (null != weatherList.get(0)) {
                    WeatherModel weatherModel = weatherList.get(0);

                    if (null != weatherModel.getGrid()) {
                        GridModel grid = weatherModel.getGrid();
                        StringBuilder regionBuilder = new StringBuilder();

                        if (null != grid.getCity()) {
                            regionBuilder.append(grid.getCity()).append(" ");
                        }

                        if (null != grid.getCounty()) {
                            regionBuilder.append(grid.getCounty()).append(" ");
                        }

                        if (null != grid.getVillage()) {
                            regionBuilder.append(grid.getVillage());
                        }

                        if (regionBuilder.length() < 1) {
                            tvCurrentRegion.setText("위치 정보를 가져올 수 없습니다.");
                        } else {
                            tvCurrentRegion.setText(regionBuilder.toString().trim());
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "currentWeatherModel.getWeather().getWeatherList().get(0) is null", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "currentWeatherModel.getWeather().getWeatherList() is null", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "currentWeatherModel is null", Toast.LENGTH_SHORT).show();
        }
    }
}
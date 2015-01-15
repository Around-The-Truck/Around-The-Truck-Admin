package kr.co.aroundthetruck.admin.fragment;


import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.common.URL;
import kr.co.aroundthetruck.admin.common.UserSession;
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
public class MainFragment extends ATTFragment {
    private static final String TAG = MainFragment.class.getSimpleName();
    private BroadcastReceiver dataReceiver;

    private Button btn;

    private BroadcastReceiver broadcastReceiver;
    private SimpleDateFormat sdfDate, sdfAmPm, sdfHour, sdfMinute;
    private TextView tvDate, tvAmPm, tvHour, tvMinute, tvCurrentRegion;


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
                    Toast.makeText(getActivity(), "currentWeatherModel.getWeather() is null", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "currentWeatherModel is null", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            Log.w(TAG, "Error : " + retrofitError.getMessage() + ", URL : " + retrofitError.getUrl());
        }
    };


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
    }

    @Override
    public void setLayout(View view) {
        tvDate = (TextView) view.findViewById(R.id.fragment_main_tv_date);
        tvAmPm = (TextView) view.findViewById(R.id.fragment_main_tv_am_pm);
        tvHour = (TextView) view.findViewById(R.id.fragment_main_tv_hour);
        tvMinute = (TextView) view.findViewById(R.id.fragment_main_tv_minute);
        tvCurrentRegion = (TextView) view.findViewById(R.id.fragment_main_tv_current_region);
        btn = (Button) view.findViewById(R.id.fragment_main_btn);
    }

    @Override
    public void initialize() {
        sdfDate = new SimpleDateFormat("yyyy.MM.dd");
        sdfAmPm = new SimpleDateFormat("a", Locale.KOREA);
        sdfHour = new SimpleDateFormat("hh", Locale.KOREA);
        sdfMinute = new SimpleDateFormat("mm", Locale.KOREA);

        Date currentTime = new Date();
        tvDate.setText(sdfDate.format(currentTime));
        tvAmPm.setText(sdfAmPm.format(currentTime));
        tvHour.setText(sdfHour.format(currentTime));
        tvMinute.setText(sdfMinute.format(currentTime));

        dataReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (null != intent) {
                    if (null != intent.getAction()) {
                        if ("kr.co.aroundthetruck.admin.data".equals(intent.getAction())) {
                            RestAdapter restAdapter = new RestAdapter.Builder()
                                    .setEndpoint(URL.WEATHER_SERVER_API)
                                    .build();

//                            WeatherLoader service = restAdapter.create(WeatherLoader.class);
//                            service.loadWeather("1", String.valueOf(UserSession.getInstance().getLatitude()), String.valueOf(UserSession.getInstance().getLongitude()), cbCurrentWeatherModel);
                        }
                    } else {
                        Toast.makeText(getActivity(), "intent.getAction() is null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "intent.getAction() is null", Toast.LENGTH_SHORT).show();
                }
            }
        };

        getActivity().registerReceiver(dataReceiver, new IntentFilter("kr.co.aroundthetruck.admin.data"));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.activity_main_container, TruckMapFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
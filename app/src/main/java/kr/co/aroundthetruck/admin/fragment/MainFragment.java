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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

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
public class MainFragment extends ATTFragment implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {
    private static final String TAG = MainFragment.class.getSimpleName();

    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationClient locationClient;
    private Location currentLocation;

    private double latitude, longitude;

    private TextView tvCity, tvCountry;

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

                                    if (null == grid.getCity() || null == grid.getCountry()) {
                                        tvCity.setText("위치정보를 가져올 수 없습니다.");
                                    } else {
                                        if (null != grid.getCity()) {
                                            tvCity.setText(grid.getCity());
                                        }

                                        if (null != grid.getCountry()) {
                                            tvCountry.setText(grid.getCountry());
                                        }
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


    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
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
        tvCity = (TextView) view.findViewById(R.id.fragment_main_tv_city);
        tvCountry = (TextView) view.findViewById(R.id.fragment_main_tv_country);
    }

    @Override
    public void initialize() {
        locationClient = new LocationClient(getActivity(), this, this);
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
}

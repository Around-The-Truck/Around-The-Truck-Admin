package kr.co.aroundthetruck.admin.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.callback.TruckListLoadCallback;
import kr.co.aroundthetruck.admin.common.URL;
import kr.co.aroundthetruck.admin.common.UserSession;
import kr.co.aroundthetruck.admin.constant.BroadcastReceiverConstants;
import kr.co.aroundthetruck.admin.loader.TruckLoader;
import kr.co.aroundthetruck.admin.model.TruckListModel;
import kr.co.aroundthetruck.admin.model.TruckModel;
import kr.co.aroundthetruck.admin.ui.ATTFragment;
import kr.co.aroundthetruck.admin.util.Util;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class TruckMapFragment extends ATTFragment implements OnMapReadyCallback, TruckListLoadCallback {
    private static final String TAG = TruckMapFragment.class.getSimpleName();

    private MapFragment mapFragment;
    private GoogleMap map;

    private TruckListModel list;
    private ArrayList<TruckModel> items;
    private ArrayList<Marker> markerList;


    private BroadcastReceiver locationReceiver;

    private boolean isLoaded;

    @Override
    public void onTruckListLoadSuccess(int statusCode, byte[] bytes) {
        String raw = new String(bytes);

        Log.i(TAG, "Raw : " + raw);

        list = Util.getGson().fromJson(raw, TruckListModel.class);
        items = list.getTruckList();

        if (null != map) {

            for (int count = 0; count < items.size(); count++) {
                TruckModel truck = items.get(count);

                LatLng latLng = new LatLng(truck.getLatitude(), truck.getLongitude());

                MarkerOptions marker = new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.img_map_icon))
                        .position(latLng);

                map.addMarker(marker);
            }
        }
    }

    @Override
    public void onTruckListLoadFail(int statusCode, byte[] bytes, Throwable throwable) {

    }

    public TruckMapFragment() {
    }

    public static TruckMapFragment newInstance() {
        TruckMapFragment fragment = new TruckMapFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_truck_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLayout(view);
        initialize();
    }

    @Override
    public void setLayout(View view) {
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_truck_map);
    }

    @Override
    public void initialize() {
        mapFragment.getMapAsync(this);
        markerList = new ArrayList<>();

        locationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (null != intent) {
                    if (intent.getAction().equals(BroadcastReceiverConstants.LOCATION_DATA)) {
                        LatLng seoul = new LatLng(UserSession.getInstance().getLatitude(), UserSession.getInstance().getLongitude());
                        map.setMyLocationEnabled(true);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 13));
                    }
                }
            }
        };
        IntentFilter filter = new IntentFilter(BroadcastReceiverConstants.LOCATION_DATA);


        getActivity().registerReceiver(locationReceiver, filter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng seoul = new LatLng(UserSession.getInstance().getLatitude(), UserSession.getInstance().getLongitude());
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 13));

        TruckLoader.getLoader().getTruckListOnMap(UserSession.getInstance().getLatitude(), UserSession.getInstance().getLongitude(), TruckMapFragment.this);

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(final Marker marker) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.info_window, null);

                StringBuilder builder = new StringBuilder();

//                final ImageView iv = (ImageView) view.findViewById(R.id.info_window_iv);
                TextView tv = (TextView) view.findViewById(R.id.info_window_tv);

                TruckModel truck = getTruck(marker.getPosition());

                if (null != truck) {
                    if (null != truck.getName()) {
                        builder.append(truck.getName());
                    }

                    if (0 == truck.getStatus()) {
                        if (0 == builder.length()) {
                            builder.append("영업 종료\r\n");
                        } else {
                            builder.append(" / 영업 종료\r\n");
                        }
                    } else {
                        if (1 == truck.getStatus()) {
                            if (0 == builder.length()) {
                                builder.append("영업중\r\n");
                            } else {
                                builder.append(" / 영업 종료\r\n");
                            }
                        }
                    }

                    builder.append(Integer.toString(truck.getFollowCount()) + " 좋아요\r\n");

                    if (null != truck.getPhoto()) {
//                        Picasso.with(getActivity())
//                                .setIndicatorsEnabled(true);

                        final String url = URL.getApi("/upload/" + truck.getPhoto());

//                        Picasso.with(getActivity())
//                                .load(url)
//                                .fit().error(R.drawable.ic_launcher)
//                                .into(iv, new Callback() {
//                                    @Override
//                                    public void onSuccess() {
//                                        if (marker != null && marker.isInfoWindowShown()) {
//                                            marker.hideInfoWindow();
//                                            marker.showInfoWindow();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onError() {
//                                        iv.setVisibility(View.GONE);
//
//                                        Toast.makeText(getActivity(), "NOPE", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                        Log.i(TAG, "URL : " + url);
//                        Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
                    } else {
//                        iv.setVisibility(View.GONE);
                    }

                    tv.setText(builder.toString());
                }

                return view;
            }

            @Override
            public View getInfoContents(final Marker marker) {
                return null;
            }
        });
    }

    private TruckModel getTruck(LatLng latLng) {
        for (int count = 0; count < items.size(); count++) {
            TruckModel truck = items.get(count);
            if (latLng.latitude == truck.getLatitude() && latLng.longitude == truck.getLongitude()) {
                return items.get(count);
            }
        }
        return null;
    }
}

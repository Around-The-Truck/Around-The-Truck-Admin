package kr.co.aroundthetruck.admin.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.callback.TruckListLoadCallback;
import kr.co.aroundthetruck.admin.common.UserSession;
import kr.co.aroundthetruck.admin.loader.TruckLoader;
import kr.co.aroundthetruck.admin.model.TruckListModel;
import kr.co.aroundthetruck.admin.model.TruckModel;
import kr.co.aroundthetruck.admin.ui.ATTFragment;
import kr.co.aroundthetruck.admin.util.Util;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class TruckMapFragment extends ATTFragment implements OnMapReadyCallback, TruckListLoadCallback {

    private MapFragment mapFragment;
    private GoogleMap map;

    private TruckListModel list;
    private ArrayList<TruckModel> items;
    private ArrayList<Marker> markerList;

    @Override
    public void onTruckListLoadSuccess(int statusCode, byte[] bytes) {
        String raw = new String(bytes);

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

//
//            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//                @Override
//                public View getInfoWindow(Marker marker) {
//                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.info_window, null);
//
//                    StringBuilder builder = new StringBuilder();
//
//                    TruckModel truck = items.get(marker.get());
//
//                    TextView tv;
//
//                    return view;
//                }
//
//                @Override
//                public View getInfoContents(Marker marker) {
//                    return null;
//                }
//            });

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
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng sydney = new LatLng(UserSession.getInstance().getLatitude() + 0.5, UserSession.getInstance().getLongitude() + 0.5);
        LatLng seoul = new LatLng(UserSession.getInstance().getLatitude(), UserSession.getInstance().getLongitude());
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        TruckLoader.getLoader().getTruckListOnMap(UserSession.getInstance().getLatitude(), UserSession.getInstance().getLongitude(), TruckMapFragment.this);
    }
}

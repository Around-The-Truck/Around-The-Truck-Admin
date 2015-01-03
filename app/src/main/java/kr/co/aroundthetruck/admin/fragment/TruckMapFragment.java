package kr.co.aroundthetruck.admin.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import kr.co.aroundthetruck.admin.R;
import kr.co.aroundthetruck.admin.common.UserSession;
import kr.co.aroundthetruck.admin.ui.ATTFragment;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class TruckMapFragment extends ATTFragment implements OnMapReadyCallback {

    private MapFragment mapFragment;

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
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(UserSession.getInstance().getLatitude(), UserSession.getInstance().getLongitude());
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
        googleMap.addMarker(new MarkerOptions()
                .title("트럭 이름 : 현재 상태")
                .snippet("카테고리 : 좋아요 수")
                .position(sydney));
    }
}

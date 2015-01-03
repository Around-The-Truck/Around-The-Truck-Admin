package kr.co.aroundthetruck.admin.fragment;


import android.app.Fragment;
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
 * A simple {@link Fragment} subclass.
 */
public class HistoryMapFragment extends ATTFragment implements OnMapReadyCallback {

    private MapFragment mapFragment;

    public HistoryMapFragment() {
    }

    public static HistoryMapFragment newInstance() {
        HistoryMapFragment fragment = new HistoryMapFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_map, container, false);
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
                .findFragmentById(R.id.fragment_history_map);
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
                .title("2015/01/03")
                .snippet("300,000")
                .position(sydney));
    }
}

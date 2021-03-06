package kr.co.aroundthetruck.admin.activity;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import kr.co.aroundthetruck.admin.ui.ATTActivity;

public class MainActivity extends ATTActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private Boolean mRequestingLocationUpdates;

    public String testString = "aaaaaaaaannnnnnn";
    public static String truckIdx = "5";

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    @Override
    public void onConnected(Bundle dataBundle) {
        // Display the connection status
        Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();

        if (currentLocation == null) {
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        if (null != currentLocation) {
            Log.i(TAG, "Latitude : " + currentLocation.getLatitude() + ", Longitude : " + currentLocation.getLongitude());
            latitude = currentLocation.getLatitude();
            longitude = currentLocation.getLongitude();
        } else {
            Log.w(TAG, "Location is NULL");

            // 사당역
            latitude = 37.5280361;
            longitude = 126.9653303;
        }

        UserSession.getInstance().setLatitude(latitude);
        UserSession.getInstance().setLongitude(longitude);

        sendBroadcast(new Intent(BroadcastReceiverConstants.LOCATION_DATA));
    }

    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private double latitude, longitude;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayout();
        initialize();
    }

    @Override
    public void setLayout() {

    }

    @Override
    public void initialize() {
        // 사당역
        latitude = 37.5280361;
        longitude = 126.9653303;

        UserSession.getInstance().setLatitude(latitude);
        UserSession.getInstance().setLongitude(longitude);

        buildGoogleApiClient();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main_container, MainFragment.newInstance()).commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.

        try {
            if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
                startLocationUpdates();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        stopLocationUpdates();
    }

    /*
         * Called when the Activity is no longer visible.
         */
    @Override
    public void onStop() {
        // Disconnecting the client invalidates it.

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    private synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }catch (Exception e){
            Log.d("YoonTag", "MainActivity Resume Error");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_modi)
////        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.);
////        fragment.onActivityResult(requestCode, resultCode, data);
//    }
}

package cmpe277.skibuddy;

import android.Manifest;
import android.app.Service;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.LinkedList;
import java.util.List;

import cmpe277.skibuddy.model.Path;
import cmpe277.skibuddy.model.Point;
import cmpe277.skibuddy.model.Record;

public class HomeActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mMap;
    private Polyline mTrace;
    private Button mStartBtn;
    private Button mStopBtn;
    private UserSessionManager sessionManager;
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new UserSessionManager(getApplicationContext());
        setContentView(R.layout.activity_home);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mStartBtn = (Button)findViewById(R.id.btn_start);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTrace = mMap.addPolyline(new PolylineOptions());
                record = new Record();
                record.setStartTime(System.currentTimeMillis());
            }
        });
        mStopBtn = (Button)findViewById(R.id.btn_stop);
        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<LatLng> points = mTrace.getPoints();
                Path path = new Path();
                for (LatLng latLng : points) {
                    path.add(new Point(latLng.latitude, latLng.longitude));
                }
                record.setEndTime(System.currentTimeMillis());
                record.setPath(path);
                ServerAPI serverAPI = ServiceFactory.createService(ServerAPI.class);
                serverAPI.addRecord(sessionManager.getUserId(), record);
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location curLoc) {
                System.out.println("onMyLocationChange " + curLoc.getLatitude() + " " + curLoc.getLongitude());
                LatLng cur = new LatLng(curLoc.getLatitude(), curLoc.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 14));
                //mMap.addMarker(new MarkerOptions().position(cur).title("Me"));
                List<LatLng> pre = mTrace.getPoints();
                if (pre.size() != 0) {
                    Location last = new Location("");
                    last.setLatitude(pre.get(pre.size() - 1).latitude);
                    last.setLongitude(pre.get(pre.size() - 1).longitude);
                    record.setDistance(record.getDistance() + curLoc.distanceTo(last));
                }
                pre.add(new LatLng(curLoc.getLatitude(), curLoc.getLongitude()));
                mTrace.setPoints(pre);
            }

        });
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // Return false so that we don't consume the event and the default behavior still occurs
        return false;
    }
}

package cmpe277.skibuddy;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

import cmpe277.skibuddy.model.Path;
import cmpe277.skibuddy.model.Point;
import cmpe277.skibuddy.model.Record;
import cmpe277.skibuddy.model.User;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class HomeActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mMap;
    private Polyline mTrace;
    private Button mStartBtn;
    private Button mStopBtn;
    private UserSessionManager sessionManager;
    private Record record;
    private TextView mdistTxv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ServiceFactory.init(getString(R.string.server_url));
        sessionManager = new UserSessionManager(getApplicationContext());
        setContentView(R.layout.activity_home);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mdistTxv = (TextView)findViewById(R.id.txv_distance);
        mStartBtn = (Button)findViewById(R.id.btn_start);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTrace = mMap.addPolyline(new PolylineOptions());
                System.out.println("--addPolyline--");
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
                Call<Record> call = serverAPI.addRecord(sessionManager.getUserId(), record);
                call.enqueue(new Callback<Record>() {
                    @Override
                    public void onResponse(Response<Record> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            Toast.makeText(getApplicationContext(), "Save Path Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Save Path Failure", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), "Save Path Success!", Toast.LENGTH_SHORT).show();
                        finish();
                        //Log.d("Error", t.getMessage());
                    }
                });
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
                if (mTrace == null) return;
                List<LatLng> pre = mTrace.getPoints();
                if (pre.size() != 0) {
                    Location last = new Location("");
                    last.setLatitude(pre.get(pre.size() - 1).latitude);
                    last.setLongitude(pre.get(pre.size() - 1).longitude);
                    record.setDistance(record.getDistance() + curLoc.distanceTo(last));
                    NumberFormat formatter = new DecimalFormat("#0.00");
                    mdistTxv.setText("" + formatter.format(record.getDistance()) + " m");
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

package cmpe277.skibuddy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * @author yishafang on 12/2/15.
 */
public class RecordDetailActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = RecordDetailActivity.class.getSimpleName();

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        LatLng cur = new LatLng(37.4219983,-122.0909983);
        Polyline mTrace = mMap.addPolyline(new PolylineOptions());
        List<LatLng> pre = mTrace.getPoints();
        pre.add(new LatLng(37.4219983,-122.0909983));
        pre.add(new LatLng(37.426,-122.093));
        pre.add(new LatLng(37.4269983,-122.096));
        mTrace.setPoints(pre);

        mMap.addMarker(new MarkerOptions().position(cur).title("My Path"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 14));
    }
}

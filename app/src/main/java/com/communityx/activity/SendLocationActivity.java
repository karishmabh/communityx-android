package com.communityx.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.communityx.R;
import com.communityx.utils.CustomToolBarHelper;
import com.communityx.utils.MyLocation;
import com.communityx.utils.PermissionHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileOutputStream;

import static com.communityx.utils.AppConstant.REQUEST_PERMISSION_CODE;

public class SendLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_location);
        ButterKnife.bind(this);

        setUpToolbar();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initLocationPermission();
    }

    private void setUpToolbar() {
        CustomToolBarHelper customToolBarHelper = new CustomToolBarHelper(this);
        customToolBarHelper.setTitle("Send Location");
        customToolBarHelper.enableBackPress();
        customToolBarHelper.setImageTail(R.drawable.ic_send_location_nav_search);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mLocation != null) {
            setMarkeronMap(mLocation);
        }
    }

    @OnClick(R.id.fab_current_location)
    void currentLocationTapped() {
        initLocationPermission();
    }

    private void setMarkeronMap(Location location) {
        mLocation = location;
        if (mMap == null) return;

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    private void initiateLocation() {
        MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
            @Override
            public void gotLocation(Location location){
                setMarkeronMap(location);
            }
        };

        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(this, locationResult);
    }

    private void initLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionHelper permission = new PermissionHelper(SendLocationActivity.this);
            if (!permission.checkPermission(permissions))
                requestPermissions(permissions, REQUEST_PERMISSION_CODE);
            else initiateLocation();
        } else {
            initiateLocation();
        }
    }

    @OnClick(R.id.constraint_location)
    void sendLocation() {
        if(mMap == null) return;

        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {

            Bitmap bitmap;
            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                bitmap = snapshot;

                String filename = "bitmap.png";
                try {
                    FileOutputStream stream = openFileOutput(filename, Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    stream.close();
                    bitmap.recycle();
                }catch (Exception e) {

                }

                Intent intent = new Intent();
                intent.putExtra("bitmap", filename);
                setResult(101, intent);
                SendLocationActivity.this.finish();
            }
        };

        mMap.snapshot(callback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initiateLocation();
                }
                return;
            }
        }
    }
}

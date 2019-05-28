package com.communityx.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.communityx.R
import com.communityx.utils.CustomToolBarHelper
import com.communityx.utils.MyLocation
import com.communityx.utils.PermissionHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import java.io.FileOutputStream

import com.communityx.utils.AppConstant.REQUEST_PERMISSION_CODE

class SendLocationActivity : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    private var mLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_location)
        ButterKnife.bind(this)

        setUpToolbar()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        initLocationPermission()
    }

    private fun setUpToolbar() {
        val customToolBarHelper = CustomToolBarHelper(this)
        customToolBarHelper.setTitle("Send Location")
        customToolBarHelper.enableBackPress()
        customToolBarHelper.setImageTail(R.drawable.ic_send_location_nav_search)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mLocation?.let { setMarkeronMap(it) }
    }

    @OnClick(R.id.fab_current_location)
    internal fun currentLocationTapped() {
        initLocationPermission()
    }

    private fun setMarkeronMap(location: Location) {
        mLocation = location
        if (mMap == null) return

        val latLng = LatLng(location.latitude, location.longitude)
        mMap!!.addMarker(MarkerOptions().position(latLng))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }

    private fun initiateLocation() {
        val locationResult = object : MyLocation.LocationResult() {
            override fun gotLocation(location: Location) {
                setMarkeronMap(location)
            }
        }

        val myLocation = MyLocation()
        myLocation.getLocation(this, locationResult)
    }

    private fun initLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = PermissionHelper(this)
            if (!permission.checkPermission(*permissions))
                requestPermissions(permissions, REQUEST_PERMISSION_CODE)
            else
                initiateLocation()
        } else {
            initiateLocation()
        }
    }

    @OnClick(R.id.constraint_location)
    internal fun sendLocation() {
        if (mMap == null) return

        val callback = object : GoogleMap.SnapshotReadyCallback {

            lateinit var bitmap: Bitmap
            override fun onSnapshotReady(snapshot: Bitmap) {
                bitmap = snapshot

                val filename = getString(R.string.string_filename)
                try {
                    val stream = openFileOutput(filename, Context.MODE_PRIVATE)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

                    stream.close()
                    bitmap.recycle()
                } catch (e: Exception) {

                }

                val intent = Intent()
                intent.putExtra("bitmap", filename)
                setResult(101, intent)
                finish()
            }
        }

        mMap!!.snapshot(callback)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initiateLocation()
                }
                return
            }
        }
    }
}

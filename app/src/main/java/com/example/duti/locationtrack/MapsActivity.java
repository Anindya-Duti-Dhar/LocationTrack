package com.example.duti.locationtrack;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.duti.locationtrack.database.DbManager;
import com.example.duti.locationtrack.models.AllLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<AllLocation> mListMarker = new ArrayList<AllLocation>();
    private DbManager helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        helper = DbManager.getInstance(MapsActivity.this);

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
        mListMarker = helper.getAllLocationDataFromDB();

        setUpMap();

        // *** Focus & Zoom
        Double Latitude = Double.parseDouble(mListMarker.get(0).getLatitude());
        Double Longitude = Double.parseDouble(mListMarker.get(0).getLongitude());
        LatLng coordinate = new LatLng(Latitude, Longitude);
        googleMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 8));

        // *** Marker (Loop)
        initMarker(mListMarker);
    }

    public void setUpMap(){
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);
    }

    private void initMarker(List<AllLocation> listData){
        //iterasi semua data dan tampilkan markernya
        for (int i=0; i<mListMarker.size(); i++){
            //set latlng nya
            LatLng location = new LatLng(Double.parseDouble(mListMarker.get(i).getLatitude()), Double.parseDouble(mListMarker.get(i).getLongitude()));
            //tambahkan markernya
            mMap.addMarker(new MarkerOptions().position(location).title(mListMarker.get(i).getAddress()));
            //set latlng index ke 0
            LatLng latLng = new LatLng(Double.parseDouble(mListMarker.get(0).getLongitude()), Double.parseDouble(mListMarker.get(0).getLongitude()));
            //lalu arahkan zooming ke marker index ke 0
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude,latLng.longitude), 11.0f));
        }
    }

}

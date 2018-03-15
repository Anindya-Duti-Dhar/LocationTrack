package com.example.duti.locationtrack;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.example.duti.locationtrack.models.AllLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<AllLocation> mListMarker = new ArrayList<AllLocation>();
    private RelativeLayout mapLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mListMarker = allLocationList();

        mapLayout = (RelativeLayout)findViewById(R.id.map_layout);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private List<AllLocation> allLocationList(){
        List<AllLocation> allLocationList = new ArrayList<AllLocation>();
        allLocationList.add(new AllLocation("23.791516", "90.411532", "Save the Children"));
        allLocationList.add(new AllLocation("23.791639", "90.411070", "Hotel Lake View Plaza"));
        allLocationList.add(new AllLocation("23.791569", "90.412027", "Taste & Twist"));
        allLocationList.add(new AllLocation("23.791079", "90.411308", "VIP Shaad Fuska & Chatpoti"));
        allLocationList.add(new AllLocation("23.790535", "90.412738", "Amari Dhaka"));
        allLocationList.add(new AllLocation("23.790203", "90.412343", "Lakeshore Hotel"));
        allLocationList.add(new AllLocation("23.790519", "90.405267", "Aarong Banani"));
        allLocationList.add(new AllLocation("23.790848", "90.403159", "Cafe Entro"));
        return allLocationList;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setUpMap();

        initMarker(mListMarker);

        //focusOnMap();

        final LatLng location = new LatLng(Double.parseDouble(mListMarker.get(0).getLatitude()), Double.parseDouble(mListMarker.get(0).getLongitude()));

        mapLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                moveToAllMarkers(location);
            }
        });

    }

    public void setUpMap(){
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);
    }

    private void initMarker(List<AllLocation> listData){
        for (int i=0; i<mListMarker.size(); i++){
            LatLng location = new LatLng(Double.parseDouble(mListMarker.get(i).getLatitude()), Double.parseDouble(mListMarker.get(i).getLongitude()));
            mMap.addMarker(new MarkerOptions().position(location).title(mListMarker.get(i).getAddress()));
            LatLng latLng = new LatLng(Double.parseDouble(mListMarker.get(0).getLongitude()), Double.parseDouble(mListMarker.get(0).getLongitude()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude,latLng.longitude), 11.0f));
        }
    }

    private void focusOnMap(){
        mapLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LatLng location = new LatLng(Double.parseDouble(mListMarker.get(0).getLatitude()), Double.parseDouble(mListMarker.get(0).getLongitude()));
                moveToAllMarkers(location);
            }
        });
    }

    ////googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 8));

    private void moveToAllMarkers(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

}

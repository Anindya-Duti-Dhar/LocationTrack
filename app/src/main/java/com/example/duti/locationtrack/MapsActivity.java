package com.example.duti.locationtrack;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.duti.locationtrack.models.AllLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Context mContext;
    private GoogleMap mMap;
    private RelativeLayout mapLayout;

    // list of marker where data are like lat, long, address
    private List<AllLocation> mListMarker = new ArrayList<AllLocation>();

    // hash map to show custom info window with custom data set
    HashMap<String, AllLocation> markerHolderMap = new HashMap<String, AllLocation>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mContext = this;

        // init marker list
        mListMarker = allLocationList();

        // init view
        mapLayout = (RelativeLayout)findViewById(R.id.map_layout);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    // create marker list method
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

    // call when map is ready to show
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // assign variable
        mMap = googleMap;

        // init map features
        setUpMap();

        // add marker to the map
        initMarker(mListMarker);

        // zoom the map to a certain location
        focusOnMap();
    }

    // show bottom card was marker info
    private void showBottomCard() {
        View view = getLayoutInflater().inflate(R.layout.bottom_note, null);
        Dialog mBottomSheetDialog = new Dialog(mContext, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
    }

    // set up map features
    public void setUpMap(){
        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // init marker click listener
        mMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {
                    boolean doNotMoveCameraToCenterMarker = true;
                    public boolean onMarkerClick(Marker marker) {
                        marker.showInfoWindow(); // show info window
                        showBottomCard();   // show bottom sheet layout
                        return doNotMoveCameraToCenterMarker;    // if return true then it never move camera to center
                    }
                });

        // init map custom info window
        // example link:
        // https://stackoverflow.com/questions/30068639/how-can-i-display-more-than-just-the-title-and-snippet-in-a-google-maps-custom-i?lq=1
        // standard and optimized way:
        // https://github.com/commonsguy/cw-omnibus/tree/master/MapsV2/Popups
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker arg0) {

                View v = getLayoutInflater().inflate(R.layout.marker_note_card, null);

                TextView tLocation = (TextView) v.findViewById(R.id.paq);

                TextView tSnippet = (TextView) v.findViewById(R.id.names);

                TextView tDates = (TextView) v.findViewById(R.id.dates);

                TextView tPlaces = (TextView) v.findViewById(R.id.places);

                //These are standard, just uses the Title and Snippet
                tLocation.setText(arg0.getTitle());

                tSnippet.setText(arg0.getSnippet());

                //Now get the extra info you need from the HashMap
                //Store it in a MarkerHolder Object
                AllLocation mHolder = markerHolderMap.get(arg0.getId()); //use the ID to get the info

                tDates.setText(mHolder.getAddress() + " " + mHolder.getLatitude());

                tPlaces.setText(mHolder.getAddress() + " " + mHolder.getLongitude());

                return v;

            }
        });
    }

    // init marker list
    private void initMarker(List<AllLocation> listData){
        for (int i=0; i<mListMarker.size(); i++){
            LatLng location = new LatLng(Double.parseDouble(mListMarker.get(i).getLatitude()), Double.parseDouble(mListMarker.get(i).getLongitude()));
            // active the following line if we do not want to show custom info window with custom data set
            // and de active the two lines after that
            // mMap.addMarker(new MarkerOptions().position(location).title(mListMarker.get(i).getAddress()));
            // if we want to show custom info window with custom data set
            Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(mListMarker.get(i).getAddress()));
            markerHolderMap.put(marker.getId(), mListMarker.get(i));
            LatLng latLng = new LatLng(Double.parseDouble(mListMarker.get(0).getLongitude()), Double.parseDouble(mListMarker.get(0).getLongitude()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude,latLng.longitude), 11.0f));
        }
    }

    // set zoom point in map when it appears for the first time
    private void focusOnMap(){
        mapLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LatLng location = new LatLng(Double.parseDouble(mListMarker.get(0).getLatitude()), Double.parseDouble(mListMarker.get(0).getLongitude()));
                // move camera
                moveToAllMarkers(location);
            }
        });
    }

    // moe map camera to certain position
    private void moveToAllMarkers(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

}

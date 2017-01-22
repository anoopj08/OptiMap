package com.example.anoop.optimap;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static android.net.Uri.parse;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private int ACCESS_FINE_LOCATION_CONSTANT;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private PlacePicker.IntentBuilder placeBuilder = new PlacePicker.IntentBuilder();
    private int PLACE_PICKER_REQUEST = 1;
    private LatLngBounds.Builder builder = new LatLngBounds.Builder();
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    private ListView mListView;
    //String[] places = new String[10];
    private ArrayList<String> places = new ArrayList<String>();
    private ArrayList<Place> listOfPlaces = new ArrayList<Place>();
    private ArrayList<Destination> destinations = new ArrayList<Destination>();
    private int numPlaces = 0;
    private ArrayAdapter adapter;
    private Intent mapIntent;
    private static final String testMapsURL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=75%209th%20Ave%20New%20York%2C%20NY&destinations=Bridgewater%20Commons%2C%20Commons%20Way%2C%20Bridgewater%2C%20NJ%7CThe%20Mall%20At%20Short%20Hills%2C%20Morris%20Turnpike%2C%20Short%20Hills%2C%20NJ%7CMonmouth%20Mall%2C%20Eatontown%2C%20NJ%7CWestfield%20Garden%20State%20Plaza%2C%20Garden%20State%20Plaza%20Boulevard%2C%20Paramus%2C%20NJ%7CNewport%20Centre%20Mall%2C%20Jersey%20City%2C%20NJ&departure_time=1541202457&traffic_model=best_guess&key=AIzaSyADKbSwzN-1LJx_xKVf2FWHftvSSNi51w8";
    private RequestQueue requestQueue;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


//                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        Button addButton = (Button) findViewById(R.id.add_place);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int PLACE_PICKER_REQUEST = 1;
//                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(placeBuilder.build(MapsActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        Button goButton = (Button) findViewById(R.id.create_route);
        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createRoute();

            }
        });


        mListView = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, places);

        //ListView listView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        fetchPosts();
    }

    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, testMapsURL, onPostsLoaded, onPostsError);
        requestQueue.add(request);
    }
    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

//            Type collectionType = new TypeToken<Collection<PostModel>>(){}.getType();
//            Collection<PostModel> posts = gson.fromJson(response, collectionType);

            //PostModel post = gson.fromJson(response, PostModel.class);
           // Log.i("PostActivity", posts.size() + " posts loaded.");
            //for (PostModel post : posts) {
            JSONObject obj = null;
            try {
                obj = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("PostActivity", obj.toString());

            ArrayList<String> list = new ArrayList<String>();
            JSONArray array = null;
            try {
                array = obj.getJSONArray("elements");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i = 0 ; i < array.length() ; i++){
                try {
                    list.add(array.getJSONObject(i).getString("duration_in_traffic"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            for(String s : list){
                Log.i("PostActivity", s);
            }
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("MapsActivity", error.toString());
        }
    };

    public void createRoute(){
        String mapsURL = "https://maps.google.com/maps?saddr=";
        CharSequence testID = "";
        for(int x = 0 ; x < numPlaces ; x++){
            Place currPlace = listOfPlaces.get(x);
            Toast.makeText(this, currPlace.getId(), Toast.LENGTH_LONG).show();
            testID = currPlace.getAddress();
            if(x == 0){
                mapsURL += currPlace.getAddress()+"&daddr=";
            }else{
                mapsURL += currPlace.getAddress();
                if(x != numPlaces -1 ){
                    mapsURL  += "+to:";
                }else{
                    //its lit
                }
            }
        }
        //mapIntent = new Intent(android.content.Intent.ACTION_VIEW,
        //        Uri.parse("https://maps.google.com/maps?saddr=San+Francisco,+CA&daddr=Los+Angeles,+CA+to:Phoenix,+AZ+to:Houston,+TX+to:Jacksonville,+FL+to:New+York,+NY+to:Buffalo,+NY+to:Chicago,+IL+to:"+testID+"+to:Seattle,+WA+to:San+Jose,+CA"));
        mapIntent = new Intent(android.content.Intent.ACTION_VIEW,
                parse(mapsURL));
        mapIntent.setPackage("com.google.android.apps.maps");
        testPathTime(destinations);
        //startActivity(mapIntent);
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
        LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_CONSTANT
                );
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_CONSTANT
                );
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }

        LatLng currLoc = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        Marker curr = mMap.addMarker(new MarkerOptions().position(currLoc).title("Current Location"));
        markers.add(curr);
        curr.remove();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(currLoc));

//        if (mLastLocation != null) {
//            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
//            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
//        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String placeName = String.format("Place: %s", place.getName());
                LatLng currLatLng = place.getLatLng();
                Marker curr = mMap.addMarker(new MarkerOptions().position(currLatLng).title(placeName));
                markers.add(curr);

                Destination currDest = new Destination((String) place.getAddress(), (String) place.getName());
                destinations.add(currDest);
                places.add(String.format("%s", place.getName()));
                listOfPlaces.add(place);
                numPlaces++;
                mListView.setAdapter(adapter);
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
            }
        }
    }

    public void testPathTime(ArrayList<Destination> dests){
        String testURI = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=Seattle&destinations=San+Francisco&key=AIzaSyADKbSwzN-1LJx_xKVf2FWHftvSSNi51w8";
        //String gonnawanna = //parse(testURI);
        //JSONObject jo = new JSON(readUrl(testURI));
        //import org.json.JSONObject;
        //mapIntent.setPackage("com.google.android.apps.maps");

        startActivity(mapIntent);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
/*its lit af bros*/

package unima.campus_navigation.activities;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import java.util.ArrayList;

import unima.campus_navigation.R;
import unima.campus_navigation.model.Entrance;
import unima.campus_navigation.model.IndoorNavigation;
import unima.campus_navigation.model.Room;
import unima.campus_navigation.service.GeoFenceTransitionIntentService;
import unima.campus_navigation.service.ProvideMockDataServiceImpl;
import unima.campus_navigation.util.CustomSpinner;

public class MainActivity extends AppCompatActivity implements CustomSpinner.OnItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback, ResultCallback<Status>, OnMapReadyCallback, View.OnTouchListener, MaterialSearchBar.OnSearchActionListener, MaterialSearchView.SearchViewListener, MaterialSearchView.OnQueryTextListener, AdapterView.OnItemClickListener, OnMenuTabSelectedListener, View.OnClickListener {


    protected ArrayList<Geofence> mGeofenceList;
    protected GoogleApiClient     mGoogleApiClient;

    private CoordinatorLayout    coordinatorLayout;
    private FloatingActionButton floatingActionButton;
    private MaterialSearchView   searchView;
    private Toolbar              toolbar;
    private GoogleMap            map;
    private SupportMapFragment   mapFragment;
    private BottomBar            bottomBar;
    private String                     result       = "";
    private ProvideMockDataServiceImpl dataProvider = new ProvideMockDataServiceImpl();
    private Spinner           spinner;
    private CardView          cardView;
    private MenuItem          menuItem;
    private Context           ctx;
    private SharedPreferences sharedPreferences;
    private boolean           isSpinnerTouched;
    private Room              room;


    public static final String INDOORNAVIGATION_KEY = "INDOORNAVIGATION_KEY";
    public static final String ROOM_KEY             = "ROOM_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Permissioncheck
        if (ContextCompat.checkSelfPermission(this,
                                              android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        // Empty list for storing geofences.
        mGeofenceList = new ArrayList<Geofence>();


        // Kick off the request to build GoogleApiClient.
        buildGoogleApiClient();


        ctx = getApplicationContext();
        floatingActionButton = (FloatingActionButton) findViewById(R.id.indoor_fab);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        spinner = (Spinner) findViewById(R.id.indoorspinner);
        cardView = (CardView) findViewById(R.id.indoor_card_view);
        menuItem = (MenuItem) findViewById(R.id.action_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search");


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                                                                 dataProvider.getIndoorStrings());

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setOnTouchListener(this);

        floatingActionButton.setOnClickListener(this);

        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_main);

        searchView.setVoiceSearch(true);
        searchView.setSubmitOnClick(true);
        searchView.setOnItemClickListener(this);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchViewListener(this);
        String[] array = new String[dataProvider.getRoomStrings().size()];
        searchView.setSuggestions(dataProvider.getRoomStrings().toArray(array));


        bottomBar.setItemsFromMenu(R.menu.menu, this);
        bottomBar.setActiveTabColor("#FF4081");

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        menuItem = menu.findItem(R.id.action_search);
        searchView.setMenuItem(menuItem);

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (isSpinnerTouched) {
            Log.d("Spinner", "clicked");
            String selectedIndoorNavigation = parent.getItemAtPosition(position).toString();
            //Send intent
            sharedPreferences = ctx.getSharedPreferences(INDOORNAVIGATION_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ROOM_KEY, selectedIndoorNavigation);
            editor.commit();
            Intent intent = new Intent(MainActivity.this, IndoorNavigationDetailActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnecting() || !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnecting() || mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onClick(View v) {
        //FIXME get Entrance by room
        Room room = null;
        Entrance entrance = null;
        if (result != null) {
            room = dataProvider.getRoomByName(result);
            entrance = dataProvider.getEntranceByName(result);
        }
        if (room != null) {
            if (entrance != null) {
                startNavigation(entrance.getLongitude(), entrance.getLatitude(), room.getName());
            } else {
                startNavigation(room.getLongitude(), room.getLatitude(), room.getName());
            }

        }
    }

    @Override
    public void onResult(@NonNull Status status) {
        if (status.isSuccess()) {
            Toast.makeText(this, "Geofences Added", Toast.LENGTH_SHORT).show();
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            Toast.makeText(this, status.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        isSpinnerTouched = true;
        return false;
    }


    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String query = (String) parent.getItemAtPosition(position);
        result = query;
        floatingActionButton.setVisibility(View.VISIBLE);
        loadData(query);
        searchView.closeSearch();
    }

    @Override
    public void onMenuItemSelected(@IdRes int menuItemId) {
        switch (menuItemId) {
            case R.id.location_item:
                cardView.setVisibility(View.GONE);
                menuItem.setVisible(true);
                getSupportActionBar().setTitle("Search");
                break;
            case R.id.favorite_item:
                cardView.setVisibility(View.VISIBLE);
                searchView.closeSearch();
                menuItem.setVisible(false);
                getSupportActionBar().setTitle("Indoornavigation");
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        room = dataProvider.getRoomByName(query);
        result = query;
        floatingActionButton.setVisibility(View.VISIBLE);
        zoom(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //Do some magic
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        String s = enabled ? "enabled" : "disabled";
        Toast.makeText(MainActivity.this, "Search " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        startSearch(text.toString(), true, null, true);

    }

    @Override
    public void onButtonClicked(int i) {
        switch (i) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*for (Room room : dataProvider.getRoomObjects()) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(room.getLongitude(), room.getLatitude())).title(room.getName()));
        }*/
        map = googleMap;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(49.482534, 8.465205), 15));
    }

    @Override
    public void onSearchViewShown() {
        searchView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSearchViewClosed() {
        //Do some magic
        searchView.setVisibility(View.GONE);
    }


    public void populateGeofenceList(double longitude, double latitude, float radius, int durationInMiliseconds) {
        //latitude = 49.48316975;
        //longitude = 8.4636915;
        //Room room_sap = new Room("SAP Apphaus", 49.48316975, 8.4636915, 2);

        Log.d("Room longitude", String.valueOf(longitude));
        Log.d("Room latitude", String.valueOf(latitude));

        mGeofenceList.add(new Geofence.Builder().setRequestId("Key").setCircularRegion(latitude, longitude, radius).setExpirationDuration(
                durationInMiliseconds).setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT).build());

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(
                LocationServices.API).build();
    }


    public void startNavigation(final double longitude, final double latitude, final String locationName) {


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Navigation");
        builder.setMessage("Do you want to be navigated to the room?");

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // positive button logic


                populateGeofenceList(longitude, latitude, 200, 12 * 60 * 60 * 1000);
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(INDOORNAVIGATION_KEY,
                                                                                                   Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //IndoorNavigation indoor = dataProvider.getIndoornavigationByRoom(room.getName());
                editor.putString(ROOM_KEY, room.getName());
                editor.commit();
                addGeofencesButtonHandler();
                String urlAddress = "http://maps.google.com/maps?q=" + longitude + "," + latitude + "(" + locationName + ")&iwloc=A&hl=es";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress));

                startActivity(intent);
            }
        });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // negative button logic
            }
        });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();


    }

    public void loadData(final String query) {
        zoom(query);
    }

    public void zoom(String query) {
        map.clear();
        String roomName = dataProvider.getRoomByName(query).getName();
        if (dataProvider.getIndoornavigationByRoom(roomName) == null) {
            //No Indoornavigation ===> Show Room only
            double latitudeRoom = dataProvider.getRoomByName(query).getLatitude();
            double longitudeRoom = dataProvider.getRoomByName(query).getLongitude();
            map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_room_black_24dp)).position(
                    new LatLng(longitudeRoom, latitudeRoom)).title(
                    "Room " + roomName + " at" + dataProvider.getRoomByName(roomName).getFloor() + " Floor")).showInfoWindow();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(longitudeRoom, latitudeRoom), 17));
            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            map.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
        } else {
            //Has Indoornavigation ==> Show entrance and room!
            double latitudeRoom = dataProvider.getRoomByName(query).getLatitude();
            double longitudeRoom = dataProvider.getRoomByName(query).getLongitude();

            IndoorNavigation indorNav = dataProvider.getIndoornavigationByRoom(roomName);
            double latitudeEntrance = indorNav.getEntrance().getLatitude();
            double longitudeEntrance = indorNav.getEntrance().getLongitude();
            String entranceName = indorNav.getEntrance().getName();

            map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_room_black_24dp)).position(
                    new LatLng(longitudeRoom, latitudeRoom)).title(
                    "Room " + roomName + " at" + dataProvider.getRoomByName(roomName).getFloor() + " Floor")).showInfoWindow();

            /*map.addMarker(new MarkerOptions().icon(
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_arrow_drop_down_circle_black_24dp)).position(
                    new LatLng(longitudeEntrance, latitudeEntrance)).title("Entrance " + entranceName));*/

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(longitudeRoom, latitudeRoom), 17));
            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            map.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
        }


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("Google Api:", "connected");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    public void addGeofencesButtonHandler() {
        Log.d("MainActivity", "addGeofencesButtonHandler");
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, "Google API Client not connected!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            LocationServices.GeofencingApi.addGeofences(mGoogleApiClient, getGeofencingRequest(),
                                                        getGeofencePendingIntent()).setResultCallback(
                    this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
        }
    }

    private GeofencingRequest getGeofencingRequest() {
        Log.d("MainActivity", "getGeofencingRequest");
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        Log.d("MainActivity", "getGeofencePendingIntent");
        Intent intent = new Intent(this, GeoFenceTransitionIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling addgeoFences()
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
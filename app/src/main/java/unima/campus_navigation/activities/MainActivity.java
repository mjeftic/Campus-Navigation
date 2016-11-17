package unima.campus_navigation.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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
import unima.campus_navigation.model.IndoorNavigation;
import unima.campus_navigation.model.Room;
import unima.campus_navigation.service.ProvideMockDataServiceImpl;
import unima.campus_navigation.util.CustomSpinner;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnTouchListener, MaterialSearchBar.OnSearchActionListener, MaterialSearchView.SearchViewListener, MaterialSearchView.OnQueryTextListener, AdapterView.OnItemClickListener, OnMenuTabSelectedListener, View.OnClickListener {

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


    public static final String INDOORNAVIGATION_KEY = "INDOORNAVIGATION_KEY";
    public static final String ROOM_KEY             = "ROOM_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        spinner.setOnItemSelectedListener(new CustomSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isSpinnerTouched) {
                    Log.d("Spinner","clicked");
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
        });
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
    public void onClick(View v) {
        Room room = null;
        if (result != null) {
            room = dataProvider.getRoomByName(result);
        }
        if (room != null) {
            startNavigation(room.getLongitude(), room.getLatitude(), room.getName());
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
        Room room = dataProvider.getRoomByName(query);
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

    public void startNavigation(final double longitude, final double latitude, final String locationName) {


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Navigation");
        builder.setMessage("Do you want to be navigated to the room");

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // positive button logic
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

            map.addMarker(new MarkerOptions().icon(
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_arrow_drop_down_circle_black_24dp)).position(
                    new LatLng(longitudeEntrance, latitudeEntrance)).title("Entrance " + entranceName)).showInfoWindow();

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(longitudeEntrance, latitudeEntrance), 17));
            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomIn());
            // Zoom out to zoom level 10, animating with a duration of 2 seconds.
            map.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
        }


    }

}
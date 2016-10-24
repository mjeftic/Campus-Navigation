package unima.campus_navigation.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import unima.campus_navigation.model.Room;
import unima.campus_navigation.service.ProvideMockDataServiceImpl;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, MaterialSearchBar.OnSearchActionListener {

    CoordinatorLayout    coordinatorLayout;
    FloatingActionButton floatingActionButton;
    MaterialSearchView   searchView;
    Toolbar              toolbar;
    GoogleMap            map;
    SupportMapFragment   mapFragment;
    String                     result       = "";
    ProvideMockDataServiceImpl dataProvider = new ProvideMockDataServiceImpl();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
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
        });

        searchView.setVoiceSearch(true);

        searchView.setSubmitOnClick(true);
        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String query = (String) parent.getItemAtPosition(position);
                result = query;
                floatingActionButton.setVisibility(View.VISIBLE);
                loadData(query);
                searchView.closeSearch();
            }
        });


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                searchView.setVisibility(View.GONE);
            }
        });

        String[] array = new String[dataProvider.getRoomStrings().size()];

        searchView.setSuggestions(dataProvider.getRoomStrings().toArray(array));


        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_main);
        bottomBar.setItemsFromMenu(R.menu.menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                switch (itemId) {
                    case R.id.location_item:
                        //Snackbar.make(coordinatorLayout, "Location Item Selected", Snackbar.LENGTH_LONG).show();
                        break;
                    case R.id.favorite_item:
                        startActivity(new Intent(MainActivity.this, IndoorNavigationActivity.class));
                        //Snackbar.make(coordinatorLayout, "Favorite Item Selected", Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        });

        // Set the color for the active tab. Ignored on mobile when there are more than three tabs.
        bottomBar.setActiveTabColor("#FF4081");


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
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
        double latitudeRoom = dataProvider.getRoomByName(query).getLatitude();
        double longitudeRoom = dataProvider.getRoomByName(query).getLongitude();
        map.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_home_black_48dp)).position(
                new LatLng(longitudeRoom, latitudeRoom)).title(roomName)).showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(longitudeRoom, latitudeRoom), 15));
        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        map.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);


    }

}
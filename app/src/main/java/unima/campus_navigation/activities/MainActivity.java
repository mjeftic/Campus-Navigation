package unima.campus_navigation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import java.util.ArrayList;
import java.util.List;

import unima.campus_navigation.R;
import unima.campus_navigation.model.Room;

import static unima.campus_navigation.R.id.map;

public class MainActivity extends AppCompatActivity implements RoomProvider, OnMapReadyCallback, MaterialSearchBar.OnSearchActionListener{

	CoordinatorLayout coordinatorLayout;
    MaterialSearchView searchView;
    Toolbar toolbar;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        toolbar = (Toolbar)  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
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

        String[] array = new String[getRoomStrings().size()];

        searchView.setSuggestions(getRoomStrings().toArray(array));



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
						//Snackbar.make(coordinatorLayout, "Favorite Item Selected", Snackbar.LENGTH_LONG).show();
						break;
				}
			}
		});

		// Set the color for the active tab. Ignored on mobile when there are more than three tabs.
		bottomBar.setActiveTabColor("#FF4081");


		SupportMapFragment mapFragment =
				(SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
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
	protected void onDestroy() {
		super.onDestroy();
		//save last queries to disk
		//saveSearchSuggestionToDisk(searchBar.getLastSuggestions());
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
        switch (i){
            case MaterialSearchBar.BUTTON_NAVIGATION:
                break;
        }
    }

    @Override
	public void onMapReady(GoogleMap googleMap) {
        for(Room room : getRoomObjects()){
            googleMap.addMarker(new MarkerOptions().position(new LatLng(room.getLongitude(), room.getLatitude())).title(room.getName()));
        }
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(49.482534, 8.465205), 12));
	}

    @Override
    public List<Room> getRoomObjects() {
       return generateMockData();
    }

    @Override
    public List<String> getRoomStrings() {
        List<Room> rooms = generateMockData();
        List<String> roomNames = new ArrayList<>();
        for(Room room: rooms){
            roomNames.add(room.getName());
        }
        return roomNames;
    }


    public List<Room> generateMockData(){
        List<Room> rooms = new ArrayList<>();
        Room S108 = new Room("S108",49.481534, 8.465205);
        Room S107 = new Room("S107",49.482534, 8.466205);
        Room S106 = new Room("S106",49.482534, 8.464205);
        Room S105 = new Room("S105",49.482534, 8.463205);
        Room S104 = new Room("S104",49.482534, 8.462205);
        Room S103 = new Room("S103",49.484534, 8.465205);
        Room S102 = new Room("S102",49.483534, 8.465205);
        rooms.add(S102);
        rooms.add(S103);
        rooms.add(S104);
        rooms.add(S105);
        rooms.add(S106);
        rooms.add(S107);
        rooms.add(S108);
        return rooms;
    }

}

interface RoomProvider {
    List<Room> getRoomObjects();
    List<String> getRoomStrings();
}
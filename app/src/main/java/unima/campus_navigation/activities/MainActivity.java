package unima.campus_navigation.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import java.util.ArrayList;
import java.util.List;

import unima.campus_navigation.R;
import unima.campus_navigation.model.Room;

import static unima.campus_navigation.R.id.map;

public class MainActivity extends AppCompatActivity implements RoomProvider, OnMapReadyCallback, MaterialSearchBar.OnSearchActionListener{

	CoordinatorLayout coordinatorLayout;
	private MaterialSearchBar searchBar;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        List<String> lastSearches = getRoomStrings();


		searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
		searchBar.setHint("Search a room");
		//enable searchbar callbacks
		searchBar.setOnSearchActionListener(this);
		//restore last queries from disk
		searchBar.setLastSuggestions(lastSearches);



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
               // drawer.openDrawer(Gravity.LEFT);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
               // openVoiceRecognizer();
        }
    }

    @Override
	public void onMapReady(GoogleMap googleMap) {
		googleMap.addMarker(new MarkerOptions().position(new LatLng(49.482534, 8.465205)).title("Mannheim"));
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(49.482534, 8.465205), 16));
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
        Room S108 = new Room("S108",49.0,49.0);
        Room S107 = new Room("S107",49.0,49.0);
        Room S106 = new Room("S106",49.0,49.0);
        Room S105 = new Room("S105",49.0,49.0);
        Room S104 = new Room("S104",49.0,49.0);
        Room S103 = new Room("S103",49.0,49.0);
        Room S102 = new Room("S102",49.0,49.0);
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
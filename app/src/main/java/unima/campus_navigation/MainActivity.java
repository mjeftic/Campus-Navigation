package unima.campus_navigation;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

public class MainActivity extends AppCompatActivity {

	CoordinatorLayout coordinatorLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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


	}

}

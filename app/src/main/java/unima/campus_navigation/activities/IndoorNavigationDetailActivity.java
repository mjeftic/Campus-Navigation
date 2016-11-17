package unima.campus_navigation.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.StackTransformer;

import unima.campus_navigation.R;
import unima.campus_navigation.service.ProvideMockDataServiceImpl;
import unima.campus_navigation.util.ViewPagerAdapter;

import static unima.campus_navigation.R.id.pager;

/**
 * Created by Aizpea on 21/10/2016.
 */

public class IndoorNavigationDetailActivity extends AppCompatActivity {
    private static ViewPager mPager;
    private        TextView  bubbleField;
    private        Context   ctx;
    private        Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoornavigation_detail);
        ctx = getApplicationContext();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_navigation_close_inverted);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void init() {
        String selectedRoom = getIndoornavigationToOpen();
        if (selectedRoom != null) {
            getSupportActionBar().setTitle(selectedRoom);
            ProvideMockDataServiceImpl data = new ProvideMockDataServiceImpl();
            mPager = (ViewPager) findViewById(pager);
            bubbleField = (TextView) findViewById(R.id.textView2);
            mPager.setAdapter(
                    new ViewPagerAdapter(IndoorNavigationDetailActivity.this, data.getIndoornavigationByRoom(selectedRoom).getImagePaths(),
                                         data.getIndoornavigationByRoom(selectedRoom).getTextBubbles()));
            mPager.setPageTransformer(true, new StackTransformer());

        } else {
            finish();
            Toast.makeText(getApplicationContext(),"Room was not found", Toast.LENGTH_SHORT).show();
        }
    }

    private String getIndoornavigationToOpen() {
        SharedPreferences sharedPref = ctx.getSharedPreferences(MainActivity.INDOORNAVIGATION_KEY, Context.MODE_PRIVATE);
        return sharedPref.getString(MainActivity.ROOM_KEY, null);
    }

}


package unima.campus_navigation.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unima.campus_navigation.R;
import unima.campus_navigation.service.ProvideMockDataServiceImpl;
import unima.campus_navigation.util.ViewPagerAdapter;

/**
 * Created by Aizpea on 21/10/2016.
 */

public class IndoorNavigationActivity extends AppCompatActivity {
    private static ViewPager mPager;

    private static       int                currentPage  = 0;
    private static       int                NUM_PAGES    = 0;
    private static final ArrayList<Integer> roomImages   = null;
    private static final ArrayList<Integer> imageNumbers = null;
    //private static boolean stairs =false;
    private static final List<Integer>      textNumbers  = new ArrayList<Integer>();
    private TextView     bubbleField;
    private List<String> bubbleStrings;

    //SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
    private String selectedRoom;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoornavigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        ProvideMockDataServiceImpl data = new ProvideMockDataServiceImpl();
        mPager = (ViewPager) findViewById(R.id.pager);
        bubbleField = (TextView) findViewById(R.id.textView2);
        //FIXME dynamic indoor navigations
        mPager.setAdapter(new ViewPagerAdapter(IndoorNavigationActivity.this, data.getIndoornavigationByRoom("O145").getImagePaths(),
                                               data.getIndoornavigationByRoom("O145").getTextBubbles()));
    }

}


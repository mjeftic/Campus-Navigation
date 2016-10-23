package unima.campus_navigation.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import unima.campus_navigation.R;
import unima.campus_navigation.service.ProvideMockDataServiceImpl;
import unima.campus_navigation.util.ViewPagerAdapter;

/**
 * Created by Aizpea on 21/10/2016.
 */

public class IndoorNavigationActivity extends AppCompatActivity{
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES= {R.drawable.ic_action_action_search,R.drawable.ic_action_navigation_arrow_back};
    private TextView bubbleField;
    private List<String> bubbleStrings;

    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indoornavigation);
        init();
    }
    private void init() {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);


        bubbleField = (TextView) findViewById(R.id.textView2);

        ProvideMockDataServiceImpl data = new ProvideMockDataServiceImpl();
        bubbleStrings = data.getBubbleStrings();
        mPager.setAdapter(new ViewPagerAdapter(IndoorNavigationActivity.this,ImagesArray,bubbleStrings));



        // CirclePageIndicator indicator = (CirclePageIndicator)
        //        findViewById(R.id.indicator);

       // indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
       // indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

    }
}


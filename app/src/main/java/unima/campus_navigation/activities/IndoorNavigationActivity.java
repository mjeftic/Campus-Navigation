package unima.campus_navigation.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
    private static final List<Integer> roomImages= new ArrayList<Integer>();
    private static final List<Integer> imageNumbers= new ArrayList<Integer>();
    private static boolean stairs =false;
    private static final List<Integer> textNumbers= new ArrayList<Integer>();
    private static final Integer[] IMAGES = {R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5,R.drawable.image6,R.drawable.image7};
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
        AlertDialog.Builder builder = new AlertDialog.Builder(IndoorNavigationActivity.this);
        builder.setTitle("Do you want to avoid the stairs?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                /*TODO avoid stairs
                * get images and text numbers list
                * */
                imageNumbers.clear();
                textNumbers.clear();

                imageNumbers.add(0);
                imageNumbers.add(1);
                imageNumbers.add(2);
                textNumbers.add(0);
                textNumbers.add(1);
                textNumbers.add(2);
                dialog.dismiss();
                launchPicture();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO not avoid the stairs
                imageNumbers.clear();
                textNumbers.clear();
                imageNumbers.add(3);
                imageNumbers.add(4);
                imageNumbers.add(5);
                textNumbers.add(3);
                textNumbers.add(4);
                textNumbers.add(5);

                dialog.dismiss();
                launchPicture();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void launchPicture() {
        //createAndShowAlertDialog();

        ImagesArray.clear();

        for(int i = 0 ; i<imageNumbers.size();i++)
            ImagesArray.add(IMAGES[imageNumbers.get(i)]);

        ProvideMockDataServiceImpl data = new ProvideMockDataServiceImpl();
        bubbleStrings = new ArrayList<String>();

        List<String> roomStrings  = data.getBubbleStrings();
        for(int i = 0; i<textNumbers.size();i++)
            bubbleStrings.add(roomStrings.get(i));

        for(int i = 0; i< roomImages.size(); i++)
            ImagesArray.add(roomImages.get(i));

        mPager = (ViewPager) findViewById(R.id.pager);

        bubbleField = (TextView) findViewById(R.id.textView2);

        mPager.setAdapter(new ViewPagerAdapter(IndoorNavigationActivity.this,ImagesArray,bubbleStrings));



        // CirclePageIndicator indicator = (CirclePageIndicator)
        //        findViewById(R.id.indicator);

       // indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
       // indicator.setRadius(5 * density);

        NUM_PAGES = roomImages.size();

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


package unima.campus_navigation.activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unima.campus_navigation.R;
import unima.campus_navigation.model.Room;
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
    //private static boolean stairs =false;
    private static final List<Integer> textNumbers= new ArrayList<Integer>();
    private static Integer[] IMAGES = {};
    private TextView bubbleField;
    private List<String> bubbleStrings;

    /////
    //SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
    private String selectedRoom;

    private boolean AVOIDSTAIRS;
    /////

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
                textNumbers.add(0);

                dialog.dismiss();
                launchPicture();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO not avoid the stairs
                imageNumbers.clear();
                textNumbers.clear();
                //
                SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                selectedRoom = prefs.getString("room", null);
                System.out.println(selectedRoom);

                AVOIDSTAIRS=false;

                if (selectedRoom.equals("O145")) {
                    imageNumbers.add(0);
                    imageNumbers.add(1);
                    imageNumbers.add(2);
                    imageNumbers.add(3);
                    imageNumbers.add(4);
                    imageNumbers.add(5);
                    imageNumbers.add(6);
                    imageNumbers.add(7);
                    imageNumbers.add(8);
                    imageNumbers.add(9);

                    textNumbers.add(0);
                    textNumbers.add(1);
                    textNumbers.add(2);
                    textNumbers.add(3);
                    textNumbers.add(4);
                    textNumbers.add(5);
                    textNumbers.add(6);
                    textNumbers.add(7);
                    textNumbers.add(8);
                    textNumbers.add(9);

                    dialog.dismiss();
                    launchPicture();
                }else if(selectedRoom.equals("Röchling Hörsaal")){
                    imageNumbers.add(0);
                    imageNumbers.add(1);
                    imageNumbers.add(2);
                    imageNumbers.add(3);
                    imageNumbers.add(4);
                    imageNumbers.add(5);
                    textNumbers.add(0);
                    textNumbers.add(1);
                    textNumbers.add(2);
                    textNumbers.add(3);
                    textNumbers.add(4);
                    textNumbers.add(5);
                    dialog.dismiss();
                    launchPicture();
                }else{
                    System.out.println("Error");
                }
                //

                /*dialog.dismiss();
                launchPicture();*/
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void launchPicture() {
        //createAndShowAlertDialog();

        ImagesArray.clear();

        ProvideMockDataServiceImpl data = new ProvideMockDataServiceImpl();
        bubbleStrings = new ArrayList<String>();

        IMAGES  = data.generateMockDataImages(selectedRoom);

        for(int i = 0 ; i<imageNumbers.size();i++)
            ImagesArray.add(IMAGES[imageNumbers.get(i)]);


        if (AVOIDSTAIRS==false) {
            List<String> roomStrings = data.getBubbleStrings(selectedRoom);
            for (int i = 0; i < textNumbers.size(); i++)
                bubbleStrings.add(roomStrings.get(i));

            for (int i = 0; i < roomImages.size(); i++)
                ImagesArray.add(roomImages.get(i));
        }else{
            List<String> roomStrings = data.getBubbleStringsNoStairs(selectedRoom);
            for (int i = 0; i < textNumbers.size(); i++)
                bubbleStrings.add(roomStrings.get(i));

            for (int i = 0; i < roomImages.size(); i++)
                ImagesArray.add(roomImages.get(i));
        }

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


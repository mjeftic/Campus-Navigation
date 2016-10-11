package unima.campus_navigation;

/**
 * Created by Aizpea on 08/10/2016.
 */

        import android.app.Activity;
        import android.graphics.Typeface;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemSelectedListener;
        import android.widget.LinearLayout;
        import android.widget.TextView;
public class ImageActivity extends Activity
{
    LinearLayout count_layout;
    TextView page_text[];
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_main);
        Gallery_Activity mGallery = (Gallery_Activity) findViewById(R.id.mygallery01);


       mGallery.setAdapter(new ImageAdapter(this));
        mGallery.setScrollingEnabled(true);
        count_layout = (LinearLayout) findViewById(R.id.image_count);
        count = mGallery.getAdapter().getCount();
        System.out.println("Gallery Image Count======>>>" + count);
        page_text = new TextView[count];
        for (int i = 0; i < count; i++)
        {
            page_text[i] = new TextView(this);
            page_text[i].setText(".");
            page_text[i].setTextSize(45);
            page_text[i].setTypeface(null, Typeface.BOLD);
            page_text[i].setTextColor(android.graphics.Color.GRAY);
            count_layout.addView(page_text[i]);
        }
        mGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // TODO Auto-generated method stub
                System.out.println("Item Selected Position=======>>>" + pos);
                for (int i = 0; i < count; i++) {
                    page_text[i]
                            .setTextColor(android.graphics.Color.GRAY);
                }
                page_text[pos]
                        .setTextColor(android.graphics.Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}

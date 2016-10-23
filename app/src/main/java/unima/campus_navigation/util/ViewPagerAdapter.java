package unima.campus_navigation.util;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unima.campus_navigation.R;

/**
 * Created by Aizpea on 21/10/2016.
 */

public class ViewPagerAdapter extends PagerAdapter{
    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;

    private List<String> TEXT;



    public ViewPagerAdapter(Context context,ArrayList<Integer> IMAGES, List<String> TEXT) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
        this.TEXT=TEXT;
    }




    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);


        imageView.setImageResource(IMAGES.get(position));

        view.addView(imageLayout, 0);
        final TextView textView = (TextView)  imageLayout.findViewById(R.id.textView2);
        textView.setText(TEXT.get(position));
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}

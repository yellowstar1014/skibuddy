package cmpe277.skibuddy.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cmpe277.skibuddy.HomeFragment;
import cmpe277.skibuddy.ProfileFragment;
import cmpe277.skibuddy.R;
import cmpe277.skibuddy.SessionFragment;

/**
 * @author yishafang on 12/1/15.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;

    private String tabTitles[] = new String[] { "New Session", "Events", "Profile"};
    private int imageResId[] = {
            R.drawable.ski,
            R.drawable.events,
            R.drawable.user,
    };

    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);

        ImageView img = (ImageView) v.findViewById(R.id.imgView);
        img.setImageResource(imageResId[position]);

        TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText(tabTitles[position]);
        tv.setTextSize(13);

        return v;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return HomeFragment.newInstance(position + 1);
        } else if (position == 1) {
            return SessionFragment.newInstance(position + 1);
        } else  {
            return ProfileFragment.newInstance(position + 1);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, 50, 50);

        // Replace blank spaces with image icon
        SpannableString spannableString = new SpannableString("   " + tabTitles[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}

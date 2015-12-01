package cmpe277.skibuddy;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author yishafang on 12/1/15.
 */
public class HomeFragment extends Fragment{
    private static final String TAG = HomeFragment.class.getSimpleName();

    public static HomeFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(TAG, page);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }
}

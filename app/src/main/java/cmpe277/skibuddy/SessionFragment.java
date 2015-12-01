package cmpe277.skibuddy;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author yishafang on 12/1/15.
 */
public class SessionFragment extends Fragment{
    private static final String TAG = SessionFragment.class.getSimpleName();

    public static SessionFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(TAG, page);
        SessionFragment sessionFragment = new SessionFragment();
        sessionFragment.setArguments(args);
        return sessionFragment;
    }
}

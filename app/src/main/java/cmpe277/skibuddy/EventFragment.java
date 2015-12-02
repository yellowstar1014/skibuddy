package cmpe277.skibuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EventFragment extends Fragment{
    private static final String TAG = EventFragment.class.getSimpleName();
    private FloatingActionButton add;

    public static EventFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(TAG, page);
        EventFragment eventFragment = new EventFragment();
        eventFragment.setArguments(args);
        return eventFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container, false);

        add = (FloatingActionButton) v.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CreateEventActivity.class);
                startActivity(i);

            }

        });

            return v;
    }

}

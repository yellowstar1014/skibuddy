package cmpe277.skibuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cmpe277.skibuddy.Adapter.UserEventAdapter;
import cmpe277.skibuddy.model.Event;
import cmpe277.skibuddy.model.UserEventWithStatus;

import static cmpe277.skibuddy.Constants.EVENT_ID;
import static cmpe277.skibuddy.Constants.EVENT_START;
import static cmpe277.skibuddy.Constants.EVENT_END;
import static cmpe277.skibuddy.Constants.EVENT_NAME;


public class EventFragment extends Fragment{
    private static final String TAG = EventFragment.class.getSimpleName();
    private FloatingActionButton add;

    private ListView ownedEvent;
    private ListView acceptedEvent;
    private ListView waitingEvent;

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

        ownedEvent = (ListView) v.findViewById(R.id.list_view_owned);
        acceptedEvent = (ListView) v.findViewById(R.id.list_view_accepted);
        waitingEvent = (ListView) v.findViewById(R.id.list_view_waiting);

        populateEvents();

        return v;
    }

    private void populateEvents() {
        // START Hard code for testing
        UserEventWithStatus userEventWithStatus1 = new UserEventWithStatus();
        Event event1 = new Event();
        event1.setTitle("Winter kickout!");
        event1.setStartTime(Long.parseLong("11112015"));
        event1.setEndTime(Long.parseLong("12222015"));
        userEventWithStatus1.setEvent(event1);
        userEventWithStatus1.setStatus(0);

        UserEventWithStatus userEventWithStatus2 = new UserEventWithStatus();
        Event event2 = new Event();
        event2.setTitle("Let's go skiing!");
        event2.setStartTime(Long.parseLong("10232015"));
        event2.setEndTime(Long.parseLong("12092015"));
        userEventWithStatus2.setEvent(event2);
        userEventWithStatus2.setStatus(1);

        UserEventWithStatus userEventWithStatus3 = new UserEventWithStatus();
        Event event3 = new Event();
        event3.setTitle("WoooooooW!!");
        event3.setStartTime(Long.parseLong("01042016"));
        event3.setEndTime(Long.parseLong("02032016"));
        userEventWithStatus3.setEvent(event3);
        userEventWithStatus3.setStatus(2);


        UserEventWithStatus userEventWithStatus4 = new UserEventWithStatus();
        Event event4 = new Event();
        event4.setTitle("Kirkwood");
        event4.setStartTime(Long.parseLong("02122016"));
        event4.setEndTime(Long.parseLong("03212016"));
        userEventWithStatus4.setEvent(event4);
        userEventWithStatus4.setStatus(2);

        ArrayList<UserEventWithStatus> lists = new ArrayList<>();
        lists.add(userEventWithStatus1);
        lists.add(userEventWithStatus2);
        lists.add(userEventWithStatus3);
        lists.add(userEventWithStatus4);
        // END Hard code for testing

        // 把不同status的event放到不同的list里面
        final ArrayList<UserEventWithStatus> owned = new ArrayList<>();
        final ArrayList<UserEventWithStatus> accepted = new ArrayList<>();
        final ArrayList<UserEventWithStatus> waiting = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).getStatus() == 0) {
                owned.add(lists.get(i));
            } else if (lists.get(i).getStatus() == 1) {
                waiting.add(lists.get(i));
            } else if (lists.get(i).getStatus() == 2) {
                accepted.add(lists.get(i));
            }
        }

        UserEventAdapter userEventAdapterOwned = new UserEventAdapter(getContext(), owned);
        ownedEvent.setAdapter(userEventAdapterOwned);
        ownedEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startEventDetailActivity(owned, position);
            }
        });

        UserEventAdapter userEventAdapterAccepted = new UserEventAdapter(getContext(), accepted);
        acceptedEvent.setAdapter(userEventAdapterAccepted);
        acceptedEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startEventDetailActivity(accepted, position);
            }
        });

        UserEventAdapter userEventAdapterWaiting = new UserEventAdapter(getContext(), waiting);
        waitingEvent.setAdapter(userEventAdapterWaiting);
        waitingEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startEventDetailActivity(waiting, position);
            }
        });

    }

    private void startEventDetailActivity(ArrayList<UserEventWithStatus> list, int position) {
        String eventId = String.valueOf(list.get(position).getEvent().getId());
        String eventName = list.get(position).getEvent().getTitle();
        Long eventStart = list.get(position).getEvent().getStartTime();
        Long eventEnd = list.get(position).getEvent().getEndTime();
        Intent intent = new Intent(getContext(), EventDetailActivity.class);
        intent.putExtra(EVENT_START, String.valueOf(eventStart));
        intent.putExtra(EVENT_END, String.valueOf(eventEnd));
        intent.putExtra(EVENT_ID, eventId);
        intent.putExtra(EVENT_NAME, eventName);
        startActivity(intent);
    }
}

package cmpe277.skibuddy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cmpe277.skibuddy.R;
import cmpe277.skibuddy.model.UserEventWithStatus;

/**
 * @author yishafang on 12/3/15.
 */
public class UserEventAdapter extends ArrayAdapter<UserEventWithStatus> {

    public UserEventAdapter(Context context, ArrayList<UserEventWithStatus> userEventWithStatuses) {
        super(context, 0, userEventWithStatuses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserEventWithStatus userEventWithStatus = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_event_item_list_view, parent, false);
        }

        TextView eventTitle = (TextView) convertView.findViewById(R.id.event_title);
        TextView eventStatus = (TextView) convertView.findViewById(R.id.event_status);

        eventTitle.setText(userEventWithStatus.getEvent().getTitle());
        eventStatus.setText(convertStatus(userEventWithStatus.getStatus()));

        return convertView;
    }

    private String convertStatus(int status) {
        if (status == 0) {
            return "Owned";
        } else if (status == 1) {
            return "Waiting";
        } else if (status == 2) {
            return "Accepted";
        }

        return "Other";
    }

}

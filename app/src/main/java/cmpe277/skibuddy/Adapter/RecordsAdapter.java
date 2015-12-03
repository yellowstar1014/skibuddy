package cmpe277.skibuddy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import cmpe277.skibuddy.R;
import cmpe277.skibuddy.model.Record;

/**
 * @author yishafang on 12/2/15.
 */
public class RecordsAdapter extends ArrayAdapter<Record> {

    public RecordsAdapter(Context context, ArrayList<Record> records) {
        super(context, 0, records);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Record record = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.record_list_view, parent, false);
        }

        TextView startTime = (TextView) convertView.findViewById(R.id.start_time);
        TextView endTime = (TextView) convertView.findViewById(R.id.end_time);
        TextView distance = (TextView) convertView.findViewById(R.id.distance);

        startTime.setText(convertEpochTimeToDate(record.getStartTime()));
        endTime.setText(convertEpochTimeToDate(record.getEndTime()));
        distance.setText(String.valueOf(record.getDistance()) + " m");

        return convertView;
    }

    private String convertEpochTimeToDate(long epoch) {
        Date date = new Date(epoch);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        return format.format(date);
    }
}

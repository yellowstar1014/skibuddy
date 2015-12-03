package cmpe277.skibuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static cmpe277.skibuddy.Constants.EVENT_NAME;

public class EventDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Intent intent = getIntent();
        String name = intent.getStringExtra(EVENT_NAME);

        TextView eventName = (TextView) findViewById(R.id.eventName);
        eventName.setText(name);


    }
}

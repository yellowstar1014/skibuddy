package cmpe277.skibuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cmpe277.skibuddy.Adapter.RecordsAdapter;
import cmpe277.skibuddy.model.Record;

import static cmpe277.skibuddy.Constants.EVENT_NAME;
import static cmpe277.skibuddy.Constants.PERSON_EMAIL;
import static cmpe277.skibuddy.Constants.PERSON_ID;
import static cmpe277.skibuddy.Constants.PERSON_NAME;
import static cmpe277.skibuddy.Constants.RECORD_ID;

public class OtherUserProfileActivity extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private ImageView photo;
    private ListView listView;

    private String personName;
    private String personEmail;
    private String personPhotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        photo = (ImageView) findViewById(R.id.photo);
        listView = (ListView) findViewById(R.id.list_view);

        Intent intent = getIntent();

        name.setText(intent.getStringExtra(PERSON_NAME));
        email.setText(intent.getStringExtra(PERSON_EMAIL));
        loadRecordData();
    }

    private void loadRecordData() {
        // TODO: hard code user id for testing only, should get it from server
        final int userId = 1;

        // START Hard code for testing
        Record r1 = new Record();
        r1.setStartTime(1400);
        r1.setEndTime(1500);
        r1.setDistance(50.01);
        Record r2 = new Record();
        r2.setStartTime(2200);
        r2.setEndTime(2400);
        r2.setDistance(360.09);
        final ArrayList<Record> records = new ArrayList<>();
        records.add(r1);
        records.add(r2);

        RecordsAdapter recordsAdapter = new RecordsAdapter(this, records);
        listView.setAdapter(recordsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String recordId = String.valueOf(records.get(position));
                Intent intent = new Intent(OtherUserProfileActivity.this, RecordDetailActivity.class);
                intent.putExtra(RECORD_ID, recordId);
                intent.putExtra(PERSON_ID, userId);
                startActivity(intent);
            }
        });
    }
}

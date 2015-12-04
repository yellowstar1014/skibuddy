package cmpe277.skibuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static cmpe277.skibuddy.Constants.EVENT_NAME;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import cmpe277.skibuddy.Adapter.MembersAdapter;
import cmpe277.skibuddy.model.User;
import static cmpe277.skibuddy.Constants.PERSON_EMAIL;
import static cmpe277.skibuddy.Constants.PERSON_NAME;
import static cmpe277.skibuddy.Constants.EVENT_START;
import static cmpe277.skibuddy.Constants.EVENT_END;


public class EventDetailActivity extends AppCompatActivity {
    private FloatingActionButton invite;
    private EditText email;
    private ListView members;
    private MembersAdapter membersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Intent intent = getIntent();
        String name = intent.getStringExtra(EVENT_NAME);
        String start = intent.getStringExtra(EVENT_START);
        String end = intent.getStringExtra(EVENT_END);
        TextView eventName = (TextView) findViewById(R.id.eventName);
        TextView eventStart = (TextView) findViewById(R.id.start2);
        TextView eventEnd = (TextView) findViewById(R.id.end2);
        eventName.setText(name);
        eventStart.setText(start);
        eventEnd.setText(end);


        email = (EditText) findViewById(R.id.email);
        members = (ListView) findViewById(R.id.members);
        invite = (FloatingActionButton) findViewById(R.id.add);
        loadMembersData();
        invite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


            }

        });
    }
//TODO: dynimicly add member to the event
//    public void addItems(View v) {
//        members.("Clicked : " + clickCounter++);
//        membersAdapter.notifyDataSetChanged();
//    }

    private void loadMembersData() {
        // TODO: hard code memeber list
        final int userId = 7;

        // START Hard code for testing
        User u1 = new User();
        u1.setName("Neil");
        u1.setEmail("neil.zhang@sjsu.edu");

        User u2 = new User();
        u2.setName("Kelly");
        u2.setEmail("kelly.zhao@sjsu.edu");


        final ArrayList<User> users = new ArrayList<>();
        users.add(u1);
        users.add(u2);

        membersAdapter = new MembersAdapter(getApplicationContext(), users);
        members.setAdapter(membersAdapter);
        members.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String recordId = String.valueOf(users.get(position));
                Intent intent = new Intent(getApplicationContext(), RecordDetailActivity.class);
//                intent.putExtra(PERSON_ID, users.get(position).getId());
                intent.putExtra(PERSON_EMAIL, users.get(position).getEmail());
                intent.putExtra(PERSON_NAME, users.get(position).getName());
                startActivity(intent);
            }
        });

    }


}

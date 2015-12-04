package cmpe277.skibuddy;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cmpe277.skibuddy.model.Event;

public class CreateEventActivity extends AppCompatActivity {
    private Button create;
    private EditText name;
    private EditText description;
    private EditText start;
    private EditText end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);


        create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {

            //TODO: add new event to event list in eventFragment
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateEventActivity.this, EventDetailActivity.class);
                startActivity(i);

            }

        });

    }
}

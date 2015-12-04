package cmpe277.skibuddy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import cmpe277.skibuddy.R;
import cmpe277.skibuddy.model.User;


public class MembersAdapter extends ArrayAdapter<User> {

    public MembersAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.member_list_view, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView email = (TextView) convertView.findViewById(R.id.email);
        name.setText(user.getName());
        email.setText(user.getEmail());
        return convertView;
    }

}

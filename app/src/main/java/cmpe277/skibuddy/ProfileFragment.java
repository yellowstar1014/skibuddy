package cmpe277.skibuddy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cmpe277.skibuddy.Adapter.RecordsAdapter;
import cmpe277.skibuddy.model.Record;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static cmpe277.skibuddy.Constants.PERSON_EMAIL;
import static cmpe277.skibuddy.Constants.PERSON_ID;
import static cmpe277.skibuddy.Constants.PERSON_NAME;
import static cmpe277.skibuddy.Constants.PERSON_PHOTO_URL;
import static cmpe277.skibuddy.Constants.RECORD_ID;

/**
 * @author yishafang on 12/1/15.
 */
public class ProfileFragment extends Fragment {
    private static final String TAG = ProfileFragment.class.getSimpleName();

    private TextView name;
    private TextView email;
    private ImageView photo;
    private ListView listView;

    private String personName;
    private String personEmail;
    private String personPhotoUrl;

    public static ProfileFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(TAG, page);
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(args);
        return profileFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ServiceFactory.init(getString(R.string.server_url));

        Intent intent = getActivity().getIntent();
        personName = intent.getStringExtra(PERSON_NAME);
        personEmail = intent.getStringExtra(PERSON_EMAIL);
        personPhotoUrl = intent.getStringExtra(PERSON_PHOTO_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        name = (TextView) v.findViewById(R.id.name);
        email = (TextView) v.findViewById(R.id.email);
        photo = (ImageView) v.findViewById(R.id.photo);
        listView = (ListView) v.findViewById(R.id.list_view);

        name.setText(personName);
        email.setText(personEmail);

        // If the person doesn't have google profile image, we give him a default avatar
//        if (personPhotoUrl.equals("null")) {
//            photo.setBackgroundResource(R.drawable.avatar);
//            photo.getLayoutParams().height = 350;
//            photo.getLayoutParams().width = 350;
//        } else {
//            download(personPhotoUrl, photo);
//        }
//
        loadRecordData();

        return v;
    }

    public void download(String url, ImageView imageView) {
        BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
        task.execute(url);
    }

    private void loadRecordData() {
        // TODO: hard code user id for testing only, should get it from server
        final int userId = 7;

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

        RecordsAdapter recordsAdapter = new RecordsAdapter(getContext(), records);
        listView.setAdapter(recordsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String recordId = String.valueOf(records.get(position));
                Intent intent = new Intent(getContext(), RecordDetailActivity.class);
                intent.putExtra(RECORD_ID, recordId);
                intent.putExtra(PERSON_ID, userId);
                startActivity(intent);
            }
        });
        // END Hard code for testing

        ServerAPI serverAPI = ServiceFactory.createService(ServerAPI.class);
        Call<List<Record>> call = serverAPI.getRecord(userId);
        call.enqueue(new Callback<List<Record>>() {
            @Override
            public void onResponse(Response<List<Record>> response, Retrofit retrofit) {
                Log.w(TAG, "Success getting records!!!");
                if (response.isSuccess()) {
                    final ArrayList<Record> records = new ArrayList<>();
                    for (Record record : response.body()) {
                        records.add(record);
                    }

                    RecordsAdapter recordsAdapter = new RecordsAdapter(getContext(), records);
                    listView.setAdapter(recordsAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String recordId = String.valueOf(records.get(position));
                            Intent intent = new Intent(getContext(), RecordDetailActivity.class);
                            intent.putExtra(RECORD_ID, recordId);
                            intent.putExtra(PERSON_ID, userId);
                            startActivity(intent);
                        }
                    });
                } else {
                    // error response, no access to resource?
                    Log.w(TAG, "response is not success!");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "error:" + t.getMessage());
            }
        });
    }


    private class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        //private String url;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<>(imageView);
        }

        @Override
        // Actual download method, run in the task thread
        protected Bitmap doInBackground(String... params) {
            // params comes from the execute() call: params[0] is the url.
            return downloadBitmap(params[0]);
        }

        @Override
        // Once the image is downloaded, associates it to the imageView
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    static Bitmap downloadBitmap(String url) {
        final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
        final HttpGet getRequest = new HttpGet(url);

        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            // Could provide a more explicit error message for IOException or IllegalStateException
            getRequest.abort();
            Log.w("ImageDownloader", "Error while retrieving bitmap from " + url, e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }
}

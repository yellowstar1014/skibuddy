package cmpe277.skibuddy;

import cmpe277.skibuddy.model.Identity;
import cmpe277.skibuddy.model.Record;
import cmpe277.skibuddy.model.User;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by yellowstar on 11/22/15.
 */
public interface ServerAPI {
    @POST("/authenticate")
    Call<User> authenticate(@Body Identity identity);

    @POST("/users/{uid}/records")
    Call<Record> addRecord(@Path("uid") int uid, @Body Record record);
}

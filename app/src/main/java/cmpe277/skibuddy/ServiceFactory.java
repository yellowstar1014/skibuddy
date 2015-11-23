package cmpe277.skibuddy;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import cmpe277.skibuddy.exception.NoBaseUrlException;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by yellowstar on 11/22/15.
 */
public class ServiceFactory {
    private static String server_base_url;
    private static OkHttpClient httpClient = new OkHttpClient();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create());
    public static void init(String baseUrl) {
        server_base_url = baseUrl;
        builder.baseUrl(baseUrl);
    }

    public static <S> S createService(Class<S> serviceClass) {
        if (server_base_url == null) throw new NoBaseUrlException("No Base Url");
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String baseUrl) {
        if (baseUrl != null) {
            builder.baseUrl(baseUrl);
        } else {
            if (server_base_url == null) throw new NoBaseUrlException("No Base Url");
        }
        /*if (authToken != null) {
            httpClient.interceptors().clear();
            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", authToken)
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }*/
        Retrofit retrofit = builder.client(httpClient).build();
        builder.baseUrl(server_base_url);
        return retrofit.create(serviceClass);
    }
}

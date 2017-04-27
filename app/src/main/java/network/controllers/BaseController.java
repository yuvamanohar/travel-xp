package network.controllers;

import android.app.Activity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import config.AppConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import util.SLogger;

/**
 * Created by yuva on 22/4/17.
 */

public abstract class BaseController {
    protected Activity activity ;

    public BaseController(Activity activity) {
        this.activity = activity ;
    }

    public Retrofit getClient() {
        if(AppConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            return new Retrofit.Builder()
                    .baseUrl(AppConfig.SERVER_BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            return new Retrofit.Builder()
                    .baseUrl(AppConfig.SERVER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static class NetworkErrorObj {
        public final String errorMessage ;

        private NetworkErrorObj(String errorMessage) {
            this.errorMessage = errorMessage ;
        }

        public static NetworkErrorObj get(String errorMessage) {
            return new NetworkErrorObj(errorMessage) ;
        }
    }

    public static class ErrorMessage {
        public static String MALFORMED_URI = "Selected File not readable." ;
        public static String USER_DATA_SERVER_ERROR = "Server error while fetching user data" ;
        public static String POST_CONTENT_SERVER_ERROR = "Post failed. Please try again" ;
        public static String FEED_SERVER_ERROR = "Servers need a vacation. Please try again" ;
        public static String INTERNET_ERROR = "No internet connectivity. Please enable internet and try again !" ;
    }

    public abstract static class ResponseHandler<T> implements Callback<T> {

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if(response.isSuccessful()) {
                this.onSuccess(call, response) ;
            } else {
                try {
                    onRequestFailure(call, response);
                    String error = response.errorBody().string();
                    //TODO - parse error and show appropriate error
                    SLogger.BOOTSTRAP.d(error);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            t.printStackTrace();
            SLogger.BOOTSTRAP.d("Bootstrap Failed : " + t.getMessage());
            //TODO - parse t.getMessage() and show appropriate error
            EventBus.getDefault().post(NetworkErrorObj.get(ErrorMessage.INTERNET_ERROR));
        }

        public abstract void onSuccess(Call<T> call, Response<T> response)  ;
        public abstract void onRequestFailure(Call<T> call, Response<T> response)  ;
    }
}

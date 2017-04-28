package network.framework;

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
import static network.framework.NetworkErrorObj.ErrorMessage ;

/**
 * Created by yuva on 22/4/17.
 */

public abstract class BaseController<T> {
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

    public abstract String getRequestFailureErrorMessage()  ;

    public void enqueue(Call<T> call) {
        call.enqueue(new ResponseHandler<T>() {});
    }

    public abstract class ResponseHandler<T> implements Callback<T> {

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if(response.isSuccessful()) {
                EventBus.getDefault().post(response.body());
            } else {
                try {
                    EventBus.getDefault().post(NetworkErrorObj.get(call, BaseController.this.getRequestFailureErrorMessage()));
                    String error = response.errorBody().string();
                    //TODO - parse error and show appropriate error
                    SLogger.BOOTSTRAP.e(error);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            t.printStackTrace();
            SLogger.BOOTSTRAP.e("Bootstrap Failed : " + t.getMessage());

            String errorMessage = t.getMessage() ;

            //TODO - parse t.getMessage() and show appropriate error
            if(errorMessage.contains("Failed to connect")) {
                EventBus.getDefault().post(NetworkErrorObj.get(call, ErrorMessage.SERVER_CONNECT_ERROR));
            } else {
                EventBus.getDefault().post(NetworkErrorObj.get(call, ErrorMessage.INTERNET_ERROR));
            }

        }
    }
}

package network.controllers;

import android.app.Activity;

import config.AppConfig;
import network.INetworkListener;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yuva on 22/4/17.
 */

public abstract class BaseController {
    protected Activity activity ;
    protected INetworkListener listener ;

    public BaseController(Activity activity, INetworkListener listener) {
        this.activity = activity ;
        this.listener = listener ;
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
}

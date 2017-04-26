package network.controllers;

import android.app.Activity;

import java.io.IOException;

import config.UserConfig;
import models.User;
import models.UserData;
import network.IBootStrap;
import network.INetworkListener;
import network.RequestType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.SLogger;

/**
 * Created by yuva on 19/4/17.
 */
public class BootstrapController extends BaseController {

    public BootstrapController(Activity activity, INetworkListener listener) {
        super(activity, listener);
    }

    public void start(User user) {
        IBootStrap bootStrap = getClient().create(IBootStrap.class);
        Call<UserData> call = bootStrap.createUser(user);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if(response.isSuccessful()) {
                    UserData userData = response.body();
                    UserConfig.getInstance().set(userData.user, userData.userStatus);
                    SLogger.BOOTSTRAP.i("User data is " + userData.toJson());
                    listener.handleSuccess(RequestType.BOOT_STRAP, userData); ;
                } else {
                    try {
                        listener.handleFailure(RequestType.BOOT_STRAP, response.errorBody());
                        SLogger.BOOTSTRAP.i(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                listener.handleError(RequestType.BOOT_STRAP, t);
                t.printStackTrace();
                SLogger.BOOTSTRAP.i(t.getMessage());
            }
        });
    }
}

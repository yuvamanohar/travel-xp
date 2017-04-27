package network.controllers;

import android.app.Activity;

import org.greenrobot.eventbus.EventBus;

import config.UserConfig;
import models.User;
import models.UserData;
import network.IBootStrap;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by yuva on 19/4/17.
 */
public class BootstrapController extends BaseController {

    public BootstrapController(Activity activity) {
        super(activity);
    }

    public void bootStrap(User user) {
        IBootStrap bootStrap = getClient().create(IBootStrap.class);
        Call<UserData> call = bootStrap.createUser(user);
//        call.enqueue(new Callback<UserData>() {
//            @Override
//            public void onResponse(Call<UserData> call, Response<UserData> response) {
//                if(response.isSuccessful()) {
//                    UserData userData = response.body();
//                    UserConfig.getInstance().set(userData.user, userData.userStatus);
////                    SLogger.BOOTSTRAP.d("Userdata is : " + userData.toJson());
//                    EventBus.getDefault().post(userData);
//                } else {
//                    try {
//                        String error = response.errorBody().string() ;
//                        SLogger.BOOTSTRAP.d(error);
//                        EventBus.getDefault().post(NetworkErrorObj.get(ErrorMessage.USER_DATA_SERVER_ERROR));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserData> call, Throwable t) {
//                t.printStackTrace();
//                SLogger.BOOTSTRAP.d("Bootstrap Failed : " + t.getMessage());
//                EventBus.getDefault().post(NetworkErrorObj.get(ErrorMessage.INTERNET_ERROR));
//            }
//        });

        call.enqueue(new ResponseHandler<UserData>() {
            @Override
            public void onSuccess(Call<UserData> call, Response<UserData> response) {
                    UserData userData = response.body();
                    UserConfig.getInstance().set(userData.user, userData.userStatus);
//                    SLogger.BOOTSTRAP.d("Userdata is : " + userData.toJson());
                    EventBus.getDefault().post(response.body());
            }

            @Override
            public void onRequestFailure(Call<UserData> call, Response<UserData> response) {
                EventBus.getDefault().post(NetworkErrorObj.get(ErrorMessage.USER_DATA_SERVER_ERROR));
            }
        });
    }
}

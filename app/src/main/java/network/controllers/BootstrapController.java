package network.controllers;

import android.app.Activity;

import models.User;
import models.UserData;
import network.IBootStrap;
import network.framework.BaseController;
import network.framework.NetworkErrorObj;
import retrofit2.Call;

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
        this.enqueue(call);
    }

    @Override
    public String getRequestFailureErrorMessage() {
        return NetworkErrorObj.ErrorMessage.USER_DATA_SERVER_ERROR;
    }
}

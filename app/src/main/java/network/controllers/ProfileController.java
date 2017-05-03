package network.controllers;

import android.app.Activity;

import com.facebook.Profile;

import models.PartialFeed;
import models.ProfileData;
import network.IFeed;
import network.IProfile;
import network.framework.BaseController;
import network.framework.NetworkErrorObj;
import retrofit2.Call;

/**
 * Created by yuva on 3/5/17.
 */

public class ProfileController extends BaseController {

    public ProfileController(Activity activity) {
        super(activity);
    }

    public void getProfileInfo(Long userId) {
        IProfile iProfile = getClient().create(IProfile.class) ;

        Call<ProfileData> profileDataCall = iProfile.getProfileInfo(userId) ;
        this.enqueue(profileDataCall);
    }

    @Override
    public String getRequestFailureErrorMessage() {
        return NetworkErrorObj.ErrorMessage.PROFILE_DATA_ERROR ;
    }
}

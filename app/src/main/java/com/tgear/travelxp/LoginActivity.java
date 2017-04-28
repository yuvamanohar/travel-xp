package com.tgear.travelxp;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import config.AppConfig;
import config.UserConfig;
import models.SocialProfile;
import models.User;
import models.UserData;
import network.controllers.BootstrapController;
import util.SLogger;
import util.TimeModule;

public class LoginActivity extends BaseActivity {
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppConfig.get() ;
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // com.tgear.travelxp.App code
                SLogger.FB_LOGIN.i("Fb login success " + loginResult.getAccessToken());
                bootStrap(Profile.getCurrentProfile());
            }

            @Override
            public void onCancel() {
                // com.tgear.travelxp.App code
                SLogger.FB_LOGIN.i("Fb login cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                // com.tgear.travelxp.App code
                SLogger.FB_LOGIN.i("Fb login error " + exception);
            }
        });

        bootStrap(Profile.getCurrentProfile());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void bootStrap(Profile profile) {
        if (profile != null) {
            SocialProfile socialProfile = new SocialProfile(null, AppConfig.Network.FACEBOOK.getName(), profile.getId(), profile.getFirstName(), profile.getMiddleName(),
                    profile.getLastName(), profile.getName(), profile.getProfilePictureUri(100, 100).toString());
            User user = new User(null, socialProfile, null, null, AppConfig.PLATFORM, null);
            new BootstrapController(this).bootStrap(user);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserData(UserData userData) {
        UserConfig.getInstance().set(userData.user, userData.userStatus);
        TimeModule.get().setServerTimeAtStart(userData.serverTimeAtStart);
        launchFeedActivity();
    }

    private void launchFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class) ;
        startActivity(intent);
    }
}

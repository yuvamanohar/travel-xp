package com.tgear.travelxp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import config.AppConfig;
import models.SocialProfile;
import models.User;
import network.INetworkListener;
import network.RequestType;
import network.controllers.BootstrapController;
import okhttp3.ResponseBody;
import util.SLogger;

public class LoginActivity extends AppCompatActivity implements INetworkListener {
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppConfig.getInstance() ;
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
            new BootstrapController(this, this).start(user);
            launchFeedActivity();
        }
    }

    @Override
    public void handleSuccess(RequestType type, Object object) {
        switch (type) {
            case BOOT_STRAP:
                launchFeedActivity() ;
                break ;
        }
    }

    private void launchFeedActivity() {
        Intent intent = new Intent(this, FeedActivity.class) ;
        startActivity(intent);
    }

    @Override
    public void handleFailure(RequestType type, ResponseBody responseBody) {
        // retry pop-up
        SLogger.NETWORK_CALL.i("Network call failed for " + type.name());
    }

    @Override
    public void handleError(RequestType type, Throwable t) {
        //error... retry pop-up
        SLogger.NETWORK_CALL.i("Network call failed for " + type.name());
    }
}

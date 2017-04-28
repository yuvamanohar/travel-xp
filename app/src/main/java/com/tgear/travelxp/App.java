package com.tgear.travelxp;

import android.app.Application;

import config.AppConfig;
import util.SLogger;

/**
 * Created by yuva on 14/4/17.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppConfig.get();
        SLogger.APP_INIT.i("App initialized") ;
    }
}

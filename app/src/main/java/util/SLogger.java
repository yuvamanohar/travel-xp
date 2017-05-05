package util;

import android.util.Log;

/**
 * Created by yuva on 14/4/17.
 */
public enum SLogger {
    APP_INIT,
    FB_LOGIN,
    BOOTSTRAP,
    DYNAMIC_FEED_LOAD,
    POST_CONTENT,
    PROFILE_DATA,
    ALBUM_THUMBNAIL,
    POST_THUMBNAIL ;

    public static int V_PRIORITY = 2 ;
    public static int D_PRIORITY = 3 ;
    public static int I_PRIORITY = 4 ;
    public static int W_PRIORITY = 5 ;
    public static int E_PRIORITY = 6 ;
    public static int currentPriority = 4 ; // default log level is info

    public static void setLogPriority(int priority) {
        currentPriority = priority ;
        
    }

    public void v(String message) {
        if(currentPriority <= V_PRIORITY) {
            Log.v(this.name(), message) ;
        }
    }

    public void d(String message) {
        if(currentPriority <= D_PRIORITY) {
            Log.d(this.name(), message) ;
        }
    }

    public void i(String message){
        if(currentPriority <= I_PRIORITY) {
            Log.i(this.name(), message) ;
        }
    }

    public void i(String message, Throwable throwable) {
        if(currentPriority <= I_PRIORITY) {
            Log.i(this.name(), message, throwable) ;
        }
    }

    public void w(String message){
        if(currentPriority <= W_PRIORITY) {
            Log.w(this.name(), message) ;
        }
    }

    public void e(String message){
        if(currentPriority <= E_PRIORITY) {
            Log.e(this.name(), message) ;
        }
    }
}

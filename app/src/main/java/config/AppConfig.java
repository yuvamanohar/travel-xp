package config;

import util.SLogger;
import util.StringUtils;

/**
 * Created by yuva on 14/4/17.
 */
public class AppConfig {
    private static AppConfig ourInstance = new AppConfig();

    public enum Network {
        FACEBOOK ;

        public String getName() {
            return StringUtils.toLowerCase(this.name()) ;
        }
    }

    public static final String PLATFORM = "android" ;
    public static final Boolean DEBUG = true ;
    public static final int LOG_PRIORITY = SLogger.D_PRIORITY ;
    public static String SERVER_BASE_URL = "http://192.168.2.12:9000/" ;
    public static String CDN_BASE_URL = SERVER_BASE_URL + "travelxp-cdn/" ;


    public static AppConfig get() {
        return ourInstance;
    }

    private AppConfig() {
        SLogger.setLogPriority(LOG_PRIORITY);
    }
}

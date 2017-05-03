package network.framework;

import retrofit2.Call;

/**
 * Created by yuva on 28/4/17.
 */

public class NetworkErrorObj<T> {
    public final Call<T> call ;
    public final String errorMessage ;

    private NetworkErrorObj(Call<T> call, String errorMessage) {
        this.call = call ;
        this.errorMessage = errorMessage ;
    }

    public static NetworkErrorObj get(Call call, String errorMessage) {
        return new NetworkErrorObj(call, errorMessage) ;
    }

    public static class ErrorMessage{
        public static String MALFORMED_URI = "Selected File not readable." ;
        public static String USER_DATA_SERVER_ERROR = "Server error while fetching user data" ;
        public static String POST_CONTENT_SERVER_ERROR = "Post failed. Please try again" ;
        public static String FEED_SERVER_ERROR = "Servers need a vacation. Please try again" ;
        public static String INTERNET_ERROR = "No internet connectivity. Please enable internet and try again !" ;
        public static String SERVER_CONNECT_ERROR = "Servers not reachable. Please try again later." ;
        public static String PROFILE_DATA_ERROR = "Error while loading profile info. Please try again later." ;
    }

}

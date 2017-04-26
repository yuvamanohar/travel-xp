package network;

import java.util.Map;

import models.User;
import models.UserData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by yuva on 17/4/17.
 */
public interface IBootStrap {
    @POST("v1/bootstrap")
    Call<UserData> createUser(@Body User user);
}

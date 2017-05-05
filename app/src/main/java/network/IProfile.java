package network;

import models.ProfileData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yuva on 3/5/17.
 */

public interface IProfile {

    @GET("v1/getProfileInfo/user/{userId}")
    Call<ProfileData> getProfileInfo(@Path("userId") Long userId);

}

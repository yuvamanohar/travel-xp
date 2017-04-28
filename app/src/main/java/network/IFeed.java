package network;

import java.util.List;

import models.PartialFeed;
import models.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yuva on 27/4/17.
 */

public interface IFeed {
    @GET("v1/user/{userId}/getOlderFeed")
    Call<PartialFeed> getOlderFeed(@Path("userId") Long userId, @Query("referenceTime") String referenceTime, @Query("offset") Integer offset) ;

    @GET("v1/user/{userId}/getUpdatedFeed")
    Call<PartialFeed> getUpdatedFeed(@Path("userId") Long userId, @Query("mostRecentPostTime") String mostRecentPostTime) ;

}

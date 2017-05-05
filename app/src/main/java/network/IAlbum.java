package network;

import models.Album;
import models.PartialFeed;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yuva on 5/5/17.
 */

public interface IAlbum {

    @GET("v1/getPosts/album/{albumId}")
    Call<Album> getPosts(@Path("albumId") Long albumId) ;

}

package network;

import java.util.List;

import models.Post;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by yuva on 24/4/17.
 */

public interface IPostContent {

    @Multipart
    @PUT("v1/user/{userId}/postContent")
    Call<Post> postContent(@Path("userId") Long userId,
                          @Part("description") RequestBody description,
                          @Part List<MultipartBody.Part> file);
}

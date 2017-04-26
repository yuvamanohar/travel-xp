package network.controllers;

import android.app.Activity;
import android.net.Uri;

import com.google.gson.Gson;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import config.UserConfig;
import models.Post;
import models.PostDetail;
import network.INetworkListener;
import network.IPostContent;
import network.RequestType;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.FileUtils;
import util.SLogger;

/**
 * Created by yuva on 24/4/17.
 */

public class PostController extends BaseController {

    public PostController(Activity activity, INetworkListener listener) {
        super(activity, listener);
    }

    public void postContent(Post post) {
        // create upload service client
        IPostContent service =
                getClient().create(IPostContent.class);

        List<MultipartBody.Part> multipartBodyList = new ArrayList<>() ;

        for(PostDetail detail : post.postDetails) {
            // Uri to file conversion is needed
            File file = null;
            try {
                file = new File(FileUtils.getFilePath(activity, detail.uri));
            } catch (URISyntaxException e) {
                e.printStackTrace();
                listener.handleFailure(RequestType.POST_CONTENT, null);
            }

            // create RequestBody instance from file
            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(activity.getContentResolver().getType(detail.uri)),
                            file);

            multipartBodyList.add(MultipartBody.Part.createFormData(file.getName(), file.getName(), requestFile)) ;
            detail.media = file.getName() ;
        }

        // add another part within the multipart request
        String descriptionString = post.toJson() ;
        RequestBody description =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, descriptionString);

        // finally, execute the request
        Call<Post> call = service.postContent(UserConfig.getInstance().getUser().userId,
                                                            description, multipartBodyList);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                SLogger.POST_CONTENT.i("Post Success " + response.toString());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                t.printStackTrace();
                SLogger.POST_CONTENT.i("Post Failed");
            }
        });
    }
}

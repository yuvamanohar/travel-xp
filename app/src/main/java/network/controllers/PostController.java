package network.controllers;

import android.app.Activity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import config.UserConfig;
import models.Post;
import models.PostDetail;
import network.IPostContent;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.FileUtils;

/**
 * Created by yuva on 24/4/17.
 */

public class PostController extends BaseController {

    public PostController(Activity activity) {
        super(activity);
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
                EventBus.getDefault().post(NetworkErrorObj.get(ErrorMessage.MALFORMED_URI));
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
//        call.enqueue(new Callback<Post>() {
//            @Override
//            public void onResponse(Call<Post> call, Response<Post> response) {
//                if(response.isSuccessful()) {
////                    SLogger.POST_CONTENT.d("Post Success : " + response.body().toJson());
//                    EventBus.getDefault().post(response.body());
//                } else {
//                    try {
//                        String error = response.errorBody().string() ;
////                        SLogger.POST_CONTENT.d(error);
//                        EventBus.getDefault().post(NetworkErrorObj.get(ErrorMessage.POST_CONTENT_SERVER_ERROR));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Post> call, Throwable t) {
//                t.printStackTrace();
////                SLogger.POST_CONTENT.d("Post Failed : " + t.getMessage());
//                EventBus.getDefault().post(NetworkErrorObj.get(ErrorMessage.INTERNET_ERROR));
//            }
//        });

        call.enqueue(new ResponseHandler<Post>() {
            @Override
            public void onSuccess(Call<Post> call, Response<Post> response) {
                EventBus.getDefault().post(response.body());
            }

            @Override
            public void onRequestFailure(Call<Post> call, Response<Post> response) {
                EventBus.getDefault().post(NetworkErrorObj.get(ErrorMessage.POST_CONTENT_SERVER_ERROR));
            }
        });
    }
}

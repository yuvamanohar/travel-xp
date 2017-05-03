package network.controllers;

import android.app.Activity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import config.UserConfig;
import models.Post;
import models.PostDetail;
import network.IPostContent;
import network.framework.BaseController;
import network.framework.NetworkErrorObj;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
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
                EventBus.getDefault().post(NetworkErrorObj.get(null, NetworkErrorObj.ErrorMessage.MALFORMED_URI));
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
        Call<Post> call = service.postContent(UserConfig.get().getUser().userId,
                                                            description, multipartBodyList);
        this.enqueue(call) ;
    }

    @Override
    public String getRequestFailureErrorMessage() {
        return NetworkErrorObj.ErrorMessage.POST_CONTENT_SERVER_ERROR;
    }
}

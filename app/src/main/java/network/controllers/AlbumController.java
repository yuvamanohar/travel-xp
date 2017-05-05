package network.controllers;

import android.app.Activity;

import java.util.List;

import models.Album;
import models.PartialFeed;
import models.Post;
import network.IAlbum;
import network.IFeed;
import network.framework.BaseController;
import network.framework.NetworkErrorObj;
import retrofit2.Call;

/**
 * Created by yuva on 5/5/17.
 */

public class AlbumController extends BaseController {


    public AlbumController(Activity activity) {
        super(activity);
    }

    public void getPosts(Long albumId) {
        IAlbum iAlbum = getClient().create(IAlbum.class) ;

        Call<Album> albumCall = iAlbum.getPosts(albumId) ;
        this.enqueue(albumCall);
    }

    @Override
    public String getRequestFailureErrorMessage() {
        return NetworkErrorObj.ErrorMessage.ALBUM_DATA_ERROR ;
    }
}

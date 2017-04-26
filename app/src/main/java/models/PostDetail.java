package models;

import android.net.Uri;

/**
 * Created by yuva on 25/4/17.
 */

public class PostDetail extends JsonModel {

    public enum MediaType {
        GIF,
        PHOTO,
        VIDEO ;
    }

    public String media ;
    public MediaType mediaType ;

    public transient Uri uri ;

    public PostDetail() {}

    public PostDetail(String media, MediaType mediaType, Uri uri) {
        this.media = media ;
        this.mediaType = mediaType ;
        this.uri = uri ;
    }
}

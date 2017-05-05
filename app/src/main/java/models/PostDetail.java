package models;

import android.net.Uri;

import config.AppConfig;

/**
 * Created by yuva on 25/4/17.
 */

public class PostDetail extends JsonModel {

    public enum MediaType {
        GIF,
        PHOTO,
        VIDEO ;
    }

    private String media ;
    public MediaType mediaType ;

    public transient Uri uri ;

    public PostDetail() {}

    public PostDetail(String media, MediaType mediaType, Uri uri) {
        this.media = media ;
        this.mediaType = mediaType ;
        this.uri = uri ;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getCdnMedia() {
        return AppConfig.CDN_BASE_URL + media;
    }
}

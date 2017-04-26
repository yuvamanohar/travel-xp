package models;

import java.util.List;

/**
 * Created by yuva on 17/4/17.
 */

public class Post extends JsonModel {

    public Long postId ;

    public String name ;
    public String title ;
    public String scribble ;
    public Double latitude ;
    public Double longitude ;
    public String location ;
    public List<PostDetail> postDetails ;
    public Integer likes ;
    public Integer comments ;
    public Integer shares ;

    public Post() {}

    public Post(String scribble, Double latitude, Double longitude, String location, List<PostDetail> postDetails) {
        this.scribble = scribble ;
        this.latitude = latitude ;
        this.longitude = longitude ;
        this.location = location ;
        this.postDetails = postDetails ;
    }
}


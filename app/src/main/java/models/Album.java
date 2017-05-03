package models;

import java.util.List;

/**
 * Created by yuva on 2/5/17.
 */

public class Album extends JsonModel {

    public Long albumId ;

    public String name ;
    public String description ;

    public Long userId ;

    public List<Post> posts ;
}

package models;

/**
 * Created by yuva on 17/4/17.
 */
public class User extends JsonModel {

    public Long userId ;

    public SocialProfile socialProfile ;

    public Long mobile ;
    public String email ;
    public String platform  ;
    public String deviceId ;

    public User() {} ;

    public User(Long userId, SocialProfile socialProfile, Long mobile, String email, String platform, String deviceId) {
        this.userId = userId ;
        this.socialProfile = socialProfile ;
        this.mobile = mobile ;
        this.email = email ;
        this.platform = platform ;
        this.deviceId = deviceId ;
    }
}

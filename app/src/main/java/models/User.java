package models;

import java.util.Objects;

/**
 * Created by yuva on 17/4/17.
 */
public class User extends JsonModel {

    public Long userId ;

    public SocialProfile socialProfile ;

    public String aboutMe ;
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

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        User user = (User) o;
        return (userId == user.userId) && (socialProfile.socialProfileId == user.socialProfile.socialProfileId) ;
    }

    @Override
    public int hashCode() {
        int result = 7;
        result = 31 * result + userId.hashCode() ;
        result = 31 * result + socialProfile.socialProfileId.hashCode() ;
        result = 31 * result + socialProfile.socialNetworkId.hashCode() ;
        return result;
    }

}

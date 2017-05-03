package config;

import java.util.List;

import models.Album;
import models.Post;
import models.ProfileData;
import models.User;

/**
 * Created by yuva on 19/4/17.
 */
public class UserConfig {
    private static UserConfig ourInstance = new UserConfig();

    private User user ;
    private String userStatus ;

    private ProfileData profileData ;

    public static UserConfig get() {
        return ourInstance;
    }

    private UserConfig() {
    }

    public void setUserData(User user, String userStatus) {
        this.user = user ;
        this.userStatus = userStatus ;
    }

    public User getUser() {
        return user;
    }

    public Long getUserId() {
        return user.userId ;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public ProfileData getProfileData() {
        return profileData;
    }

    public void setProfileData(ProfileData profileData) {
        this.profileData = profileData;
    }
}

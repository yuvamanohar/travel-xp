package config;

import models.User;

/**
 * Created by yuva on 19/4/17.
 */
public class UserConfig {
    private static UserConfig ourInstance = new UserConfig();

    private User user ;
    private String userStatus ;

    public static UserConfig getInstance() {
        return ourInstance;
    }

    private UserConfig() {
    }

    public void set(User user, String userStatus) {
        this.user = user ;
        this.userStatus = userStatus ;
    }

    public User getUser() {
        return user;
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
}

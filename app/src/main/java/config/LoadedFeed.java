package config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.PartialFeed;
import models.Post;
import models.User;

/**
 * Created by yuva on 27/4/17.
 */

public class LoadedFeed {
    private static LoadedFeed loadedFeed ;

    private List<Post> posts ;
    private Map<Long, User> users ;
    private String mostRecentPostTime ;
    private String leastRecentPostTime ;

    private LoadedFeed() {
        posts = new ArrayList<>() ;
        users = new HashMap<>() ;
    }

    public static LoadedFeed getInstance() {
        if(loadedFeed == null) {
            loadedFeed = new LoadedFeed();
        }

        return loadedFeed ;
    }

    public void addPartialFeed(PartialFeed partialFeed) {
        for(User user : partialFeed.users) {
            users.put(user.userId, user) ;
        }

        switch (partialFeed.feedType) {
            case OLDER_FEED:
                posts.addAll(partialFeed.posts) ;
                leastRecentPostTime = partialFeed.leastRecentPostTime ;
                break ;
            case REFRESH_FEED:
                posts.addAll(0, partialFeed.posts) ;
                mostRecentPostTime = partialFeed.mostRecentPostTime ;
                break ;
        }
    }

    public List<Post> getPosts() {
        return posts;
    }

    public String getUserName(Long userId) {
        return users.get(userId).socialProfile.completeName ;
    }
}

package config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.PartialFeed;
import models.Post;
import models.User;
import util.TimeModule;

/**
 * Created by yuva on 27/4/17.
 */

public class LoadedFeed {
    private static LoadedFeed loadedFeed ;

    private List<Post> posts ;
    private Map<Long, User> users ;
    private String mostRecentPostTime ;
    private String referenceTime;
    private Integer offset ; // Offset from the post having updatedAt <= referenceTime
    private Boolean olderFeedEnd;

    private LoadedFeed() {
        posts = new ArrayList<>() ;
        users = new HashMap<>() ;
        mostRecentPostTime = TimeModule.get().getCurrentTimeOnServer() ;
        referenceTime = mostRecentPostTime ;
        offset = 0 ;
        olderFeedEnd = false ;
    }

    public static LoadedFeed getInstance() {
        if(loadedFeed == null) {
            loadedFeed = new LoadedFeed();
        }

        return loadedFeed ;
    }

    public void addPartialFeed(PartialFeed partialFeed) {
        this.olderFeedEnd = partialFeed.olderFeedEnd;
        this.offset = partialFeed.offset ;
        for(User user : partialFeed.users) {
            users.put(user.userId, user) ;
        }

        switch (partialFeed.feedType) {
            case OLDER_FEED:
                posts.addAll(partialFeed.posts) ;
                break ;
            case UPDATED_FEED:
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

    public String getMostRecentPostTime() {
        return mostRecentPostTime;
    }

    public String getReferenceTime() {
        return referenceTime;
    }

    public Boolean getOlderFeedEnd() {
        return olderFeedEnd;
    }

    public Integer getOffset() {
        return offset;
    }
}

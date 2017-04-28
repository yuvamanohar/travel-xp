package models;

import java.util.List;
import java.util.Set;

/**
 * Created by yuva on 27/4/17.
 */
public class PartialFeed extends JsonModel {
    public enum FeedType {
        UPDATED_FEED,
        OLDER_FEED ;
    }

    public List<Post> posts ;
    public Set<User> users ;
    public String mostRecentPostTime ;
    public String referenceTime;
    public Integer offset ;
    public FeedType feedType ;
    public Boolean olderFeedEnd;
    // When refreshing posts, it may so happen that we cannot send all the updated posts at one shot
    // In such case this time be referenceTime of updated posts
    // TODO in later version it gets tricky if the user keeps refreshing and keep checking partial updates
    // TODO Client has to handle such case..possibly by clearing up the whole list and re-populate
//    public String truncatedMostRecentPostTime ;
//    public boolean isEndOfFeed ;

}

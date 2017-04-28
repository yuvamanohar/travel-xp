package network.controllers;

import android.app.Activity;

import models.PartialFeed;
import network.IFeed;
import network.framework.BaseController;
import network.framework.NetworkErrorObj;
import retrofit2.Call;

/**
 * Created by yuva on 27/4/17.
 */

public class FeedController extends BaseController {
    public FeedController(Activity activity) {
        super(activity);
    }

    public void getOlderFeed(Long userId, String leastRecentPostTime) {
        IFeed iFeed = getClient().create(IFeed.class) ;

        Call<PartialFeed> partialFeedCall = iFeed.getOlderFeed(userId, leastRecentPostTime) ;
        this.enqueue(partialFeedCall);
    }

    public void getUpdatedFeed(Long userId, String mostRecentPostTime) {
        IFeed iFeed = getClient().create(IFeed.class) ;

        Call<PartialFeed> partialFeedCall = iFeed.getUpdatedFeed(userId, mostRecentPostTime) ;
        this.enqueue(partialFeedCall);
    }

    @Override
    public String getRequestFailureErrorMessage() {
        return NetworkErrorObj.ErrorMessage.FEED_SERVER_ERROR;
    }
}

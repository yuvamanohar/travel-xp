package network.controllers;

import android.app.Activity;

import config.LoadedFeed;
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

    public void getOlderFeed(Long userId, String referenceTime, Integer offset) {
        if(LoadedFeed.getInstance().getOlderFeedEnd())
            return;

        IFeed iFeed = getClient().create(IFeed.class) ;

        Call<PartialFeed> partialFeedCall = iFeed.getOlderFeed(userId, referenceTime, offset) ;
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

package network.controllers;

import android.app.Activity;

import org.greenrobot.eventbus.EventBus;

import models.PartialFeed;
import network.IFeed;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        partialFeedCall.enqueue(new ResponseHandler<PartialFeed>() {
            @Override
            public void onSuccess(Call<PartialFeed> call, Response<PartialFeed> response) {
                EventBus.getDefault().post(response.body());
            }

            @Override
            public void onRequestFailure(Call<PartialFeed> call, Response<PartialFeed> response) {
                EventBus.getDefault().post(NetworkErrorObj.get(ErrorMessage.FEED_SERVER_ERROR));
            }
        });
    }

    public void getUpdatedFeed(Long userId, String mostRecentPostTime) {
        IFeed iFeed = getClient().create(IFeed.class) ;

        Call<PartialFeed> partialFeedCall = iFeed.getUpdatedFeed(userId, mostRecentPostTime) ;
        partialFeedCall.enqueue(new ResponseHandler<PartialFeed>() {
            @Override
            public void onSuccess(Call<PartialFeed> call, Response<PartialFeed> response) {
                EventBus.getDefault().post(response.body());
            }

            @Override
            public void onRequestFailure(Call<PartialFeed> call, Response<PartialFeed> response) {
                EventBus.getDefault().post(NetworkErrorObj.get(ErrorMessage.FEED_SERVER_ERROR));
            }
        });
    }
}

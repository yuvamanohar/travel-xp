package network;

import okhttp3.ResponseBody;

/**
 * Created by yuva on 21/4/17.
 */

public interface INetworkListener<T> {

    public void handleSuccess(RequestType type, T object) ;
    public void handleFailure(RequestType type, ResponseBody responseBody) ;
    public void handleError(RequestType type, Throwable t) ;
}

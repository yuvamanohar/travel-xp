package models;

import com.google.gson.Gson;

/**
 * Created by yuva on 24/4/17.
 */

public abstract class JsonModel {

    public String toJson() {
        Gson gson = new Gson() ;
        return gson.toJson(this) ;
    }

}

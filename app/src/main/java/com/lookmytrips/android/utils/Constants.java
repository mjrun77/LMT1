package com.lookmytrips.android.utils;

/**
 * Created by Win on 08.09.2016.
 */

public class Constants {


    private String feed_interesting = "http://www.lookmytrips.com/api/rating/interesting/?last=";
    private String feed_popular = "http://www.lookmytrips.com/api/rating/popular/?last=";
    private String feed_new = "http://www.lookmytrips.com/api/media/feed/?last=&media=";

    public String getFeed_popular() {
        return feed_popular;
    }

    public String getFeed_new() {
        return feed_new;
    }

    public String getFeed_interesting() {
        return feed_interesting;
    }

    public static final String IS_LOGGED_IN = "isLoggedIn";

    public static final String NAME = "name";
    public static final String AVATAR = "email";
    public static final String USER_ID = "1";
    public static final String PREFERENCES_LOGIN = "Login";

    public static final class HTTP {
        public static final String BASE_URL = "http://www.lookmytrips.com";
    }
}

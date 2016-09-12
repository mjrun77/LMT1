package com.lookmytrips.android.controller;


import com.lookmytrips.android.model.MainFeed;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MVP on 21.03.2016.
 */
public interface PostService {

    @GET("/api/rating/interesting/?last=0")
    Call<MainFeed> getAllPosts();
}

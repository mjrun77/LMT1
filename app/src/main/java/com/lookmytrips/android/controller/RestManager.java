package com.lookmytrips.android.controller;



import com.lookmytrips.android.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MVP on 21.03.2016.
 */
public class RestManager {

    private PostService mPostService;

    public PostService getmPostService(){
        if (mPostService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mPostService = retrofit.create(PostService.class);

        }
        return mPostService;
    }
}

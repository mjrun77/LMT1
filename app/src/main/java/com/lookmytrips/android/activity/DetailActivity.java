package com.lookmytrips.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lookmytrips.android.R;
import com.lookmytrips.android.fragments.Post;
import com.lookmytrips.android.helper.Constants;

/**
 * Created by Win on 13.09.2016.
 */
public class DetailActivity extends AppCompatActivity{

    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        post = (Post) getIntent().getSerializableExtra(Constants.REFERENCE.POST);

        Toast.makeText(this, post.getLikes(), Toast.LENGTH_SHORT).show();


    }
}

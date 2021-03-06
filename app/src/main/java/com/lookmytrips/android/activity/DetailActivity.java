package com.lookmytrips.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lookmytrips.android.R;
import com.lookmytrips.android.fragments.Post;
import com.lookmytrips.android.helper.Constants;
import com.pkmmte.view.CircularImageView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private Post post;
    private SlidingUpPanelLayout mLayout;
    private TextView mTvDetailTitle, mDetailUserName;
    private CircularImageView mIvDetailAvatar;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        post = (Post) getIntent().getSerializableExtra(Constants.REFERENCE.POST);

        setSupportActionBar((Toolbar) findViewById(R.id.detail_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_close);

        getSupportActionBar().setTitle("");

        // TODO: 15.09.2016  detail request
     //   getDetail(post.getId());

        mTvDetailTitle = (TextView) findViewById(R.id.detail_title);
        mTvDetailTitle.setText(post.getTitle());

        mDetailUserName = (TextView) findViewById(R.id.detail_user_name);
        mDetailUserName.setText(post.getUserName());
        mIvDetailAvatar = (CircularImageView) findViewById(R.id.detail_avatar);

        Picasso.with(this)
                .load(post.getAvatar())
                .into(mIvDetailAvatar);


        ListView lv = (ListView) findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DetailActivity.this, "onItemClick", Toast.LENGTH_SHORT).show();
            }
        });

        List<String> your_array_list = Arrays.asList(
                "Comments",
                "item_2",
                "item_3"
        );

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list);

        lv.setAdapter(arrayAdapter);

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i("Detail", "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, PanelState previousState, PanelState newState) {
                Log.i("Detail", "onPanelStateChanged " + newState);
            }
        });
        mLayout.setFadeOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(PanelState.COLLAPSED);
            }
        });

//        TextView t = (TextView) findViewById(R.id.name);
//        t.setText("");
//        Button f = (Button) findViewById(R.id.follow);
//        f.setText("");
//        f.setMovementMethod(LinkMovementMethod.getInstance());
//        f.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse("http://www.twitter.com/umanoapp"));
//                startActivity(i);
//            }
//        });
    }



    @Override
    public boolean onSupportNavigateUp() {
        finish();

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
//        MenuItem item = menu.findItem(R.id.action_toggle);
//        if (mLayout != null) {
//            if (mLayout.getPanelState() == PanelState.HIDDEN) {
//                item.setTitle(R.string.action_show);
//            } else {
//                item.setTitle(R.string.action_hide);
//            }
//        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share: {
//                if (mLayout != null) {
//                    if (mLayout.getPanelState() != PanelState.HIDDEN) {
//                        mLayout.setPanelState(PanelState.HIDDEN);
//                        item.setTitle(R.string.action_show);
//                    } else {
//                        mLayout.setPanelState(PanelState.COLLAPSED);
//                        item.setTitle(R.string.action_hide);
//                    }
//                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == PanelState.EXPANDED || mLayout.getPanelState() == PanelState.ANCHORED)) {
            mLayout.setPanelState(PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}

package com.lookmytrips.android.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lookmytrips.android.R;
import com.lookmytrips.android.adapters.PostAdapter;
import com.lookmytrips.android.utils.AppController;
import com.lookmytrips.android.utils.SpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListNewsFragment extends Fragment implements PostAdapter.PostClickListener{

    int l = 1;
    LinearLayoutManager mLayoutManager;
    String title;
    String url;
    ProgressBar progressBar;
    private String category;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Post> posts = new ArrayList<>();
    private PostAdapter mPostAdapter;
    private Post post;

    public ListNewsFragment() {
    }

    public static Fragment newInstance() {

        ListNewsFragment myFragment = new ListNewsFragment();
//        Bundle args = new Bundle();
//        args.putString("title", title);
//        args.putString("url", url);
//        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        title = getArguments().getString("title");
//        url = getArguments().getString("url");
    }

    private void loadNews() {

        //    if (AppStatus.getInstance(getActivity()).isOnline(getActivity())) {
        progressBar.setVisibility(View.VISIBLE);
        String category_url = "http://www.lookmytrips.com/api/rating/interesting/?last=0";

        JsonObjectRequest eventsRequest = new JsonObjectRequest(Request.Method.GET,
                category_url, (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                post = new Post();
                try {
                    JSONObject obj = response.getJSONObject("items");

                    JSONArray feedArray = obj.getJSONArray("feed");

                    for (int i = 0; i < feedArray.length(); i++) {
                        JSONObject jsonObject = feedArray.getJSONObject(i);
                        String id = jsonObject.getString("_id");
                        String owner = jsonObject.getString("Owner");
                        String likes = jsonObject.getString("Likes");
                        String title = jsonObject.getString("Title");
                        String wasHere = jsonObject.getString("WasHere");
                        String shares = jsonObject.getString("Shares");
                        String rating = jsonObject.getString("Rating");
                        String comments = jsonObject.getString("Comments");

                        JSONObject images = jsonObject.getJSONObject("Images");
                        String image = images.getString("image");
                        String thumb = images.getString("thumb");
                        String big = images.getString("big");

                        JSONObject geoHr = jsonObject.getJSONObject("GeoHr");
                        String country = geoHr.getString("country");
                        String city = geoHr.getString("city");
                        String state = geoHr.getString("state");
                        String street = geoHr.getString("street");


                        post.setId(id);
                        post.setLikes(likes);
                        post.setOwner(owner);
                        post.setImage(image);
                        post.setBig(big);
                        post.setTitle(title);
                        post.setWasHere(wasHere);
                        post.setShares(shares);
                        post.setRating(rating);
                        post.setComments(comments);
                        post.setThumb(thumb);
                        post.setGeoHrCity(country);
                        post.setGeoHrCity(city);
                        post.setGeoHrState(state);
                        post.setGeoHrStreet(street);

                        posts.add(post);


                    }

                    JSONArray userArray = obj.getJSONArray("users");

                    for (int i = 0; i < userArray.length(); i++) {
                        JSONObject jsonUser = userArray.getJSONObject(i);
                        String id = jsonUser.getString("id");
                        String avatar = jsonUser.getString("Avatar");
                        String anguage = jsonUser.getString("Language");
                        String name = jsonUser.getString("Name");
                        String role = jsonUser.getString("Role");

                        post.setAvatar(avatar);
                        post.setUserName(name);

                    }


                    mPostAdapter.notifyDataSetChanged();
                    } catch (JSONException e1) {
                    e1.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        AppController.getInstance().addToRequestQueue(eventsRequest);
//        }else {
//            Toast.makeText(getActivity(), "Отсутствует соединение с интернетом",
//                    Toast.LENGTH_LONG).show();
//        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup conteiner, Bundle bundle) {
//        category = getArguments().getString("url");
//        title = getArguments().getString("title");

        View v = inflater.inflate(R.layout.fragment_recyclerview, conteiner, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar_news);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        posts = new ArrayList();
        mPostAdapter = new PostAdapter(getActivity(), this);
        mAdapter = mPostAdapter;
        mRecyclerView.setAdapter(mAdapter);



        loadNews();

//        mRecyclerView.addOnItemTouchListener(
//                new RecyclerClick(getActivity(), new RecyclerClick.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        // TODO Handle item click
//                        Post selectedFlower = postAdapter.getSelectedFlower(position - 1);
//
//                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
//                        intent.putExtra("POST_ITEM", selectedFlower);
//                        getActivity().startActivity(intent);
//                    }
//                })
//        );

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, visibleItemCount, totalItemCount;
            private int previousTotal = 0;
            private boolean loading = true;
            private int visibleThreshold = 5;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                visibleItemCount = mRecyclerView.getChildCount();
//                totalItemCount = mLayoutManager.getItemCount();
//                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
//
//                if (loading) {
//                    if (totalItemCount > previousTotal) {
//                        loading = false;
//                        previousTotal = totalItemCount;
//                    }
//                }
//                if (!loading && (totalItemCount - visibleItemCount)
//                        <= (firstVisibleItem + visibleThreshold)) {
//                    // End has been reached
//                    loading = true;
//
//                    l++;
//                    category = category + "&page=" + l;
//                    loadNews(category);
//                    mAdapter.notifyDataSetChanged();
//                }
            }
        });

        //   MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

        return v;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(title, posts);
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//    }

    public void clearGridView() {
        posts.clear();
    }

//    @Override
//    public void onClick(MainFeed post) {
//        Intent intent = new Intent(getActivity(), DetailsActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        intent.putExtra("POST_ITEM", post);
//        getActivity().startActivity(intent);
//    }

    @Override
    public void onClick(int position) {

        //  MainFeed.Feed selectedPost = mPostAdapter.getSelectedPost(position);
        //  Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        //  intent.putExtra("posts", selectedPost);
        //  startActivity(intent);
    }
}
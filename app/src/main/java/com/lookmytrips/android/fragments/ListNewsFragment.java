package com.lookmytrips.android.fragments;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lookmytrips.android.R;
import com.lookmytrips.android.activity.DetailActivity;
import com.lookmytrips.android.adapters.PostAdapter;
import com.lookmytrips.android.helper.Constants;
import com.lookmytrips.android.model.GeoHr;
import com.lookmytrips.android.pojo.Cat;
import com.lookmytrips.android.pojo.Geo;
import com.lookmytrips.android.pojo.CardImages;
import com.lookmytrips.android.pojo.Places;
import com.lookmytrips.android.utils.Utils;
import com.lookmytrips.android.utils.AppController;
import com.lookmytrips.android.utils.SpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.ScaleInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

import static com.vk.sdk.VKUIHelper.getApplicationContext;

public class ListNewsFragment extends Fragment implements PostAdapter.PostClickListener{

    int l = 1;
    LinearLayoutManager mLayoutManager;
    String title;
    ProgressBar progressBar;
    private String category;
    private RecyclerView mRecyclerView;
    private ArrayList<Post> posts = new ArrayList<>();
    private PostAdapter mPostAdapter;
    private Post post;
    private SwipeRefreshLayout swipeContainer;

    public ListNewsFragment() {
    }

    public static Fragment newInstance(String url) {

        ListNewsFragment myFragment = new ListNewsFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getArguments().getString("url");
    }

    private void getFeed(String category_url) {

        //    if (AppStatus.getInstance(getActivity()).isOnline(getActivity())) {
        progressBar.setVisibility(View.VISIBLE);
        category_url = category;

        JsonObjectRequest eventsRequest = new JsonObjectRequest(Request.Method.GET,
                category_url, (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject obj = response.getJSONObject("items");
                    JSONArray feedArray = obj.getJSONArray("feed");

                    for (int i = 0; i < feedArray.length(); i++) {
                        post = new Post();
                        ArrayList<Places> places = new ArrayList<Places>();
                        ArrayList<Geo> geos = new ArrayList<Geo>();
                        ArrayList<Cat> cats = new ArrayList<Cat>();

                        JSONObject jsonObject = feedArray.getJSONObject(i);
                        String id = jsonObject.getString("_id");
                        String owner = jsonObject.getString("Owner");
                        String likes = jsonObject.getString("Likes");
                        String htmlTitle = jsonObject.getString("Title");
                        String title = Html.fromHtml(htmlTitle).toString();

                        String wasHere = jsonObject.getString("WasHere");
                        String shares = jsonObject.getString("Shares");
                        int rating = jsonObject.getInt("Rating");
                        String comments = jsonObject.getString("Comments");

                        String type = jsonObject.getString("Type");
                        if (type.equals("image")){
                            parseImage(jsonObject);
                        } else if (type.equals("article")) {
                            parseArticle(jsonObject);

                        }

                        post.setId(id);
                        post.setLikes(likes);
                        post.setOwner(owner);

                        post.setTitle(title);
                        post.setWasHere(wasHere);
                        post.setShares(shares);
                        post.setRating(rating);
                        post.setComments(comments);

                        JSONArray placesArray = jsonObject.getJSONArray("Places");
                        for (int p = 0; p < placesArray.length(); p++) {

                            JSONObject jsonPlObject = placesArray.getJSONObject(p);
                            String placesType = jsonPlObject.getString("type");
                            String placesName = jsonPlObject.getString("name");

                            places.add(new Places(placesType, placesName));

                        }

                        JSONArray geoArray = jsonObject.getJSONArray("Geo");
                        for (int j = 0; j < geoArray.length(); j++) {
                            String lat = geoArray.get(0).toString();
                            String lon = geoArray.get(1).toString();
                            geos.add(new Geo(lat, lon));
                        }
//
//
                        JSONArray catArray = jsonObject.getJSONArray("Cat");
                        for (int c = 0; c < catArray.length(); c++) {
                            String catTitle = catArray.get(c).toString();
                            cats.add(new Cat(catTitle));
                        }

                        post.setPlacesList(places);
                        post.setGeoList(geos);
                        post.setCatList(cats);

                        posts.add(post);

                    }
                    mPostAdapter.notifyDataSetChanged();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                for (int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);

//                    SaveIntoDatabase task = new SaveIntoDatabase();
//                    task.execute(flower);

                    try {

                        JSONObject obj = response.getJSONObject("items");

                        JSONArray feedArray = obj.getJSONArray("feed");

                        JSONObject userObj = obj.getJSONObject("users");

                    for (int j = 0; j < userObj.length(); j++) {
                        JSONObject detailedUserObj = userObj.getJSONObject(post.getOwner());

                        String avatar = detailedUserObj.getString("Avatar");
                        String anguage = detailedUserObj.getString("Language");
                        String name = detailedUserObj.getString("Name");
                        String role = detailedUserObj.getString("Role");

                        if (avatar.contains("null")){
                            avatar ="http://img.lookmytrips.com/images/look62lj/57017057ff936741ad022f85-1bg2s2n.jpg";
                        }

                        post.setAvatar(avatar);
                        post.setUserName(name);
                    }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                    mPostAdapter.addPost(post);
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

        View v = inflater.inflate(R.layout.fragment_recyclerview, conteiner, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));


        //test animation
        mRecyclerView.setItemAnimator(new ScaleInLeftAnimator());

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar_news);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        posts = new ArrayList();
        mPostAdapter = new PostAdapter(this);

        mRecyclerView.setAdapter(mPostAdapter);

        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);

        // Setup refresh listener which triggers new data loading

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {

                fetchTimelineAsync(0);

            }

        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, visibleItemCount, totalItemCount;
            private int previousTotal = 0;
            private boolean loading = true;
            private int visibleThreshold = 5;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached
                    loading = true;

                    l++;
                    category = category + "30";
                    getFeed(category);
                    mPostAdapter.notifyDataSetChanged();
                }
            }
        });

        return v;
    }

    private void parseImage(JSONObject object) throws JSONException {
        JSONObject images = object.getJSONObject("Images");
        String image = images.getString("image");
        String thumb = images.getString("thumb");
        String big = images.getString("big");

        JSONObject geoHr = object.getJSONObject("GeoHr");
        String country = geoHr.getString("country");
        String city = geoHr.getString("city");
        String state = geoHr.getString("state");
        String street = geoHr.getString("street");

        post.setImage(image);
        post.setBig(big);
        post.setThumb(thumb);
        post.setGeoHrCountry(country);
        post.setGeoHrCity(city);
        post.setGeoHrState(state);
        post.setGeoHrStreet(street);

    }

    private void parseArticle(JSONObject object) throws JSONException {

        ArrayList<CardImages> cardImages = new ArrayList<>();
        ArrayList<GeoHr> geoHrs = new ArrayList<>();
        String country = "";
        String city = "";

        String cover = object.getString("Cover");
        JSONArray imagesA = object.getJSONArray("Images");
        for (int ob = 0; ob < imagesA.length(); ob++) {
            JSONObject jsonArrayImg = imagesA.getJSONObject(ob);
            String imTitle = jsonArrayImg.getString("title");
            String imBig = jsonArrayImg.getString("big");
            String imUrl = jsonArrayImg.getString("url");

            post.setThumb(cover);
            cardImages.add(new CardImages(imTitle, "", "", imBig, imUrl));
        }

        JSONObject geoHr = object.getJSONObject("GeoHr");
        JSONArray geoHrCountries = geoHr.getJSONArray("countries");
        for (int i = 0; i < geoHrCountries.length(); i++) {
            country = geoHrCountries.get(0).toString();

        }

        JSONArray geoHrCities = geoHr.getJSONArray("countries");
        for (int i = 0; i < geoHrCities.length(); i++) {
            city = geoHrCities.get(0).toString();

        }

        geoHrs.add(new GeoHr(country, city));
        post.setGeoHrCity(city);
        post.setGeoHrCountry(country);

        }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadFeed();

    }

    private void loadFeed() {
        mPostAdapter.reset();

   //     if (getNetworkAvailability()) {
            getFeed(category);
   //     } else {
      //      getFeedFromDatabase();
     //   }
    }

    public boolean getNetworkAvailability() {
        return Utils.isNetworkAvailable(getApplicationContext());
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(title, posts);
    }

    @Override
    public void onResume() {
        super.onResume();

  //      getView().setFocusableInTouchMode(true);
        getView().requestFocus();
    }
    @Override
    public void onClick(int position) {
        Post selectedPost = mPostAdapter.getSelectedPost(position);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(Constants.REFERENCE.POST, selectedPost);
        startActivity(intent);
    }

    public void fetchTimelineAsync(int page) {
        loadFeed();
        swipeContainer.setRefreshing(false);

    }

}


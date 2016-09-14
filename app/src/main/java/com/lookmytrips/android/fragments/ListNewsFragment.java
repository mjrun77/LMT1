package com.lookmytrips.android.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.lookmytrips.android.model.User;
import com.lookmytrips.android.pojo.Cat;
import com.lookmytrips.android.pojo.Geo;
import com.lookmytrips.android.pojo.Places;
import com.lookmytrips.android.utils.Utils;
import com.lookmytrips.android.utils.AppController;
import com.lookmytrips.android.utils.SpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.vk.sdk.VKUIHelper.getApplicationContext;

public class ListNewsFragment extends Fragment implements PostAdapter.PostClickListener{

    int l = 1;
    LinearLayoutManager mLayoutManager;
    String title;
    String url;
    ProgressBar progressBar;
    private String category;
    private RecyclerView mRecyclerView;
  //  private RecyclerView.Adapter mAdapter;
    private ArrayList<Post> posts = new ArrayList<>();
    private PostAdapter mPostAdapter;
    private Post post;
    private ArrayList<User> users = new ArrayList<>();


    private ProgressDialog mDialog;

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
//        title = getArguments().getString("title");
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
                        post.setThumb(thumb);
                        post.setTitle(title);
                        post.setWasHere(wasHere);
                        post.setShares(shares);
                        post.setRating(rating);
                        post.setComments(comments);
                        post.setGeoHrCountry(country);
                        post.setGeoHrCity(city);
                        post.setGeoHrState(state);
                        post.setGeoHrStreet(street);


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
              //  mPostAdapter.notifyDataSetChanged();
                mDialog.dismiss();

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
       // mAdapter = new PostAdapter(this, posts);
        mPostAdapter = new PostAdapter(this);

        mRecyclerView.setAdapter(mPostAdapter);

     //   getFeed(category);

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
//                    getFeed(category);
//                    mAdapter.notifyDataSetChanged();
//                }
            }
        });

        //   MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadFeed();

    }

    private void loadFeed() {

        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Loading Flower Data...");
        mDialog.setCancelable(true);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setIndeterminate(true);

        mPostAdapter.reset();

        mDialog.show();

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
        Post selectedPost = mPostAdapter.getSelectedPost(position);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(Constants.REFERENCE.POST, selectedPost);
        startActivity(intent);
    }
}
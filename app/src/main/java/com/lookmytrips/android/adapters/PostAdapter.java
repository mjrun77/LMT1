package com.lookmytrips.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.lookmytrips.android.R;
import com.lookmytrips.android.fragments.Post;
import com.lookmytrips.android.model.MainFeed;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.Holder>{

    Context context;
    private List<Post> mPosts;
    private List <MainFeed.Users> users;
    PostClickListener mListener;
    private LinkedTreeMap <String, String> mapa;

    public PostAdapter(Context  context, PostClickListener listener){
        this.context = context;
        mPosts = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Post currPost = mPosts.get(position);
     //   MainFeed.Feed.Images images = mPosts.get(position) ;

      //  users = users.get(position);





//
//        if (city.equals("null")){
//            city = "";
//        }
//
//        if (country.equals("null")){
//            country = "";
//        }


//        Post currPost = mPosts.get(position);
//        holder.mTitle.setText(currPost.getTitle());
//
//        holder.mTitle.setText(currPost.getTitle());
//        holder.mTvLikes.setText(currPost.getLikes());
//        holder.mTvComments.setText(currPost.getComments());
//        holder.mTvFavorities.setText("soon");
//        holder.mTvShares.setText(currPost.getShares());
//        holder.mPlace.setText(currPost.getGeoHrCountry() + ", " + currPost.getGeoHrCity());
//        holder.mTvComments.setText(currPost.getComments());
//
//        Picasso.with(context).load(currPost.getThumb()).into(holder.mImage);

        holder.mTitle.setText(currPost.getTitle());
        holder.mTvLikes.setText(currPost.getLikes());
        holder.mTvComments.setText(currPost.getComments());
        holder.mTvFavorities.setText("soon");
        holder.mTvShares.setText(currPost.getShares());
    //    holder.mPlace.setText(country + ", " + city);
        holder.mTvComments.setText(currPost.getComments());

    //    Object staff = currPost.getImages().toString();
     //    Object mapa =  currPost.images;


        String m = "http://img.lookmytrips.com/images/look4qod/thumb-579f4caaff93677b4d0112b5-57a0d644d1d25-1bq1li5.jpg";


        Type listType = new TypeToken<ArrayList<MainFeed.Feed>>(){}.getType();

//        List<MainFeed.Feed> yourClassList = new Gson().fromJson(String.valueOf(currPost.images), listType);

        Log.i("fffffffffff", m);

     //    m = currPost.getCover().toString();

    //  m =  currPost.cover.;




     //   String m = currPost.cover.toString();

        Picasso.with(context).load(m).into(holder.mImage);


    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public void addPost(Post post){
        mPosts.add(post);
        notifyDataSetChanged();
    }

    public Post getSelectedPost(int position) {
        return mPosts.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle, mTvLikes, mPlace, mTvComments, mTvFavorities, mTvShares;
        private ImageView mImage;

        public Holder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.cardTitle);
            mPlace = (TextView) itemView.findViewById(R.id.tv_card_place);
            mTvLikes = (TextView) itemView.findViewById(R.id.tv_card_like_count);
            mTvFavorities = (TextView) itemView.findViewById(R.id.tv_card_favorite_count);
            mTvComments = (TextView) itemView.findViewById(R.id.tv_card_comments_count);
            mTvShares = (TextView) itemView.findViewById(R.id.tv_card_share_count);
            mImage = (ImageView) itemView.findViewById(R.id.bg_image);
      itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(getLayoutPosition());
        }
    }

    public interface PostClickListener{
        void onClick(int position);
    }
}

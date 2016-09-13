package com.lookmytrips.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.lookmytrips.android.R;
import com.lookmytrips.android.fragments.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.Holder>{

    Context context;
    private List<Post> posts;
    PostClickListener mListener;

//    public PostAdapter(PostClickListener listener, ArrayList<Post> posts){
//        this.context = context;
//        this.posts = posts;
//        mListener = listener;
//    }

    public PostAdapter(PostClickListener clickListener){
        posts = new ArrayList<>();
        this.mListener = clickListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Post currPost = posts.get(position);
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


  //      Type listType = new TypeToken<ArrayList<MainFeed.Feed>>(){}.getType();

//        List<MainFeed.Feed> yourClassList = new Gson().fromJson(String.valueOf(currPost.images), listType);

        Log.i("fffffffffff", m);

        Picasso.with(holder.itemView.getContext()).load(m).into(holder.mImage);


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void addPost(Post post){
        posts.add(post);
        notifyDataSetChanged();
    }

    public Post getSelectedPost(int position) {
        return posts.get(position);
    }

    public void reset() {
        posts.clear();
        notifyDataSetChanged();
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

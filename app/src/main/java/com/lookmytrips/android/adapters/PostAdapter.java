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

    private List<Post> posts;
    PostClickListener mListener;

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
        holder.mPlace.setText(currPost.getGeoHrCountry() + ", " + currPost.getGeoHrCity());
        holder.mTvComments.setText(currPost.getComments());
        holder.mTvUserName.setText(currPost.getUserName());

        Picasso.with(holder.itemView.getContext()).load(currPost.getThumb()).into(holder.mImage);

        String avatar;
        avatar = currPost.getAvatar();

        if (currPost.getAvatar().equals("null")){
            avatar ="http://img.lookmytrips.com/images/look62lj/57017057ff936741ad022f85-1bg2s2n.jpg";
        }

        Picasso.with(holder.itemView.getContext()).load(avatar).into(holder.mAvatar);

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

        private TextView mTitle, mTvLikes, mPlace, mTvComments, mTvFavorities, mTvShares, mTvUserName;
        private ImageView mImage, mAvatar;

        public Holder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.cardTitle);
            mPlace = (TextView) itemView.findViewById(R.id.tv_card_place);
            mTvLikes = (TextView) itemView.findViewById(R.id.tv_card_like_count);
            mTvFavorities = (TextView) itemView.findViewById(R.id.tv_card_favorite_count);
            mTvComments = (TextView) itemView.findViewById(R.id.tv_card_comments_count);
            mTvShares = (TextView) itemView.findViewById(R.id.tv_card_share_count);
            mImage = (ImageView) itemView.findViewById(R.id.bg_image);
            mAvatar = (ImageView) itemView.findViewById(R.id.card_avatar);
            mTvUserName = (TextView) itemView.findViewById(R.id.card_user_name);
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

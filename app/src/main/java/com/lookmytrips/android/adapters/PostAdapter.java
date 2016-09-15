package com.lookmytrips.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lookmytrips.android.R;
import com.lookmytrips.android.fragments.Post;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import at.grabner.circleprogress.CircleProgressView;

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
        holder.mCircleView.setValue(currPost.getRating());

        Picasso.with(holder.itemView.getContext()).load(currPost.getThumb()).into(holder.mImage);
        Picasso.with(holder.itemView.getContext()).load(currPost.getAvatar()).error(R.drawable.ic_account_circle).noFade().placeholder(R.drawable.ic_account_circle).into(holder.mAvatar);
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
        private ImageView mImage;
        private CircularImageView mAvatar;
        private CircleProgressView mCircleView;

        public Holder(View itemView) {
            super(itemView);

            mCircleView = (CircleProgressView) itemView.findViewById(R.id.circleView);
            mCircleView.setMaxValue(100);
            mCircleView.setBarWidth(10);
            mCircleView.setRimWidth(10);
            mCircleView.setRimColor(itemView.getContext().getResources().getColor(R.color.light_grey));
            mCircleView.setContourColor(itemView.getContext().getResources().getColor(R.color.light_grey));
            mCircleView.setBarColor(itemView.getContext().getResources().getColor(R.color.green));
            mCircleView.setUnitVisible(true);
            mCircleView.setUnit("%");
//          mCircleView.setUnitColor();
//          mCircleView.setValueAnimated(24);

//          mCircleView.setTextSize(90); // text size set, auto text size off
//          mCircleView.setUnitSize(90); // if i set the text size i also have to set the unit size
            mCircleView.setAutoTextSize(true); // enable auto text size, previous values are overwritten
            //if you want the calculated text sizes to be bigger/smaller you can do so via
//          mCircleView.setUnitScale(0.9f);
//          mCircleView.setTextScale(0.9f);

            mTitle = (TextView) itemView.findViewById(R.id.cardTitle);
            mPlace = (TextView) itemView.findViewById(R.id.tv_card_place);
            mTvLikes = (TextView) itemView.findViewById(R.id.tv_card_like_count);
            mTvFavorities = (TextView) itemView.findViewById(R.id.tv_card_favorite_count);
            mTvComments = (TextView) itemView.findViewById(R.id.tv_card_comments_count);
            mTvShares = (TextView) itemView.findViewById(R.id.tv_card_share_count);
            mImage = (ImageView) itemView.findViewById(R.id.bg_image);
            mAvatar = (CircularImageView) itemView.findViewById(R.id.card_avatar);
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

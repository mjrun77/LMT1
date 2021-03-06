package com.lookmytrips.android.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.listener.OnPostingCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;
import com.lookmytrips.android.R;
import com.lookmytrips.android.model.User;
import com.lookmytrips.android.utils.Constants;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements OnRequestSocialPersonCompleteListener {
    private String message = "Check this:";
    private String link = "http://lookmytrips.com";

    private static final String NETWORK_ID = "NETWORK_ID";
    private SocialNetwork socialNetwork;
    private int networkId;
    private ImageView photo;
    private TextView name;
    private TextView id;
    private TextView info;
    private Button friends;
    private Button share;
    private RelativeLayout frame;
    private SharedPreferences pref;

    public static ProfileFragment newInstannce(int id) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(NETWORK_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        networkId = getArguments().containsKey(NETWORK_ID) ? getArguments().getInt(NETWORK_ID) : 0;
        pref = getActivity().getSharedPreferences(Constants.PREFERENCES_LOGIN, 0);
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);

        frame = (RelativeLayout) rootView.findViewById(R.id.frame);
        photo = (ImageView) rootView.findViewById(R.id.imageView);
        name = (TextView) rootView.findViewById(R.id.name);
        id = (TextView) rootView.findViewById(R.id.id);
        info = (TextView) rootView.findViewById(R.id.info);
        friends = (Button) rootView.findViewById(R.id.friends);
        friends.setOnClickListener(friendsClick);
        share = (Button) rootView.findViewById(R.id.share);
        share.setOnClickListener(shareClick);
        colorProfile(networkId);

        socialNetwork = LoginFragment.mSocialNetworkManager.getSocialNetwork(networkId);
        socialNetwork.setOnRequestCurrentPersonCompleteListener(this);
        socialNetwork.requestCurrentPerson();

        LoginActivity.showProgress("Loading");
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.login, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                socialNetwork.logout();
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestSocialPersonSuccess(int i, SocialPerson socialPerson) {
        LoginActivity.hideProgress();

        User user = new User();
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN, true);
        editor.putString(Constants.AVATAR, socialPerson.avatarURL);
        editor.putString(Constants.USER_ID, socialPerson.id);
        editor.putString(Constants.NAME, socialPerson.name);
        editor.apply();

        name.setText(socialPerson.name);
        id.setText(socialPerson.id);
        String socialPersonString = socialPerson.toString();
        String infoString = socialPersonString.substring(socialPersonString.indexOf("{")+1, socialPersonString.lastIndexOf("}"));
        info.setText(infoString.replace(", ", "\n"));
        Picasso.with(getActivity())
                .load(socialPerson.avatarURL)
                .into(photo);
    }

    @Override
    public void onError(int networkId, String requestID, String errorMessage, Object data) {
        LoginActivity.hideProgress();
        Toast.makeText(getActivity(), "ERROR: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    private View.OnClickListener friendsClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FriendsFragment friends = FriendsFragment.newInstannce(networkId);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack("friends")
                    .replace(R.id.container, friends)
                    .commit();
        }
    };

    private View.OnClickListener shareClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder ad = alertDialogInit("Would you like to post Link:", link);
            ad.setPositiveButton("Post link", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Bundle postParams = new Bundle();
                    postParams.putString(SocialNetwork.BUNDLE_NAME, "Simple and easy share with friends");
                    postParams.putString(SocialNetwork.BUNDLE_LINK, link);
                    if(networkId == GooglePlusSocialNetwork.ID) {
                        socialNetwork.requestPostDialog(postParams, postingComplete);
                    } else {
                        socialNetwork.requestPostLink(postParams, message, postingComplete);
                    }
                }
            });
            ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });
            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    dialog.cancel();
                }
            });
            ad.create().show();
        }
    };

    private OnPostingCompleteListener postingComplete = new OnPostingCompleteListener() {
        @Override
        public void onPostSuccessfully(int socialNetworkID) {
            Toast.makeText(getActivity(), "Sent", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
            Toast.makeText(getActivity(), "Error while sending: " + errorMessage, Toast.LENGTH_LONG).show();
        }
    };

    private void colorProfile(int networkId){
        int color = getResources().getColor(R.color.dark);
        int image = R.drawable.user;
        switch (networkId) {
            case GooglePlusSocialNetwork.ID:
                color = getResources().getColor(R.color.colorAccent);
                image = R.drawable.user;
                break;
            case FacebookSocialNetwork.ID:
                color = getResources().getColor(R.color.colorPrimary);
                image = R.drawable.com_facebook_profile_picture_blank_square;
                break;
        }
        frame.setBackgroundColor(color);
        name.setTextColor(color);
        friends.setBackgroundColor(color);
        share.setBackgroundColor(color);
        photo.setBackgroundColor(color);
        photo.setImageResource(image);
    }

    private AlertDialog.Builder alertDialogInit(String title, String message){
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        ad.setTitle(title);
        ad.setMessage(message);
        ad.setCancelable(true);
        return ad;
    }
}

package com.lookmytrips.android.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;
import com.github.gorbin.asne.vk.VkSocialNetwork;
import com.lookmytrips.android.R;
import com.lookmytrips.android.model.User;
import com.lookmytrips.android.utils.Constants;
import com.vk.sdk.VKScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginFragment extends Fragment implements SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener {
    public static SocialNetworkManager mSocialNetworkManager;

    private Button facebook;
    private Button vk;
    private Button odnoklassniki;
    private Button googleplus;
    private TextView tvLookWithout;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        facebook = (Button) rootView.findViewById(R.id.facebook);
        facebook.setOnClickListener(loginClick);
        vk = (Button) rootView.findViewById(R.id.vk);
        vk.setOnClickListener(loginClick);
        tvLookWithout = (TextView) rootView.findViewById(R.id.tv_without_auth);
        tvLookWithout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
//        odnoklassniki = (Button) rootView.findViewById(R.id.ok);
//        odnoklassniki.setOnClickListener(loginClick);
        googleplus = (Button) rootView.findViewById(R.id.googleplus);
        googleplus.setOnClickListener(loginClick);

        //Get Keys for initiate SocialNetworks
        String VK_KEY = getActivity().getString(R.string.vk_app_id);

        //Chose permissions
        ArrayList<String> fbScope = new ArrayList<String>();
        fbScope.addAll(Arrays.asList("public_profile, email, user_friends"));

        //Use manager to manage SocialNetworks
        mSocialNetworkManager = (SocialNetworkManager) getFragmentManager().findFragmentByTag(LoginActivity.SOCIAL_NETWORK_TAG);

        //Check if manager exist
        if (mSocialNetworkManager == null) {
            mSocialNetworkManager = new SocialNetworkManager();

            //Init and add to manager FacebookSocialNetwork
            FacebookSocialNetwork fbNetwork = new FacebookSocialNetwork(this, fbScope);
            mSocialNetworkManager.addSocialNetwork(fbNetwork);

            //Init and add to manager TwitterSocialNetwork
            String[] vkScope = new String[] {
                    VKScope.FRIENDS,
                    VKScope.WALL,
                    VKScope.PHOTOS,
                    VKScope.NOHTTPS,
                    VKScope.STATUS,
            };
            VkSocialNetwork vkNetwork = new VkSocialNetwork(this, VK_KEY, vkScope);
            mSocialNetworkManager.addSocialNetwork(vkNetwork);

            GooglePlusSocialNetwork gpNetwork = new GooglePlusSocialNetwork(this);
            mSocialNetworkManager.addSocialNetwork(gpNetwork);

            //Initiate every network from mSocialNetworkManager
            getFragmentManager().beginTransaction().add(mSocialNetworkManager, LoginActivity.SOCIAL_NETWORK_TAG).commit();
            mSocialNetworkManager.setOnInitializationCompleteListener(this);
        } else {
            //if manager exist - get and setup login only for initialized SocialNetworks
            if(!mSocialNetworkManager.getInitializedSocialNetworks().isEmpty()) {
                List<SocialNetwork> socialNetworks = mSocialNetworkManager.getInitializedSocialNetworks();
                for (SocialNetwork socialNetwork : socialNetworks) {
                    socialNetwork.setOnLoginCompleteListener(this);
                }
            }
        }
        return rootView;
    }

    @Override
    public void onSocialNetworkManagerInitialized() {
        //when init SocialNetworks - get and setup login only for initialized SocialNetworks
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);

        }
    }

    //Login listener

    private View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int networkId = 0;
            switch (view.getId()){
                case R.id.facebook:
                    networkId = FacebookSocialNetwork.ID;
                    break;
                case R.id.vk:
                    networkId = VkSocialNetwork.ID;
                    break;
//                case R.id.odnoklassniki:
//                    networkId = LinkedInSocialNetwork.ID;
//                    break;
                case R.id.googleplus:
                    networkId = GooglePlusSocialNetwork.ID;
                    break;
            }
            SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);
            if(!socialNetwork.isConnected()) {
                if(networkId != 0) {
                    socialNetwork.requestLogin();
                    LoginActivity.showProgress("Loading");
                } else {
                    Toast.makeText(getActivity(), "Wrong networkId", Toast.LENGTH_LONG).show();
                }
            } else {
                startProfile(socialNetwork.getID());
            }
        }
    };

    @Override
    public void onLoginSuccess(int networkId) {
        LoginActivity.hideProgress();
        startProfile(networkId);

    }

    @Override
    public void onError(int networkId, String requestID, String errorMessage, Object data) {
        LoginActivity.hideProgress();
        Toast.makeText(getActivity(), "ERROR: " + errorMessage, Toast.LENGTH_LONG).show();
    }

    private void startProfile(int networkId){
        ProfileFragment profile = ProfileFragment.newInstannce(networkId);
        getActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack("profile")
                .replace(R.id.container, profile)
                .commit();

        startActivity(new Intent(getActivity(), MainActivity.class));
    }

}

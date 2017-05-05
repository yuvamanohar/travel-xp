package com.tgear.travelxp;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import adapters.AlbumGridAdapter;
import config.UserConfig;
import models.ProfileData;
import network.controllers.ProfileController;
import util.SLogger;

public class ProfileActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private AlbumGridAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.albumsList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager

        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new AlbumGridAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

//        if(UserConfig.get().getProfileData() != null)  {
//            initializeView(UserConfig.get().getProfileData()) ;
//            mAdapter.setAlbumList(UserConfig.get().getProfileData().albums,
//                                    UserConfig.get().getProfileData().orphanedPosts);
//        } else {
            new ProfileController(this).getProfileInfo(UserConfig.get().getUserId());
//        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPostSuccess(ProfileData profileData) {
        SLogger.PROFILE_DATA.i(profileData.toJson()) ;

        if(profileData.user.userId.equals(UserConfig.get().getUser().userId)) {
            UserConfig.get().setProfileData(profileData) ;
        }

        initializeView(profileData) ;
        initializeAlbums(profileData) ;
    }

    private void initializeView(ProfileData profileData) {
        Glide.with(this).load(profileData.user.socialProfile.profilePic).into((ImageView)findViewById(R.id.profilePic));
        ((TextView) findViewById(R.id.profileName)).setText(profileData.user.socialProfile.completeName);
        ((TextView) findViewById(R.id.profileDesc)).setText(profileData.user.aboutMe);
    }

    private void initializeAlbums(ProfileData profileData) {
        mAdapter.setAlbumList(profileData.albums, profileData.orphanedPosts);
        mAdapter.notifyDataSetChanged();
    }
}

package com.tgear.travelxp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import adapters.AlbumGridAdapter;
import adapters.AlbumPostAdapter;
import adapters.PostAdapter;
import config.LoadedFeed;
import config.UserConfig;

public class ViewAlbumActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Long userId ;
    private Long albumId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_album);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userId = getIntent().getLongExtra(AlbumGridAdapter.USER_ID, 0) ;
        albumId = getIntent().getLongExtra(AlbumGridAdapter.ALBUM_ID, 0) ;

        mRecyclerView = (RecyclerView) findViewById(R.id.albumPostListView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new AlbumPostAdapter(this, LoadedFeed.getInstance());
        mRecyclerView.setAdapter(mAdapter);

        if(!UserConfig.get().isMyUserId(userId)) {

        } else {

        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}

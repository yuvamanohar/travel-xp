package com.tgear.travelxp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import adapters.FeedAdapter;
import config.LoadedFeed;
import config.UserConfig;
import models.PartialFeed;
import models.Post;
import network.controllers.FeedController;
import util.DateFormatter;

public class FeedActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private FeedAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final int REQUEST_WRITE_PERMISSION = 786;
    private static final int RESULT_GALLERY = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        mRecyclerView = (RecyclerView) findViewById(R.id.postListView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new FeedAdapter(this, LoadedFeed.getInstance());
        mRecyclerView.setAdapter(mAdapter);
        new FeedController(this).getOlderFeed(UserConfig.getInstance().getUser().userId, DateFormatter.getReadableCurrentTime());
    }

    public void onGalleryButtonTap(View v) {
        // Check permissions for Gallery access

        // Here, is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

  //          } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_PERMISSION);

                // REQUEST_WRITE_PERMISSION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
   //         }
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    openGallery();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private static Uri imageUri ;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_GALLERY :
                if (null != data) {
                    imageUri = data.getData();
                    openPostActivity(imageUri) ;
                    //Do whatever that you desire here. or leave this blank

                }
                break;
            default:
                break;
        }
    }

    private void openPostActivity(Uri imageUri) {
        Intent intent = new Intent(this, PostActivity.class) ;
        intent.putExtra("imageUri", imageUri.toString());
        startActivity(intent) ;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFeedUpdate(PartialFeed partialFeed) {
        mAdapter.onFeedUpdate(partialFeed);
    }

}

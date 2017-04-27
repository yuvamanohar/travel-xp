package com.tgear.travelxp;

import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import models.Post;
import models.PostDetail;
import models.UserData;
import network.controllers.PostController;
import util.SLogger;

public class PostActivity extends BaseActivity {
    private Place place ;
    private List<Uri> uris = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Uri uri = Uri.parse(getIntent().getStringExtra("imageUri")) ;
        uris.add(uri) ;

        ImageView imageView = (ImageView) findViewById(R.id.imageView) ;
        imageView.setImageURI(uri);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                SLogger.POST_CONTENT.i("Place: " + place.getName());
                PostActivity.this.place = place ;
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                SLogger.POST_CONTENT.i("An error occurred: " + status);
            }
        });
    }

    public void submit(View v) {
        TextInputEditText scribble = (TextInputEditText) findViewById(R.id.scribble) ;
        String scribbleText = scribble.getText().toString() ;

        if(scribbleText != null && scribbleText.equals(""))
            scribbleText = null ;

        List<PostDetail> postDetails = new ArrayList<>() ;

        // Media type of the request for postContent details
        //MediaType.parse(activity.getContentResolver().getType(fileUri)

        for(Uri uri : uris) {
            postDetails.add(new PostDetail(null, PostDetail.MediaType.PHOTO, uri)) ;
        }


        Post post = new Post(scribbleText, place != null ? place.getLatLng().latitude: null,
                place !=null ? place.getLatLng().longitude: null,
                place != null ? place.getName().toString(): null, postDetails) ;
        new PostController(this).postContent(post);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPostSuccess(Post post) {
//        launchFeedActivity();
    }

}

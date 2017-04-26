package com.tgear.travelxp;

import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.List;

import models.Post;
import models.PostDetail;
import network.INetworkListener;
import network.RequestType;
import network.controllers.PostController;
import okhttp3.ResponseBody;
import util.SLogger;

public class PostActivity extends AppCompatActivity implements INetworkListener<Post> {
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
        new PostController(this, this).postContent(post);
    }

    @Override
    public void handleSuccess(RequestType type, Post object) {
        SLogger.POST_CONTENT.i("Post object is " + object.toJson());
    }

    @Override
    public void handleFailure(RequestType type, ResponseBody responseBody) {
        // retry pop-up
        SLogger.NETWORK_CALL.i("Network call failed for " + type.name());
    }

    @Override
    public void handleError(RequestType type, Throwable t) {
        //error... retry pop-up
        SLogger.NETWORK_CALL.i("Network call failed for " + type.name());
    }
}

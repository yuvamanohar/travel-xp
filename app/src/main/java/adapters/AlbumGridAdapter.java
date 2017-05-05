package adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tgear.travelxp.ViewAlbumActivity;
import com.tgear.travelxp.R;
import com.tgear.travelxp.ViewPostActivity;

import java.util.ArrayList;
import java.util.List;

import models.Album;
import models.Post;
import util.SLogger;

/**
 * Created by yuva on 21/4/17.
 */

public class AlbumGridAdapter extends RecyclerView.Adapter<AlbumGridAdapter.AlbumViewHolder> {

    public static String USER_ID = "userId" ;
    public static String ALBUM_ID = "albumId" ;
    public static String POST_ID = "postId" ;

    private Activity activity;
    private List<Album> albumList ;
    private List<Post> orphanedPostList ;

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail ;
        public TextView albumName ;
//        public TextView location ;
//        public TextView likes ;
//        public TextView comments ;
//        public TextView shares ;

        public AlbumViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.albumThumbnail);
            albumName = (TextView) view.findViewById(R.id.albumName);
//            location = (TextView) view.findViewById(R.id.location);
//            likes = (TextView) view.findViewById(R.id.likes);
//            comments = (TextView) view.findViewById(R.id.comments);
//            shares = (TextView) view.findViewById(R.id.shares) ;
        }
    }

    public AlbumGridAdapter(Activity activity) {
        this.activity = activity ;
        this.albumList = new ArrayList<>();
        this.orphanedPostList = new ArrayList<>() ;
    }

    public void setAlbumList(List<Album> albumList, List<Post> orphanedPostList) {
        this.albumList = albumList ;
        this.orphanedPostList = orphanedPostList ;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_thumbnail, parent, false);

        return new AlbumViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AlbumViewHolder holder, int position) {
        if(position < albumList.size()) {
            final Album album = albumList.get(position);
            holder.albumName.setText(album.name);
            Glide.with(activity).load(album.posts.get(0).postDetails.get(0).getCdnMedia()).into(holder.thumbnail);
            holder.thumbnail.setTag(album.albumId);
            holder.thumbnail.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Long albumId = (Long) ((ImageView) v).getTag() ;
                    SLogger.ALBUM_THUMBNAIL.d("Album clicked is : " + albumId);
                    Intent intent = new Intent(activity, ViewAlbumActivity.class) ;
                    intent.putExtra(USER_ID, album.userId) ;
                    intent.putExtra(ALBUM_ID, albumId) ;
                    activity.startActivity(intent);
                }
            });
        } else {
            final Post post = orphanedPostList.get(position - albumList.size()) ;
            holder.albumName.setText(post.location);
            Glide.with(activity).load(post.postDetails.get(0).getCdnMedia()).into(holder.thumbnail);
            holder.thumbnail.setTag(post.postId) ;

            holder.thumbnail.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Long postId = (Long) ((ImageView) v).getTag() ;
                    SLogger.POST_THUMBNAIL.d("Post clicked is : " + postId);
                    Intent intent = new Intent(activity, ViewPostActivity.class) ;
                    intent.putExtra(USER_ID, post.userId) ;
                    intent.putExtra(POST_ID, postId) ;
                    activity.startActivity(intent);
                }
            });
        }

//        holder.location.setText(album.location);
//        holder.likes.setText("" + album.likes + " likes");
//        holder.comments.setText("" + album.comments + " comments");
//        holder.shares.setText("" + album.shares + " shares");
    }

    @Override
    public int getItemCount() {
        return albumList.size() + orphanedPostList.size() ;
    }
}

package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tgear.travelxp.R;

import java.util.ArrayList;
import java.util.List;

import config.LoadedFeed;
import models.Album;
import models.PartialFeed;
import models.Post;

/**
 * Created by yuva on 21/4/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private Context mContext ;
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

    public AlbumAdapter(Context context) {
        this.mContext = context ;
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
            Album album = albumList.get(position);
            holder.albumName.setText(album.name);
            Glide.with(mContext).load(album.posts.get(0).postDetails.get(0).media).into(holder.thumbnail);
        } else {
            Post post = orphanedPostList.get(position - albumList.size()) ;
            holder.albumName.setText(post.location);
            Glide.with(mContext).load(post.postDetails.get(0).media).into(holder.thumbnail);
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

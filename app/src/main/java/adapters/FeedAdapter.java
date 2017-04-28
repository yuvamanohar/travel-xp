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

import java.util.List;

import config.LoadedFeed;
import models.PartialFeed;
import models.Post;

/**
 * Created by yuva on 21/4/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.PostViewHolder> {

    private Context mContext ;

    private LoadedFeed loadedFeed ;
    private List<Post> postList;

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView name ;
        public ImageView media ;
        public TextView title ;
        public TextView scribble ;
        public TextView location ;
        public TextView likes ;
        public TextView comments ;
        public TextView shares ;

        public PostViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            media = (ImageView) view.findViewById(R.id.media);
            title = (TextView) view.findViewById(R.id.title);
            scribble = (TextView) view.findViewById(R.id.scribble);
            location = (TextView) view.findViewById(R.id.location);
            likes = (TextView) view.findViewById(R.id.likes);
            comments = (TextView) view.findViewById(R.id.comments);
            shares = (TextView) view.findViewById(R.id.shares) ;
        }
    }


    public FeedAdapter(Context context, LoadedFeed loadedFeed) {
        this.mContext = context ;
        this.loadedFeed = loadedFeed ;
        this.postList = loadedFeed.getPosts() ;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post, parent, false);

        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.name.setText(LoadedFeed.getInstance().getUserName(post.userId));
        Glide.with(mContext).load(post.postDetails.get(0).media).into(holder.media);
        holder.scribble.setText(post.scribble);
        holder.location.setText(post.location);
        holder.likes.setText("" + post.likes + " likes");
        holder.comments.setText("" + post.comments + " comments");
        holder.shares.setText("" + post.shares + " shares");

        // loading album cover using Glide library
//        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
//            }
//        });
    }

//    /**
//     * Showing popup menu when tapping on 3 dots
//     */
//    private void showPopupMenu(View view) {
//        // inflate menu
//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_album, popup.getMenu());
//        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
//        popup.show();
//    }

    /**
     * Click listener for popup menu items
     */
//    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
//
//        public MyMenuItemClickListener() {
//        }
//
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_add_favourite:
//                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_play_next:
//                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
//                    return true;
//                default:
//            }
//            return false;
//        }
//    }
//
    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void onFeedUpdate(PartialFeed partialFeed) {
        if(partialFeed.posts.size() == 0)
            return;

        int beginSize = LoadedFeed.getInstance().getPosts().size() ;
        LoadedFeed.getInstance().addPartialFeed(partialFeed);
        switch (partialFeed.feedType) {
            case UPDATED_FEED:
                notifyItemRangeInserted(0, partialFeed.posts.size()) ;
                break;
            case OLDER_FEED:
                notifyItemRangeInserted(beginSize, partialFeed.posts.size()) ;
                break ;
        }
    }
}

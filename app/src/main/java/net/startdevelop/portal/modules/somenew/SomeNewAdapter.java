package net.startdevelop.portal.modules.somenew;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import net.startdevelop.portal.R;
import java.util.List;

public class SomeNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> list;
    private Context context;
    private static final int TYPE_TEXT=0;
    private static final int TYPE_IMAGE=1;
    private static final int TYPE_VIDEO=2;



    public SomeNewAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TYPE_IMAGE){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_somenew_image, parent, false);
            return new ViewHolderImage(itemView);
        } else if (viewType == TYPE_VIDEO){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_somenew_video, parent, false);
            return new ViewHolderVideo(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_somenew_text, parent, false);
            return new ViewHolderText(itemView);
        }
    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final String s = list.get(position);
        switch (getItemViewType(position)){
            case TYPE_TEXT:
                ViewHolderText viewHolderText = (ViewHolderText)holder;
                viewHolderText.text.setText(null);
                viewHolderText.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                viewHolderText.text.setTextColor(context.getResources().getColor(R.color.textSomeNew));
                viewHolderText.text.setGravity(Gravity.LEFT);
                 if (position ==0){
                     viewHolderText.text.setText(s);
                     viewHolderText.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                     viewHolderText.text.setTextColor(context.getResources().getColor(R.color.black));
                     viewHolderText.text.setGravity(Gravity.CENTER);
                 } else if (position == 1){
                     viewHolderText.text.setText(s);
                     viewHolderText.text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                     viewHolderText.text.setTextColor(context.getResources().getColor(R.color.timeSomeNew));
                     viewHolderText.text.setGravity(Gravity.CENTER);
                 } else{
                     viewHolderText.text.setText(s);
                 }
                break;
            case TYPE_IMAGE:
                ViewHolderImage viewHolderImage = (ViewHolderImage) holder;
                viewHolderImage.image.setImageDrawable(null);
                //viewHolderImage.image.setImageDrawable(context.getResources().getDrawable(R.drawable.zagl));
                if (s.contains(".jpg") || s.contains(".jpeg") || s.contains(".gif")){
                    Glide.with(context).load(s).asBitmap().format(DecodeFormat.PREFER_RGB_565).into(viewHolderImage.image);
                } else if (s.contains(".gif")){
                    Glide.with(context).load(s).asGif().into(viewHolderImage.image);
                }
                break;
            case TYPE_VIDEO:
                final String vid=s.substring(30, s.length());
                ViewHolderVideo viewHolderVideo = (ViewHolderVideo) holder;
                viewHolderVideo.onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo(vid);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        Log.d("logs", "onInitializationFailure");
                    }
                };
                viewHolderVideo.video.initialize(YoutubeConfig.API_KEY, viewHolderVideo.onInitializedListener);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).contains(".jpg") ||
                list.get(position).contains(".jpeg") ||
                list.get(position).contains(".png") ||
                list.get(position).contains(".gif")) {
            return TYPE_IMAGE;
        } else if (list.get(position).contains("https://www.youtube.com/embed/")){
            return TYPE_VIDEO;
        } else{
            return TYPE_TEXT;
        }
    }

    class ViewHolderText extends RecyclerView.ViewHolder {
        private TextView text;
        ViewHolderText(View itemView){
            super(itemView);
            this.text = (TextView) itemView.findViewById(R.id.tvSomeNew);
        }
    }

    class ViewHolderImage extends RecyclerView.ViewHolder {
        private ImageView image;
        ViewHolderImage(View itemView){
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.ivSomeNew);
        }
    }

    class ViewHolderVideo extends RecyclerView.ViewHolder {
        private YouTubePlayerView video;
        private YouTubePlayer.OnInitializedListener onInitializedListener;
        ViewHolderVideo(View itemView){
            super(itemView);
            this.video = (YouTubePlayerView) itemView.findViewById(R.id.youtube_player);
        }
    }
}

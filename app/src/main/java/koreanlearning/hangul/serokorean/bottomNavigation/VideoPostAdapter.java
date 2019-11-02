package koreanlearning.hangul.serokorean.bottomNavigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hangul.serokorean.R;

import java.util.ArrayList;

import koreanlearning.hangul.serokorean.model.YoutubeVideoModel;

public class VideoPostAdapter extends RecyclerView.Adapter<VideoPostAdapter.YoutubePostHolder> {
    private ArrayList<YoutubeVideoModel> youtubeList;
    private Context context;

    public VideoPostAdapter(Context context, ArrayList<YoutubeVideoModel> youtubeList) {
        this.context = context;
        this.youtubeList = youtubeList;
    }

    @NonNull
    @Override
    public YoutubePostHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_post_layout, parent, false);
        YoutubePostHolder postHolder = new YoutubePostHolder(view);

        return postHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubePostHolder holder, int i) {
        TextView textviewTitle = holder.textViewTitle;
        TextView textViewDes = holder.textViewDes;
        TextView textViewDate = holder.textViewDate;
        ImageView imageThumbnail = holder.ImageThumb;

        YoutubeVideoModel object = youtubeList.get(i);
        textviewTitle.setText(object.getTitle());
        textViewDes.setText(object.getDescription());
        textViewDate.setText(object.getDate());

        // image will be downloaded from url
        Glide.with(holder.itemView.getContext()).load(object.getThumbnail()).into(holder.ImageThumb);
        String thumbnail = object.getThumbnail();
    }

    @Override
    public int getItemCount() {
        return youtubeList.size();
    }

    public static class YoutubePostHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDes;
        private TextView textViewDate;
        private ImageView ImageThumb;


        public YoutubePostHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewTitle = itemView.findViewById(R.id.video_title);
            this.textViewDes = itemView.findViewById(R.id.video_des);
            this.textViewDate = itemView.findViewById(R.id.vieo_date);
            this.ImageThumb = itemView.findViewById(R.id.youtube_image);
        }
    }
}

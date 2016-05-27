package com.masaila.oink.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.GlidePalette;
import com.masaila.oink.R;
import com.masaila.oink.model.Playlist;

import java.util.List;

/**
 * Created by MASAILA on 16/5/27.
 */
public class DiscoverAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Playlist> mPlaylists;
    private Context mContext;
    private DisplayMetrics mMetrics;


    private static final int NORMAL_TYPE = 1;
    private static final int FOOTER_TYPE = 2;

    public DiscoverAdapter(Context context, List<Playlist> playlists, DisplayMetrics metrics) {
        mPlaylists = playlists;
        mContext = context;
        mMetrics = metrics;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (NORMAL_TYPE == viewType) {
            return new DiscoverHolder(
                    LayoutInflater.from(mContext)
                            .inflate(R.layout.recycler_item_discover_playlist, null, false)
            );
        } else if (FOOTER_TYPE == viewType) {
            return new FooterHolder(
                    LayoutInflater.from(mContext)
                            .inflate(R.layout.recycler_item_discover_loading, null, false)
            );
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DiscoverHolder) {
            DiscoverHolder discoverHolder = (DiscoverHolder) holder;
            discoverHolder.mTextViewTitle.setText(mPlaylists.get(position).getName());
            discoverHolder.mTextViewAuthor.setText(mPlaylists.get(position).getAuthor());
            discoverHolder.mImageView.setMaxHeight(mMetrics.widthPixels / 2);
            discoverHolder.mImageView.setMinimumHeight(mMetrics.widthPixels / 2);

            Glide.with(mContext)
                    .load(mPlaylists.get(position).getAlbum())
                    .crossFade()
                    .listener(GlidePalette.with(mPlaylists.get(position).getAlbum())
                            .use(GlidePalette.Profile.MUTED_DARK)
                            .intoBackground(discoverHolder.mLayoutContent)
                    )
                    .into(discoverHolder.mImageView);
        } else if (holder instanceof FooterHolder) {
            FooterHolder footerHolder = (FooterHolder) holder;
        }

    }

    @Override
    public int getItemCount() {
        return mPlaylists.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mPlaylists.get(position) == null ? FOOTER_TYPE : NORMAL_TYPE;
    }

    class DiscoverHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTextViewTitle;
        private TextView mTextViewAuthor;
        private LinearLayout mLayoutContent;

        public DiscoverHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            mTextViewTitle = (TextView) itemView.findViewById(R.id.textView_title);
            mTextViewAuthor = (TextView) itemView.findViewById(R.id.textView_author);
            mLayoutContent = (LinearLayout) itemView.findViewById(R.id.layout_content);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View itemView) {
            super(itemView);
        }

    }
}
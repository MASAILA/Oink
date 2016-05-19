package com.masaila.oink.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.masaila.oink.api.APIManager;
import com.masaila.oink.model.AllPlaylist;
import com.masaila.oink.model.Playlist;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DiscoverFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<Playlist> mPlaylists;
    private DiscoverAdapter mAdapter;

    DisplayMetrics mMetrics;

    public DiscoverFragment() {
    }

    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, view);

        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

        APIManager.getInstance()
                .getAllPlaylist()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mGetAllPlaylistSubscriber);

        return view;
    }

    Subscriber<AllPlaylist> mGetAllPlaylistSubscriber = new Subscriber<AllPlaylist>() {
        @Override
        public void onCompleted() {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(AllPlaylist allPlaylist) {
            mPlaylists = new ArrayList<>();
            mPlaylists.clear();
            mPlaylists.addAll(allPlaylist.getData());
            mAdapter = new DiscoverAdapter(getContext(), mPlaylists);
        }
    };

    class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverHolder> {

        private List<Playlist> mPlaylists;
        private Context mContext;

        public DiscoverAdapter(Context context, List<Playlist> playlists) {
            mPlaylists = playlists;
            mContext = context;
        }

        @Override
        public DiscoverHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DiscoverHolder(
                    LayoutInflater.from(mContext)
                            .inflate(R.layout.recycler_item_discover_playlist, null, false)
            );
        }

        @Override
        public void onBindViewHolder(final DiscoverHolder holder, int position) {
            holder.mTextViewTitle.setText(mPlaylists.get(position).getName());
            holder.mTextViewAuthor.setText(mPlaylists.get(position).getAuthor());
            holder.mImageView.setMaxHeight(540);
            Glide.with(getContext())
                    .load(mPlaylists.get(position).getAlbum())
                    .override(540, 540)
                    .centerCrop()
                    .crossFade()
                    .listener(GlidePalette.with(mPlaylists.get(position).getAlbum())
                            .use(GlidePalette.Profile.MUTED_DARK)
                            .intoBackground(holder.mLayoutContent)
                    )
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mPlaylists.size();
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
    }


}

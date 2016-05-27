package com.masaila.oink.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.masaila.oink.R;
import com.masaila.oink.adapter.DiscoverAdapter;
import com.masaila.oink.api.ApiManager;
import com.masaila.oink.listener.OnScrollFooterListener;
import com.masaila.oink.model.HttpResult;
import com.masaila.oink.model.Playlist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

public class DiscoverFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<Playlist> mPlaylists;
    private DiscoverAdapter mAdapter;
    private int mPage = 0;

    private DisplayMetrics mMetrics;

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

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        mPlaylists = new ArrayList<>();
        mAdapter = new DiscoverAdapter(getContext(), mPlaylists, mMetrics);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        ApiManager.getInstance()
                .getAllPlaylist(mGetAllPlaylistSubscriber, mPage);
        
        //滑动到底部还有个bug 在刷新的时候 继续滑 会继续出发 onLoadMore 要在listener里面加一个isRefreshing
        mRecyclerView.addOnScrollListener(new OnScrollFooterListener() {
            @Override
            public void onLoadMore() {
                mPage += 1;
                Log.e("onLoadMore", "page: " + mPage);
                ApiManager.getInstance()
                        .getAllPlaylist(new Subscriber<HttpResult>() {
                            @Override
                            public void onCompleted() {
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(HttpResult httpResult) {
                                mPlaylists.remove(mPlaylists.size() - 1);
                                mPlaylists.addAll((Collection<? extends Playlist>) httpResult.getData());
                                mPlaylists.add(null);
                            }
                        }, mPage);
            }
        });
    }

    Subscriber<HttpResult> mGetAllPlaylistSubscriber = new Subscriber<HttpResult>() {
        @Override
        public void onCompleted() {
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(HttpResult httpResult) {
            mPlaylists.addAll((Collection<? extends Playlist>) httpResult.getData());
            mPlaylists.add(null);
        }
    };


}

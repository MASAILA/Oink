package com.masaila.oink.ui.activites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.masaila.oink.R;
import com.masaila.oink.api.APIManager;
import com.masaila.oink.model.AllPlaylist;
import com.masaila.oink.model.Song;
import com.masaila.oink.ui.fragments.DiscoverFragment;
import com.masaila.oink.ui.fragments.TopListFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tab)
    SmartTabLayout mTabLayout;

    private MyPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupViewPager();


        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        //pass your items here
                )
                .build();

        new DrawerBuilder()
                .withActivity(this)
                .addDrawerItems(
                        //pass your items here
                )
                .withDrawerGravity(Gravity.END)
                .append(result);

    }

    private void setupViewPager() {
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mTabLayout.setCustomTabView(new SmartTabLayout.TabProvider() {
            final LayoutInflater inflater = LayoutInflater.from(mTabLayout.getContext());

            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                ImageView imageView = (ImageView) inflater.inflate(R.layout.tab_main_icon, container,
                        false);
                switch (position) {
                    case 0:
                        imageView.setImageResource(R.drawable.home_fill);
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.discover);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.rank_fill);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.people_fill);
                        break;
                }
                return imageView;
            }
        });
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setViewPager(mViewPager);
    }

//    @OnClick({R.id.button_play, R.id.button_next, R.id.button_seek})
//    public void onClick(View view) {
//        PlayEvent playEvent;
//        switch (view.getId()) {
//            case R.id.button_play:
//                playEvent = new PlayEvent();
//                List<Song> queue = new ArrayList<>();
//                queue.add(getSong("http://m2.music.126.net/AuxCK2R5aJlTETgi8kwN3g==/5923069139252948.mp3"));
//                queue.add(getSong("http://m2.music.126.net/AuxCK2R5aJlTETgi8kwN3g==/5923069139252948.mp3"));
//                playEvent.setAction(PlayEvent.Action.PLAY);
//                playEvent.setQueue(queue);
//                EventBus.getDefault().post(playEvent);
//                break;
//            case R.id.button_next:
//                playEvent = new PlayEvent();
//                playEvent.setAction(PlayEvent.Action.NEXT);
//                EventBus.getDefault().post(playEvent);
//                break;
//            case R.id.button_seek:
//                playEvent = new PlayEvent();
//                playEvent.setAction(PlayEvent.Action.SEEK);
//                break;
//        }
//    }

    private Song getSong(String url) {
        Song song = new Song();
        song.setPath(url);
        return song;
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] fragments = new Fragment[]{
                TopListFragment.newInstance(),
                DiscoverFragment.newInstance(),
                TopListFragment.newInstance(),
                TopListFragment.newInstance()
        };

        private final String[] TITLES = {"我的", "最新", "曲库", "电台"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        public MyPagerAdapter(FragmentManager fm, Fragment[] fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }

}
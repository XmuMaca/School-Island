package wxm.com.androiddesign.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import wxm.com.androiddesign.R;
import wxm.com.androiddesign.adapter.TabPagerAdapter;
import wxm.com.androiddesign.module.MyUser;
import wxm.com.androiddesign.ui.fragment.ActivityFragment;
import wxm.com.androiddesign.ui.fragment.ListFragment;
import wxm.com.androiddesign.utils.Config;


/**
 * Created by Wu on 2015/4/16.
 */
public class NotificationActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{

    public static final int CHAT=1;
    public static final int COMMENT=2;
    public static final int NOTIFY=3;
    int type;
    String userId;
    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;
    MainActivity.MyOnTouchListener onTouchListener;
    private TabPagerAdapter adapter;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.fragmentparent);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type");
        ButterKnife.bind(this);
        appBarLayout.addOnOffsetChangedListener(this);
        if (viewPager != null) {
            setupViewPager();
        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        setupToolBar();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                ActivityFragment activityFragment = (ActivityFragment) adapter.getItem(viewPager.getCurrentItem());
//                if (index == 0) {
//                    activityFragment.getmSwipeRefreshLayout().setEnabled(true);
//                } else {
//                    activityFragment.getmSwipeRefreshLayout().setEnabled(false);
//                }
//                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void setupToolBar(){
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("消息通知");
    }

    private void setupViewPager() {
        Log.d("user", "setupViewPager" + userId);
        adapter = new TabPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ListFragment.newInstance(ListFragment.CHAT), "私信");
        adapter.addFragment(ListFragment.newInstance(ListFragment.COMMENT), "评论");
        adapter.addFragment(ListFragment.newInstance(ListFragment.NOTIFY), "通知");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        switch (type) {
            case CHAT:tabLayout.getTabAt(0).select();break;
            case COMMENT:tabLayout.getTabAt(1).select();break;
            case NOTIFY:tabLayout.getTabAt(2).select();break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    //swipe to refresh fix
    int index=0;
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        index=i;
        Log.d(Config.appBar, "index:" + i);
//        if (index<-100){
//            resetAppBar();
//        }
    }

    private void resetAppBar(){
        CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior=(AppBarLayout.Behavior)params.getBehavior();
        behavior.onNestedFling(coordinatorLayout, appBarLayout, null, 0, -1000, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
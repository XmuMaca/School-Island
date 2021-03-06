package wxm.com.androiddesign.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import wxm.com.androiddesign.R;
import wxm.com.androiddesign.adapter.TabPagerAdapter;
import wxm.com.androiddesign.module.MyUser;
import wxm.com.androiddesign.module.User;
import wxm.com.androiddesign.network.JsonConnection;
import wxm.com.androiddesign.ui.fragment.GroupListFragment;
import wxm.com.androiddesign.ui.fragment.PhotoFragment;
import wxm.com.androiddesign.ui.fragment.ProfileFragment;
import wxm.com.androiddesign.ui.fragment.UserActivityFragment;
import wxm.com.androiddesign.utils.Config;
import wxm.com.androiddesign.utils.MyUtils;


public class Dre_UserAcitivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    ViewPager viewPager;
    String userId = null;
    User user;
    public int appBarHeight;

    @Bind(R.id.user_id)
    TextView user_id;
    @Bind(R.id.score)
    TextView score;
    @Bind(R.id.user_photo)
    CircleImageView user_photo;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.main_content)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_acitivity);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("userId");
        new GetUserInfo(this).execute();
        appBarHeight = MyUtils.getPixels(this,175);
        //appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        return super.dispatchTouchEvent(ev);
    }

    boolean isExpanded=true;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        Log.d(Config.appBar, "verticalOffset:" + i);
//        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, getResources().getDisplayMetrics())
        Log.d(Config.appBar, "px_verticalOffset:" + i/(getResources().getDisplayMetrics().densityDpi/160f));
        Log.d(Config.appBar, "appBarHeight:" +appBarHeight);
        Log.d(Config.appBar,"pixels:"+MyUtils.getPixels(this,200));
        if (i==0){
            isExpanded=true;
        }
        else if(Math.abs(i)==appBarHeight){
            isExpanded=false;
        }
        if(Math.abs(i)>appBarHeight/2){
            if(isExpanded){
                collapseAppBar();
                isExpanded=false;
            }
        }else {
            if (!isExpanded){
                isExpanded=true;
                expandAppbar();
            }
        }
    }

    private void collapseAppBar() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior!=null){
            behavior.onNestedFling(coordinatorLayout, appBarLayout, null, 0, 10000, true);
        }
    }

    private void expandAppbar(){
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        if (behavior!=null){
            behavior.setTopAndBottomOffset(0);
            behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, 1, new int[2]);
        }
    }

    private class GetUserInfo extends AsyncTask<Void, Void, Boolean> {
        Context context;

        public GetUserInfo(Context context) {
            this.context = context;
        }

        @Override
        protected void onPostExecute(Boolean reslut) {
            super.onPostExecute(reslut);
            if (reslut) {
                setupToolBar();
                setupViewPager();
                setupTabLayout();
                user_id.setText(user.getUserName());
                score.setText("积分：" + user.getUserCredit());
                user_photo.setVisibility(View.VISIBLE);
                Picasso.with(context).load(user.getUserIcon()).into(user_photo, new Callback() {
                    @Override
                    public void onSuccess() {
                        user_photo.animate().alpha(1f).setDuration(1000).start();
                        score.animate().alpha(1f).setDuration(1000).start();
                        user_id.animate().alpha(1f).setDuration(1000).start();
                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            JSONObject object = new JSONObject();
            try {
                object.put("action", "showProfile");
                object.put("userId", userId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            user = new Gson().fromJson(JsonConnection.getJSON(object.toString()), User.class);
            if (userId != null) {
                user.setUserId(userId);
            }

            return true;
        }
    }

    private void applyPalette(Palette palette) {
        int primaryDark = ContextCompat.getColor(this, R.color.primary_dark);
        int primary = ContextCompat.getColor(this, R.color.primary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        //updateBackground((FloatingActionButton) findViewById(R.id.fab), palette);
        supportStartPostponedEnterTransition();
    }

    private void setupToolBar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(user.getUserName());
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));

    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ProfileFragment.newInstance(userId), "个人信息");
        adapter.addFragment(UserActivityFragment.newInstance(UserActivityFragment.Release, userId), "已发布活动");
        adapter.addFragment(UserActivityFragment.newInstance(UserActivityFragment.Joined, userId), "参与活动");
        adapter.addFragment(GroupListFragment.newInstance(userId, GroupListFragment.JOINED,false), "社区");
        adapter.addFragment(PhotoFragment.newInstance(userId), "相册");
        viewPager.setAdapter(adapter);
    }

    private void setupTabLayout() {
        TabLayout tab = (TabLayout) findViewById(R.id.tabs);
        tab.setupWithViewPager(viewPager);
     //   tab.setBackgroundColor(ContextCompat.getColor(this, R.color.tab_color));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_acitivity, menu);
        if (userId.equals(MyUser.userId)) {
            MenuItem menuItem = menu.findItem(R.id.action_send);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_send:
                //打开聊天
                Intent chatIntent = new Intent(this, ChatActivity.class);
                chatIntent.putExtra("easemobId", user.getEasemobId());
                chatIntent.putExtra("userIcon", user.getUserIcon());
                chatIntent.putExtra("userName",user.getUserName());
                chatIntent.putExtra("chatType",ChatActivity.CHAT);
                startActivity(chatIntent);
                break;
            case R.id.action_settings:
                Snackbar.make(user_photo, "举报", Snackbar.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

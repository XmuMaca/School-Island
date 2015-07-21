package wxm.com.androiddesign.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
//import com.melnykov.fab.FloatingActionButton;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import wxm.com.androiddesign.module.User;

import wxm.com.androiddesign.network.JsonConnection;

import wxm.com.androiddesign.services.LocationServices;

import wxm.com.androiddesign.ui.fragment.FragmentParent;
import wxm.com.androiddesign.ui.fragment.HomeFragment;
import wxm.com.androiddesign.R;
import wxm.com.androiddesign.ui.fragment.LoginFragment;
import wxm.com.androiddesign.ui.fragment.MsgListFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LoginFragment.LoginCallBack, SearchView.OnQueryTextListener {
    DrawerLayout drawerLayout;

    public static MainActivity instance =null ;
    public static Context context;

    @Bind(R.id.fab)
    FloatingActionButton fab;
    User mUuser = new User();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeLocationServices();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance=this;
        context=getApplicationContext();
        super.onCreate(savedInstanceState);

        mUuser.setUserId("cz");
        mUuser.setUserName("游客");

        setContentView(R.layout.activity_main);
        instance = this;
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.content, HomeFragment.newInstance(mUuser.getUserId())).commit();
        }

        ButterKnife.bind(this);
        setupFab();
        new LoginTask(this).execute();
        setupNavigationView();
        setupInfo();
        openLocationServices();
    }
    private void openLocationServices()
    {
        Log.e("CJ", "onpenLocationServices");
        Intent i = new Intent();
        i.setClass(getApplicationContext(), LocationServices.class);
        startService(i);
    }
    private void closeLocationServices()
    {
        Log.e("CJ","closeLocationServices");
        Intent i = new Intent();
        i.setClass(getApplicationContext(), LocationServices.class);
        startService(i);
    }


    private void setupFab() {

        fab.setOnClickListener(this);
    }

    private void setupInfo() {

    }

    public class LoginTask extends AsyncTask<Void, Void, User> {
        Context context;

        public LoginTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            mUuser = user;
            ((TextView) findViewById(R.id.username)).setText(mUuser.getUserName());
            ((TextView) findViewById(R.id.user_email)).setText(mUuser.getUserEmail());
            Picasso.with(context).load(mUuser.getUserIcon()).into((CircleImageView) findViewById(R.id.user_photo));
        }

        @Override
        protected User doInBackground(Void... params) {
            SharedPreferences prefs = getSharedPreferences("wxm.com.androiddesign", Context.MODE_PRIVATE);
            String name = prefs.getString("UserId", "游客");
            String password = prefs.getString("UserPassword", "123");
            JSONObject object = new JSONObject();
            try {
                object.put("action", "login");
                object.put("userId", name);
                object.put("userPassword", password);

                String userJson = JsonConnection.getJSON(object.toString());
                return new Gson().fromJson(userJson, User.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onLongin(User user) {
        mUuser = user;
        Log.d("user", user.toString());
        SharedPreferences prefs = getSharedPreferences("wxm.com.androiddesign", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("UserId", user.getUserId());
        editor.putString("UserPassword", user.getUserPassword());
        editor.apply();
        ((TextView) findViewById(R.id.username)).setText(user.getUserName());
        ((TextView) findViewById(R.id.user_email)).setText(user.getUserEmail());
        Picasso.with(this).load(user.getUserIcon()).into((CircleImageView) findViewById(R.id.user_photo));
    }

    private void setupDrawerContent(final NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        for (int index = 0; index < navigationView.getMenu().size(); index++) {
                            navigationView.getMenu().getItem(index).setChecked(false);
                        }
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                getSupportFragmentManager().beginTransaction().replace(R.id.content, HomeFragment.newInstance(mUuser.getUserId())).commitAllowingStateLoss();
                                return true;
                            case R.id.nav_explore:
                                getSupportFragmentManager().beginTransaction().replace(R.id.content, FragmentParent.newInstance(mUuser.getUserId())).commitAllowingStateLoss();
                                return true;
                            case R.id.nav_attention:

                                Snackbar.make(drawerLayout, "关注",
                                        Snackbar.LENGTH_SHORT).show();
                                return true;
                            case R.id.nav_messages:
                                getSupportFragmentManager().beginTransaction().replace(R.id.content, MsgListFragment.newInstance(mUuser.getUserId())).commitAllowingStateLoss();
                            case R.id.nav_user_setting:

                                return true;
                            default:
                                return true;
                        }
                    }
                });
    }


    private void setupNavigationView() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        View header = findViewById(R.id.header);
        header.setClickable(true);
        final CircleImageView userPhoto = (CircleImageView) findViewById(R.id.user_photo);
        userPhoto.setClickable(true);
        //Glide.with(this).load("http://101.200.191.149:8080/bootstrapRepository/images_repo/back_dark.png").into(userPhoto);
        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((TextView) findViewById(R.id.username)).getText().equals("游客")) {
                    drawerLayout.closeDrawers();
                    showLoginDialog();
                } else {
                    Intent intent = new Intent(MainActivity.this, UserAcitivity.class);
                    intent.putExtra("userId", mUuser.getUserId());
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            MainActivity.this, new Pair<View, String>(userPhoto, getResources().getString(R.string.transition_user_photo))
                    );
                    ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);

                return true;
            case R.id.action_serch:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_serch);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        }

        if (searchView != null) {
            searchView.setOnQueryTextListener(this);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, ReleaseActivity.class);
        intent.putExtra("userId", mUuser.getUserId());
        startActivity(intent);
    }

    private void showLoginDialog() {
        FragmentManager fm = getSupportFragmentManager();
        LoginFragment releaseFragment = new LoginFragment();
        releaseFragment.show(fm, "login");
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Snackbar.make(drawerLayout, query, Snackbar.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Snackbar.make(drawerLayout, "newText" + newText, Snackbar.LENGTH_SHORT).show();
        return false;
    }
}

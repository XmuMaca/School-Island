package wxm.com.androiddesign.ui.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import wxm.com.androiddesign.listener.RecyclerItemClickListener;
import wxm.com.androiddesign.module.AtyItem;
import wxm.com.androiddesign.adapter.MyRecycerAdapter;
import wxm.com.androiddesign.R;
import wxm.com.androiddesign.network.JsonConnection;
import wxm.com.androiddesign.ui.ReleaseActivity;
import wxm.com.androiddesign.utils.ScrollManager;
import wxm.com.androiddesign.utils.TransparentToolBar;

/**
 * Created by zero on 2015/6/26.
 */
public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView;
    Toolbar toolbar;
    static MyRecycerAdapter myRecycerAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Spinner mSpinner;
    static List<AtyItem> activityItems = new ArrayList<AtyItem>();
    private String userId;


    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getArguments().getString("UserId");
        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static HomeFragment newInstance(String muserId) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("UserId", muserId);
        fragment.setArguments(args);
        return fragment;
    }
    private class GetAtyTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean==true){

                myRecycerAdapter = new MyRecycerAdapter(activityItems,userId,(AppCompatActivity) getActivity(), "HomeFragment");
                recyclerView.setAdapter(myRecycerAdapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {
            JSONObject object=new JSONObject();
            try {
                object.put("action","showActivities");
                object.put("userId",userId);
                String jsonarrys = JsonConnection.getJSON(object.toString());
              //  Log.i("jsonarray",jsonarrys.toString());
                activityItems = new Gson().fromJson(jsonarrys, new TypeToken<List<AtyItem>>() {
                }.getType());
                for (int i=0;i<activityItems.size();i++){
                    Log.d("Task",activityItems.get(i).toString());
                }
                Log.d("Task",jsonarrys);
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_layout, viewGroup, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_activity);
        mSpinner = (Spinner) v.findViewById(R.id.spinner);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        setupSwipeRefreshLayout(mSwipeRefreshLayout);
        setupRecyclerView(recyclerView);
        setupSpinner(mSpinner);

        Log.d("home","onCreateView");
        return v;
    }

    private void setupSpinner(Spinner spinner) {
        List<String> spinnerarry = new ArrayList<String>();
        spinnerarry.add("厦门");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, spinnerarry);
        // ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(getActivity(),R.array.action_bar_spinner,
        //         R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        new GetAtyTask().execute();
//        activityItems.get(1).toString();
        myRecycerAdapter = new MyRecycerAdapter(activityItems,userId,(AppCompatActivity) getActivity(), "HomeFragment");
        recyclerView.setAdapter(myRecycerAdapter);
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        ScrollManager manager = new ScrollManager();
        manager.attach(recyclerView);
        manager.addView(getActivity().findViewById(R.id.fab), ScrollManager.Direction.DOWN);
        manager.setSwipeRefreshLayout(mSwipeRefreshLayout);
        animator.setAddDuration(2000);
        animator.setRemoveDuration(1000);
    }

    private void setupSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0, 150);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void refreshContent() {
        //load content
        mSwipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("home","postDelayed");

                //myRecycerAdapter.notifyDataSetChanged();

                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 5000);
        //load complete

        onContentLoadComplete();
    }

    private void onContentLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public static void addActivity(AtyItem atyItem) {
        activityItems.add(0, atyItem);
        myRecycerAdapter.notifyDataSetChanged();
    }

    public static void refresh(AtyItem atyItem, int position) {
        activityItems.remove(position);
        activityItems.add(position, atyItem);
        myRecycerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Log.d("spinner",view.toString()+position+"|"+id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d("spinner", "nothing" + parent.getId());
    }
}

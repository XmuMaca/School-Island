package wxm.com.androiddesign.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wxm.com.androiddesign.R;
import wxm.com.androiddesign.adapter.MyRecycerAdapter;
import wxm.com.androiddesign.adapter.UserAtyRecycerAdapter;
import wxm.com.androiddesign.module.AtyItem;
import wxm.com.androiddesign.network.JsonConnection;


/**
 * Created by zero on 2015/6/25.
 */
public class UserActivityFragment extends Fragment {

    public static final int Joined = 1;
    public static final int Release = 2;

    private int type;
    private String userId;
    RecyclerView recyclerView;
    static UserAtyRecycerAdapter myRecycerAdapter;

    public static UserActivityFragment newInstance(int type, String muserId) {
        UserActivityFragment fragment = new UserActivityFragment();
        Bundle args = new Bundle();
        args.putInt("Type", type);
        args.putString("UserId", muserId);
        fragment.setArguments(args);
        return fragment;
    }

    static ArrayList<AtyItem> activityItems = new ArrayList<AtyItem>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("Type");
        userId = getArguments().getString("UserId");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.activity_user_fragment, viewGroup, false);
        recyclerView = (RecyclerView) v;
        setupRecyclerView(recyclerView);
        return v;
    }


    private void setupRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setItemAnimator(new MyItemAnimator());
        new GetAtyTask().execute(type);

    }


    private class GetAtyTask extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
                myRecycerAdapter = new UserAtyRecycerAdapter(activityItems,getActivity(), "ActivityFragment");
                recyclerView.setAdapter(myRecycerAdapter);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            JSONObject object = new JSONObject();
            try {
                switch (params[0]) {
                    case Joined:
                        object.put("action", "showJoinedAty");
                        break;
                    case Release:
                        object.put("action", "showDistributedAty");
                        break;

                }
                object.put("userId", userId);
                String jsonarrys = JsonConnection.getJSON(object.toString());
                activityItems = new Gson().fromJson(jsonarrys, new TypeToken<List<AtyItem>>() {
                }.getType());
                if (activityItems == null) {
                    return false;
                }
                if (activityItems.size() == 0)
                    return false;
                for (int i = 0; i < activityItems.size(); i++) {
                    Log.d("Task", activityItems.get(i).toString());
                }
                //Log.d("Task", jsonarrys);
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

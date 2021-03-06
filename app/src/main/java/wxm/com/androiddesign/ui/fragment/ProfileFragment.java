package wxm.com.androiddesign.ui.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import wxm.com.androiddesign.R;
import wxm.com.androiddesign.adapter.ProfieAdapter;
import wxm.com.androiddesign.module.User;
import wxm.com.androiddesign.network.JsonConnection;
import wxm.com.androiddesign.utils.SpacesItemDecoration;

/**
 * Created by zero on 2015/6/30.
 */
public class ProfileFragment extends Fragment {
    RecyclerView recyclerView;
    private String userId;
    User user;


    public static ProfileFragment newInstance(String muserId) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("UserId", muserId);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_layout, viewGroup, false);
        userId = getArguments().getString("UserId");
        recyclerView = (RecyclerView) v;
        new GetProfile(getActivity()).execute();
        return v;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new SpacesItemDecoration(getActivity()));
        recyclerView.setAdapter(new ProfieAdapter(user));
    }

    private class GetProfile extends AsyncTask<String, Void, Boolean> {
        Context context;

        public GetProfile(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            setupRecyclerView(recyclerView);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            JSONObject object = new JSONObject();
            try {
                object.put("action", "showProfile");
                object.put("userId", userId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String json = JsonConnection.getJSON(object.toString());
            user = new Gson().fromJson(json, User.class);
            user.setUserId(userId);
            return null;
        }
    }
}

package wxm.com.androiddesign.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wxm.com.androiddesign.R;
import wxm.com.androiddesign.module.Group;
import wxm.com.androiddesign.module.MyUser;
import wxm.com.androiddesign.network.JsonConnection;
import wxm.com.androiddesign.utils.MyBitmapFactory;
import wxm.com.androiddesign.utils.MyUtils;

public class CreateGroupActivity extends AppCompatActivity {
    public static final int CHOOSE_IMAGE=1;

    @Bind(R.id.group_img)
    ImageView groupImage;
    @Bind(R.id.group_name)
    EditText groupName;
    @Bind(R.id.group_brief_intro)
    EditText groupIntro;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(groupName.getText().toString())||"".equals(groupIntro.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"信息不完整",Toast.LENGTH_SHORT).show();
                }else new CreateGroupTask().execute();


            }
        });
    }

    @OnClick(R.id.group_img)
    public void chooseImage(){
        MyUtils.chooseImage(this,CHOOSE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri chosenImageUri = data.getData();
            Picasso.with(this).load(chosenImageUri).into(groupImage);
        }
    }

    private class CreateGroupTask extends AsyncTask<Void,Integer,Boolean>{
        Bitmap bitmap;
        Group group;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bitmap=((BitmapDrawable)groupImage.getDrawable()).getBitmap();
            fab.setClickable(false);
            String icon = MyBitmapFactory.BitmapToString(bitmap);
            groupImage.setClickable(false);
            Log.d("createCommity", MyUser.userId);
            group=new Group("createCommunity",MyUser.userId,icon,"",
                    groupName.getText().toString(),groupIntro.getText().toString());
            Toast.makeText(getApplicationContext(),"部落创建中...",Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Toast.makeText(getApplicationContext(),"部落创建成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String result = JsonConnection.getJSON(new Gson().toJson(group));
            return null;
        }
    }
}

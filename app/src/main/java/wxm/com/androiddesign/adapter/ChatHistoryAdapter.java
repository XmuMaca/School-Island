package wxm.com.androiddesign.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContact;
import com.easemob.chat.EMConversation;
import com.easemob.chat.TextMessageBody;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import wxm.com.androiddesign.R;
import wxm.com.androiddesign.module.ChatItem;
import wxm.com.androiddesign.module.MyUser;
import wxm.com.androiddesign.network.JsonConnection;
import wxm.com.androiddesign.ui.ChatActivity;
import wxm.com.androiddesign.ui.MyApplication;

/**
 * Created by zero on 2015/6/30.
 */
public class ChatHistoryAdapter extends RecyclerView.Adapter<ChatHistoryAdapter.MyViewHolder>  {

    List<ChatItem> mChatItemList = new ArrayList<>();
    Activity activity;

    public ChatHistoryAdapter(List<ChatItem> mChatItemList,Activity activity) {
        this.mChatItemList = mChatItemList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.chat_item, parent, false
                ));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChatItem chatItem = mChatItemList.get(position);
        holder.mTime.setText(chatItem.getSendTime());
        holder.mContent.setText(chatItem.getMsgContent());
        holder.userName.setText(chatItem.getFromUserName());
        Picasso.with(MyApplication.applicationContext)
                .load(chatItem.getFromUserIcon())
                .into(holder.userPhoto);
    }

    @Override
    public int getItemCount() {
        return mChatItemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.chat_msg)
        TextView mContent;
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.msg_time)
        TextView mTime;
        @Bind(R.id.user_photo)
        CircleImageView userPhoto;
        @Bind(R.id.list_item)
        RelativeLayout listItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatActivity.start(activity,ChatActivity.CHAT
                            ,mChatItemList.get(getAdapterPosition()).getFromEasemobId()
                            ,mChatItemList.get(getAdapterPosition()).getFromUserName());
                }
            });
            listItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new DeleteHistory().execute();
                    return false;
                }
            });
        }

        private class DeleteHistory extends AsyncTask<String, Void, Boolean> {
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                mChatItemList.remove(getAdapterPosition());
                notifyDataSetChanged();
            }
            @Override
            protected Boolean doInBackground(String... params) {
                JSONObject object = new JSONObject();
                try {
                    object.put("action","deleteOneChat");
                    object.put("toEasemobId",MyUser.easemobId);
                    object.put("fromEasemobId",mChatItemList.get(getAdapterPosition()).getFromEasemobId());
                    JsonConnection.getJSON(object.toString());
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
    }
}

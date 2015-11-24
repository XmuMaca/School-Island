package wxm.com.androiddesign.utils;

import android.content.Context;

import wxm.com.androiddesign.ui.NewUserAcitivity;
import wxm.com.androiddesign.ui.test.UserAcitvity;

/**
 * Created by Zero on 11/4/2015.
 */
public class ActivityStartHelper {
    public static void startProfileActivity(Context context,String userId,int index){
        if (Config.laterLollipop()){
            UserAcitvity.start(context,userId,index);
        }
        else {
            NewUserAcitivity.start(context,userId,index);
        }
    }
}

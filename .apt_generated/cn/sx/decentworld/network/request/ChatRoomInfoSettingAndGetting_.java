//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.network.request;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public final class ChatRoomInfoSettingAndGetting_
    extends ChatRoomInfoSettingAndGetting
{

    private Context context_;

    private ChatRoomInfoSettingAndGetting_(Context context) {
        context_ = context;
        init_();
    }

    public void afterSetContentView_() {
        if (!(context_ instanceof Activity)) {
            return ;
        }
        init();
    }

    /**
     * You should check that context is an activity before calling this method
     * 
     */
    public View findViewById(int id) {
        Activity activity_ = ((Activity) context_);
        return activity_.findViewById(id);
    }

    @SuppressWarnings("all")
    private void init_() {
        if (context_ instanceof Activity) {
            Activity activity = ((Activity) context_);
        }
        if (context_ instanceof Activity) {
            activity = ((Activity) context_);
        }
        context = context_;
    }

    public static ChatRoomInfoSettingAndGetting_ getInstance_(Context context) {
        return new ChatRoomInfoSettingAndGetting_(context);
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}

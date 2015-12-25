//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.component.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent_;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.network.request.GetUserInfo_;
import cn.sx.decentworld.widget.CustomScrollView;

public final class MeComponent_
    extends MeComponent
{

    private Context context_;

    private MeComponent_(Context context) {
        context_ = context;
        init_();
    }

    public void afterSetContentView_() {
        if (!(context_ instanceof Activity)) {
            return ;
        }
        ll_chat_me_work_circle = ((LinearLayout) findViewById(cn.sx.decentworld.R.id.ll_chat_me_work_circle));
        tv_chat_me_edit = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_chat_me_edit));
        tv_chat_me_introduce = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_chat_me_introduce));
        relMeTitle = ((RelativeLayout) findViewById(cn.sx.decentworld.R.id.rel_me_title));
        ll_chat_me_recharge = ((RelativeLayout) findViewById(cn.sx.decentworld.R.id.rl_chat_me_recharge));
        rl_chat_me_root = ((RelativeLayout) findViewById(cn.sx.decentworld.R.id.rl_chat_me_root));
        sv_chat_me = ((CustomScrollView) findViewById(cn.sx.decentworld.R.id.sv_chat_me));
        ll_chat_me_setting = ((RelativeLayout) findViewById(cn.sx.decentworld.R.id.rl_chat_me_setting));
        ll_chat_me_user_detail = ((LinearLayout) findViewById(cn.sx.decentworld.R.id.ll_chat_me_user_detail));
        ((GetUserInfo_) getUserInfo).afterSetContentView_();
        ((ChoceAndTakePictureComponent_) choceAndTakePictureComponent).afterSetContentView_();
        ((ToastComponent_) toast).afterSetContentView_();
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
        getUserInfo = GetUserInfo_.getInstance_(context_);
        choceAndTakePictureComponent = ChoceAndTakePictureComponent_.getInstance_(context_);
        toast = ToastComponent_.getInstance_(context_);
    }

    public static MeComponent_ getInstance_(Context context) {
        return new MeComponent_(context);
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}
//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.TitleBar_;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.widget.ExpandGridView;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class ChatSettingActivity_
    extends ChatSettingActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.chat_setting);
    }

    private void init_(Bundle savedInstanceState) {
        toast = ToastComponent_.getInstance_(this);
        titleBar = TitleBar_.getInstance_(this);
    }

    private void afterSetContentView_() {
        rl_chat_details_empty_record = ((RelativeLayout) findViewById(id.rl_chat_details_empty_record));
        iv_switch_top = ((ImageView) findViewById(id.iv_switch_top));
        main_header_left = ((LinearLayout) findViewById(id.main_header_left));
        rl_switch_msg_top = ((RelativeLayout) findViewById(id.rl_switch_msg_top));
        rl_chat_details_set_bg = ((RelativeLayout) findViewById(id.rl_chat_details_set_bg));
        rl_switch_msg_no_disturb = ((RelativeLayout) findViewById(id.rl_switch_msg_no_disturb));
        iv_switch_no_disturb = ((ImageView) findViewById(id.iv_switch_no_disturb));
        rl_chat_details_whistleblowing = ((RelativeLayout) findViewById(id.rl_chat_details_whistleblowing));
        chat_setting_gridview = ((ExpandGridView) findViewById(id.chat_setting_gridview));
        ((ToastComponent_) toast).afterSetContentView_();
        ((TitleBar_) titleBar).afterSetContentView_();
        init();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        afterSetContentView_();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    public static ChatSettingActivity_.IntentBuilder_ intent(Context context) {
        return new ChatSettingActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ChatSettingActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ChatSettingActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (context_ instanceof Activity) {
                ((Activity) context_).startActivityForResult(intent_, requestCode);
            } else {
                context_.startActivity(intent_);
            }
        }

    }

}

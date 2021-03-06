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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.network.request.ChatRoomInfoSettingAndGetting_;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class ChatRoomEditActivity_
    extends ChatRoomEditActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_edit);
    }

    private void init_(Bundle savedInstanceState) {
        chatRoomInfoSettingAndGetting = ChatRoomInfoSettingAndGetting_.getInstance_(this);
    }

    private void afterSetContentView_() {
        rgContentFee = ((RadioGroup) findViewById(id.rg_content));
        rbFeeScale = ((RadioButton) findViewById(id.rb_fee_scale));
        ivBack = ((ImageView) findViewById(id.iv_back));
        tvSend = ((TextView) findViewById(id.tv_send));
        rbContent = ((RadioButton) findViewById(id.rb_content));
        ((ChatRoomInfoSettingAndGetting_) chatRoomInfoSettingAndGetting).afterSetContentView_();
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

    public static ChatRoomEditActivity_.IntentBuilder_ intent(Context context) {
        return new ChatRoomEditActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ChatRoomEditActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ChatRoomEditActivity_.IntentBuilder_ flags(int flags) {
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

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.network.request.ChatRoomInfoSettingAndGetting_;
import cn.sx.decentworld.widget.CircularImageView;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class ChatRoomEditMyInfoActivity_
    extends ChatRoomEditMyInfoActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_my_info);
    }

    private void init_(Bundle savedInstanceState) {
        chatRoomInfoSettingAndGetting = ChatRoomInfoSettingAndGetting_.getInstance_(this);
        toast = ToastComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        tvNickName = ((TextView) findViewById(id.tv_nickname));
        btnOk = ((Button) findViewById(id.btn_OK));
        etSelfIntroduce = ((EditText) findViewById(id.et_self_introduce));
        llChatRoomHead = ((LinearLayout) findViewById(id.ll_chatroom_head));
        tvTitle = ((TextView) findViewById(id.tv_header_title));
        ivChatRoomHead = ((CircularImageView) findViewById(id.iv_chatroom_head));
        ivBack = ((ImageView) findViewById(id.iv_back));
        ((ChatRoomInfoSettingAndGetting_) chatRoomInfoSettingAndGetting).afterSetContentView_();
        ((ToastComponent_) toast).afterSetContentView_();
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

    public static ChatRoomEditMyInfoActivity_.IntentBuilder_ intent(Context context) {
        return new ChatRoomEditMyInfoActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ChatRoomEditMyInfoActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ChatRoomEditMyInfoActivity_.IntentBuilder_ flags(int flags) {
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
//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.bean.manager.DWSMessageManager_;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent_;
import cn.sx.decentworld.component.KeyboardComponent_;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.component.ui.ChatComponent_;
import cn.sx.decentworld.network.request.GetUserInfo_;
import cn.sx.decentworld.widget.PasteEditText;
import cn.sx.decentworld.widget.RecorderButton;
import com.googlecode.androidannotations.api.SdkVersionHelper;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public final class ChatActivity_
    extends ChatActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_chat);
    }

    private void init_(Bundle savedInstanceState) {
        getUserInfo = GetUserInfo_.getInstance_(this);
        toast = ToastComponent_.getInstance_(this);
        KeyboardComponent = KeyboardComponent_.getInstance_(this);
        choceAndTakePictureComponent = ChoceAndTakePictureComponent_.getInstance_(this);
        chatComponent = ChatComponent_.getInstance_(this);
        dwsMessageManager = DWSMessageManager_.getInstance_(this);
    }

    private void afterSetContentView_() {
        main_header_title = ((TextView) findViewById(id.tv_header_title));
        main_header_left = ((LinearLayout) findViewById(id.main_header_left));
        micImage = ((ImageView) findViewById(id.mic_image));
        mEditTextContent = ((PasteEditText) findViewById(id.et_sendmessage));
        main_header_right_tv = ((TextView) findViewById(id.main_header_right_tv));
        iv_emoticons_normal = ((ImageView) findViewById(id.iv_emoticons_normal));
        more = ((LinearLayout) findViewById(id.more));
        buttonSetModeKeyboard = ((Button) findViewById(id.btn_set_mode_keyboard));
        activity_chat_bg = ((ImageView) findViewById(id.activity_chat_bg));
        edittext_layout = ((RelativeLayout) findViewById(id.edittext_layout));
        main_header_right_btn = ((ImageView) findViewById(id.main_header_right_btn));
        btnContainer = ((LinearLayout) findViewById(id.ll_btn_container));
        listView = ((PullToRefreshListView) findViewById(id.list));
        buttonPressToSpeak = ((RecorderButton) findViewById(id.btn_press_to_speak));
        buttonSend = ((Button) findViewById(id.btn_send));
        btn_location_ll = ((LinearLayout) findViewById(id.btn_location_ll));
        iv_emoticons_checked = ((ImageView) findViewById(id.iv_emoticons_checked));
        emojiIconContainer = ((LinearLayout) findViewById(id.ll_face_container));
        recordingContainer = ((View) findViewById(id.recording_container));
        btnMore = ((Button) findViewById(id.btn_more));
        expressionViewpager = ((ViewPager) findViewById(id.vPager));
        btn_set_mode_voice = ((Button) findViewById(id.btn_set_mode_voice));
        recordingHint = ((TextView) findViewById(id.recording_hint));
        {
            View view = findViewById(id.main_header_right);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatActivity_.this.goDetail();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.main_header_left);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatActivity_.this.back();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.btn_set_mode_voice);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatActivity_.this.btn_set_mode_voice();
                    }

                }
                );
            }
        }
        ((GetUserInfo_) getUserInfo).afterSetContentView_();
        ((ToastComponent_) toast).afterSetContentView_();
        ((KeyboardComponent_) KeyboardComponent).afterSetContentView_();
        ((ChoceAndTakePictureComponent_) choceAndTakePictureComponent).afterSetContentView_();
        ((ChatComponent_) chatComponent).afterSetContentView_();
        ((DWSMessageManager_) dwsMessageManager).afterSetContentView_();
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

    public static ChatActivity_.IntentBuilder_ intent(Context context) {
        return new ChatActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ChatActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ChatActivity_.IntentBuilder_ flags(int flags) {
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
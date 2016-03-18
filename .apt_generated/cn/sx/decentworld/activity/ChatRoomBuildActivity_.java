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
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent_;
import cn.sx.decentworld.component.TitleBar_;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.component.ui.ChatRoomBuildComponent_;
import cn.sx.decentworld.network.request.SetRoomInfo_;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class ChatRoomBuildActivity_
    extends ChatRoomBuildActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_chat_room_build);
    }

    private void init_(Bundle savedInstanceState) {
        setRoomInfo = SetRoomInfo_.getInstance_(this);
        titleBar = TitleBar_.getInstance_(this);
        choceAndTakePictureComponent = ChoceAndTakePictureComponent_.getInstance_(this);
        chatRoomBuildComponent = ChatRoomBuildComponent_.getInstance_(this);
        toast = ToastComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        iv_chat_room_build_icon = ((ImageView) findViewById(id.iv_chat_room_build_icon));
        iv_chat_room_build_background = ((ImageView) findViewById(id.iv_chat_room_build_background));
        iv_chat_room_build_modif_icon = ((ImageView) findViewById(id.iv_chat_room_build_modif_icon));
        add_topic_button = ((Button) findViewById(id.add_topic_button));
        add_topic_content2 = ((TextView) findViewById(id.add_topic_content2));
        et_chat_room_build_name = ((EditText) findViewById(id.et_chat_room_build_name));
        et_chat_room_build_owner_introduction = ((EditText) findViewById(id.et_chat_room_build_owner_introduction));
        root_activitiy_chat_room_build = ((RelativeLayout) findViewById(id.root_activitiy_chat_room_build));
        tv_chat_room_build_hostname = ((TextView) findViewById(id.tv_chat_room_build_hostname));
        iv_chat_room_build_modif_name = ((ImageView) findViewById(id.iv_chat_room_build_modif_name));
        {
            View view = findViewById(id.add_topic_button);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatRoomBuildActivity_.this.tocreateChatRoom();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.iv_chat_room_build_modif_name);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatRoomBuildActivity_.this.modifHostName();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.btn_chat_room_build_save);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatRoomBuildActivity_.this.save();
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
                        ChatRoomBuildActivity_.this.back();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.iv_chat_room_build_background);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatRoomBuildActivity_.this.addRoomBackground();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.add_topic_content2);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatRoomBuildActivity_.this.toadd_Content();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.iv_chat_room_build_modif_icon);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatRoomBuildActivity_.this.modifHostIcon();
                    }

                }
                );
            }
        }
        ((SetRoomInfo_) setRoomInfo).afterSetContentView_();
        ((TitleBar_) titleBar).afterSetContentView_();
        ((ChoceAndTakePictureComponent_) choceAndTakePictureComponent).afterSetContentView_();
        ((ChatRoomBuildComponent_) chatRoomBuildComponent).afterSetContentView_();
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

    public static ChatRoomBuildActivity_.IntentBuilder_ intent(Context context) {
        return new ChatRoomBuildActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ChatRoomBuildActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ChatRoomBuildActivity_.IntentBuilder_ flags(int flags) {
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

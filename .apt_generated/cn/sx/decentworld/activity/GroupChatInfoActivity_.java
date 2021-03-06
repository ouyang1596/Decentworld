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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.component.ui.MainFragmentComponent_;
import cn.sx.decentworld.widget.ExpandGridView;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class GroupChatInfoActivity_
    extends GroupChatInfoActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.fragment_chat_chat_group_info);
    }

    private void init_(Bundle savedInstanceState) {
        toast = ToastComponent_.getInstance_(this);
        mainFragmentComponent = MainFragmentComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        group_chat_group_icon = ((ImageView) findViewById(id.group_chat_group_icon));
        userGridview = ((ExpandGridView) findViewById(id.group_chat_gridview));
        interrupt_switcher = ((ImageView) findViewById(id.interrupt_switcher));
        set_top_switcher = ((ImageView) findViewById(id.set_top_switcher));
        my_name_groupchat = ((TextView) findViewById(id.my_name_groupchat));
        root_fragment_chat_chat_group_info = ((LinearLayout) findViewById(id.root_fragment_chat_chat_group_info));
        exitBtn = ((Button) findViewById(id.btn_exit_grp));
        real_groupname = ((TextView) findViewById(id.real_groupname));
        {
            View view = findViewById(id.main_header_left);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GroupChatInfoActivity_.this.back();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.set_chat_top);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GroupChatInfoActivity_.this.changeIcon();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.no_interrupt);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GroupChatInfoActivity_.this.changeinterrupt_Icon();
                    }

                }
                );
            }
        }
        ((ToastComponent_) toast).afterSetContentView_();
        ((MainFragmentComponent_) mainFragmentComponent).afterSetContentView_();
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

    public static GroupChatInfoActivity_.IntentBuilder_ intent(Context context) {
        return new GroupChatInfoActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, GroupChatInfoActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public GroupChatInfoActivity_.IntentBuilder_ flags(int flags) {
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

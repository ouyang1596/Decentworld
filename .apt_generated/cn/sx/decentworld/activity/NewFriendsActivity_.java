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
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.TitleBar_;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.component.ui.MainFragmentComponent_;
import cn.sx.decentworld.network.request.GetFriendInfo_;
import cn.sx.decentworld.network.request.GetUserInfo_;
import cn.sx.decentworld.widget.ListViewForScrollView;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class NewFriendsActivity_
    extends NewFriendsActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_new_friends);
    }

    private void init_(Bundle savedInstanceState) {
        getUserInfo = GetUserInfo_.getInstance_(this);
        getFriendInfo = GetFriendInfo_.getInstance_(this);
        titleBar = TitleBar_.getInstance_(this);
        toast = ToastComponent_.getInstance_(this);
        mainFragmentComponent = MainFragmentComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        lv_newfriends = ((ListViewForScrollView) findViewById(id.lv_newfriends));
        main_header_left_tv = ((TextView) findViewById(id.main_header_left_tv));
        bt_to_add = ((Button) findViewById(id.bt_to_add));
        {
            View view = findViewById(id.main_header_left);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        NewFriendsActivity_.this.back();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.main_header_right_tv);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        NewFriendsActivity_.this.refreshList();
                    }

                }
                );
            }
        }
        ((GetUserInfo_) getUserInfo).afterSetContentView_();
        ((GetFriendInfo_) getFriendInfo).afterSetContentView_();
        ((TitleBar_) titleBar).afterSetContentView_();
        ((ToastComponent_) toast).afterSetContentView_();
        ((MainFragmentComponent_) mainFragmentComponent).afterSetContentView_();
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

    public static NewFriendsActivity_.IntentBuilder_ intent(Context context) {
        return new NewFriendsActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, NewFriendsActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public NewFriendsActivity_.IntentBuilder_ flags(int flags) {
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
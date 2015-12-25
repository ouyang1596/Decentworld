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
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.TitleBar_;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.component.ui.FriendDetailComponent_;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class FriendDetailSettingActivity_
    extends FriendDetailSettingActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_friend_detail_setting);
    }

    private void init_(Bundle savedInstanceState) {
        friend_detail_Component = FriendDetailComponent_.getInstance_(this);
        titleBar = TitleBar_.getInstance_(this);
        toast = ToastComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        ll_friend_detail_set_remark = ((LinearLayout) findViewById(id.ll_friend_detail_set_remark));
        iv_friend_detail_setting_2 = ((ImageView) findViewById(id.iv_friend_detail_setting_2));
        iv_friend_detail_setting_1 = ((ImageView) findViewById(id.iv_friend_detail_setting_1));
        ll_friend_detail_delete_contact = ((LinearLayout) findViewById(id.ll_friend_detail_delete_contact));
        iv_friend_detail_setting_3 = ((ImageView) findViewById(id.iv_friend_detail_setting_3));
        ll_friend_detail_whistle_blowing = ((LinearLayout) findViewById(id.ll_friend_detail_whistle_blowing));
        ll_friend_detail_root = ((LinearLayout) findViewById(id.ll_friend_detail_root));
        {
            View view = findViewById(id.main_header_left);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        FriendDetailSettingActivity_.this.back();
                    }

                }
                );
            }
        }
        ((FriendDetailComponent_) friend_detail_Component).afterSetContentView_();
        ((TitleBar_) titleBar).afterSetContentView_();
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

    public static FriendDetailSettingActivity_.IntentBuilder_ intent(Context context) {
        return new FriendDetailSettingActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, FriendDetailSettingActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public FriendDetailSettingActivity_.IntentBuilder_ flags(int flags) {
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
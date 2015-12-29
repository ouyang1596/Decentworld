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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.network.request.GetUserInfo_;
import cn.sx.decentworld.widget.VerticalSeekBar;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class ExamineWelcomeActivity_
    extends ExamineWelcomeActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_examine_welcome);
    }

    private void init_(Bundle savedInstanceState) {
        getUserInfo = GetUserInfo_.getInstance_(this);
        toast = ToastComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        lvExamineSupport = ((ListView) findViewById(id.lv_examine_support));
        tvTryAgain = ((TextView) findViewById(id.tv_try_again));
        vsNoSupport = ((VerticalSeekBar) findViewById(id.vs_examine_no_support));
        tvBackLogin = ((TextView) findViewById(id.tv_back_login));
        tvAnotherMethod = ((TextView) findViewById(id.tv_another_method));
        vsSupport = ((VerticalSeekBar) findViewById(id.vs_examine_support));
        relCheckedSuccess = ((RelativeLayout) findViewById(id.rel_check_success));
        llCheckFailure = ((LinearLayout) findViewById(id.ll_check_failure));
        ((GetUserInfo_) getUserInfo).afterSetContentView_();
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

    public static ExamineWelcomeActivity_.IntentBuilder_ intent(Context context) {
        return new ExamineWelcomeActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ExamineWelcomeActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ExamineWelcomeActivity_.IntentBuilder_ flags(int flags) {
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

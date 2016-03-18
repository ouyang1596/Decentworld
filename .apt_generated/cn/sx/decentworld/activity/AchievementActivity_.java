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
import android.widget.EditText;
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class AchievementActivity_
    extends AchievementActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_achievement);
    }

    private void init_(Bundle savedInstanceState) {
    }

    private void afterSetContentView_() {
        tvEnsure = ((TextView) findViewById(id.tv_ensure));
        tvCareerSuccess = ((TextView) findViewById(id.tv_career_success));
        etShortIntroduce = ((EditText) findViewById(id.et_short_introduce));
        tvCareerStart = ((TextView) findViewById(id.tv_career_start));
        tvWan = ((TextView) findViewById(id.tv_wan));
        tvBack = ((TextView) findViewById(id.tv_back));
        etDetailIntroduce = ((EditText) findViewById(id.et_introduce_detail));
        tvCareerIsToStart = ((TextView) findViewById(id.tv_career_isto_success));
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

    public static AchievementActivity_.IntentBuilder_ intent(Context context) {
        return new AchievementActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, AchievementActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public AchievementActivity_.IntentBuilder_ flags(int flags) {
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

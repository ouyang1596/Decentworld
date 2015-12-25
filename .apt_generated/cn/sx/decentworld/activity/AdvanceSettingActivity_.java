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
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class AdvanceSettingActivity_
    extends AdvanceSettingActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_advance_setting);
    }

    private void init_(Bundle savedInstanceState) {
        titleBar = TitleBar_.getInstance_(this);
    }

    private void afterSetContentView_() {
        iv_advance_setting_push_appearance = ((ImageView) findViewById(id.iv_advance_setting_push_appearance));
        iv_advance_setting_1 = ((ImageView) findViewById(id.iv_advance_setting_1));
        iv_advance_setting_realname = ((ImageView) findViewById(id.iv_advance_setting_realname));
        iv_advance_setting_nickname = ((ImageView) findViewById(id.iv_advance_setting_nickname));
        ll_advance_setting_root = ((LinearLayout) findViewById(id.ll_advance_setting_root));
        iv_advance_setting_autoTransfer = ((ImageView) findViewById(id.iv_advance_setting_autoTransfer));
        iv_advance_setting_dw = ((ImageView) findViewById(id.iv_advance_setting_dw));
        iv_advance_setting_2 = ((ImageView) findViewById(id.iv_advance_setting_2));
        iv_advance_setting_phone = ((ImageView) findViewById(id.iv_advance_setting_phone));
        {
            View view = findViewById(id.main_header_left);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        AdvanceSettingActivity_.this.back();
                    }

                }
                );
            }
        }
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

    public static AdvanceSettingActivity_.IntentBuilder_ intent(Context context) {
        return new AdvanceSettingActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, AdvanceSettingActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public AdvanceSettingActivity_.IntentBuilder_ flags(int flags) {
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

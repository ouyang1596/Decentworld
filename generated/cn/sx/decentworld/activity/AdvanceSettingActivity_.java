//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;

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
    }

    private void afterSetContentView_() {
        iv_advance_setting_realname = ((ImageView) findViewById(id.iv_advance_setting_realname));
        main_header_right_tv = ((TextView) findViewById(id.main_header_right_tv));
        main_header_left_tv = ((TextView) findViewById(id.main_header_left_tv));
        ll_advance_setting_root = ((LinearLayout) findViewById(id.ll_advance_setting_root));
        iv_advance_setting_nickname = ((ImageView) findViewById(id.iv_advance_setting_nickname));
        main_header_title = ((TextView) findViewById(id.main_header_title));
        iv_advance_setting_1 = ((ImageView) findViewById(id.iv_advance_setting_1));
        main_header_left = ((LinearLayout) findViewById(id.main_header_left));
        main_header_right_btn = ((ImageView) findViewById(id.main_header_right_btn));
        iv_advance_setting_dw = ((ImageView) findViewById(id.iv_advance_setting_dw));
        iv_advance_setting_phone = ((ImageView) findViewById(id.iv_advance_setting_phone));
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

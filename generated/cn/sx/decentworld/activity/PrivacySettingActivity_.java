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
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent_;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class PrivacySettingActivity_
    extends PrivacySettingActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_privacy_setting);
    }

    private void init_(Bundle savedInstanceState) {
        toast = ToastComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        privacy_setting_password = ((TextView) findViewById(id.privacy_setting_password));
        main_header_left = ((LinearLayout) findViewById(id.main_header_left));
        privacy_setting_phone_number = ((TextView) findViewById(id.privacy_setting_phone_number));
        privacy_setting_bank_card = ((TextView) findViewById(id.privacy_setting_bank_card));
        ll_privacy_setting_root = ((LinearLayout) findViewById(id.ll_privacy_setting_root));
        main_header_right_btn = ((ImageView) findViewById(id.main_header_right_btn));
        main_header_left_tv = ((TextView) findViewById(id.main_header_left_tv));
        ll_root = ((LinearLayout) findViewById(id.ll_root));
        main_header_right_tv = ((TextView) findViewById(id.main_header_right_tv));
        main_header_title = ((TextView) findViewById(id.main_header_title));
        {
            View view = findViewById(id.main_header_left);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        PrivacySettingActivity_.this.setBackBtn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.privacy_setting_modification_bank_card);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        PrivacySettingActivity_.this.bankCardModification(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.privacy_setting_modification_phone_number);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        PrivacySettingActivity_.this.bankCardModification(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.privacy_setting_modification_password);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        PrivacySettingActivity_.this.bankCardModification(view);
                    }

                }
                );
            }
        }
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

    public static PrivacySettingActivity_.IntentBuilder_ intent(Context context) {
        return new PrivacySettingActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, PrivacySettingActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public PrivacySettingActivity_.IntentBuilder_ flags(int flags) {
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

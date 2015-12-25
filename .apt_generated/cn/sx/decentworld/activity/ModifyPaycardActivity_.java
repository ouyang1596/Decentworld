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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.TitleBar_;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.network.request.SetUserInfo_;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class ModifyPaycardActivity_
    extends ModifyPaycardActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_modify_paycard);
    }

    private void init_(Bundle savedInstanceState) {
        setUserInfo = SetUserInfo_.getInstance_(this);
        titleBar = TitleBar_.getInstance_(this);
        toast = ToastComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        main_header_left = ((LinearLayout) findViewById(id.main_header_left));
        rb_paycard_weixin = ((RadioButton) findViewById(id.rb_paycard_weixin));
        et_paycard_weixin = ((EditText) findViewById(id.et_paycard_weixin));
        rb_paycard_alipay = ((RadioButton) findViewById(id.rb_paycard_alipay));
        btn_paycard_confirm = ((Button) findViewById(id.btn_paycard_confirm));
        et_paycard_alipay = ((EditText) findViewById(id.et_paycard_alipay));
        ((SetUserInfo_) setUserInfo).afterSetContentView_();
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

    public static ModifyPaycardActivity_.IntentBuilder_ intent(Context context) {
        return new ModifyPaycardActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ModifyPaycardActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ModifyPaycardActivity_.IntentBuilder_ flags(int flags) {
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

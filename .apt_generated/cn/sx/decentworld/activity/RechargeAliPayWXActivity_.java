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
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.KeyboardComponent_;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class RechargeAliPayWXActivity_
    extends RechargeAliPayWXActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_recharge_ali_pay_wx);
    }

    private void init_(Bundle savedInstanceState) {
        keyboardComponent = KeyboardComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        btnEunsre = ((Button) findViewById(id.btn_ensure));
        etMoney = ((EditText) findViewById(id.et_money));
        ivBack = ((ImageView) findViewById(id.iv_back));
        tvTitle = ((TextView) findViewById(id.tv_header_title));
        llpayMethod = ((LinearLayout) findViewById(id.ll_pay_method));
        {
            View view = findViewById(id.iv_back);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        RechargeAliPayWXActivity_.this.back();
                    }

                }
                );
            }
        }
        ((KeyboardComponent_) keyboardComponent).afterSetContentView_();
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

    public static RechargeAliPayWXActivity_.IntentBuilder_ intent(Context context) {
        return new RechargeAliPayWXActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, RechargeAliPayWXActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public RechargeAliPayWXActivity_.IntentBuilder_ flags(int flags) {
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

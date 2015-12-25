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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.MainHeaderComponent_;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.component.ui.RechargeComponent_;

public final class RechargeActivity_
    extends RechargeActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_recharge);
    }

    private void init_(Bundle savedInstanceState) {
        toast = ToastComponent_.getInstance_(this);
        rechargeComponent = RechargeComponent_.getInstance_(this);
        mainHeaderComponent = MainHeaderComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        btn_recharge_upmp = ((Button) findViewById(id.btn_recharge_upmp));
        et_recharge_amount = ((EditText) findViewById(id.et_recharge_amount));
        ll_recharge_root = ((LinearLayout) findViewById(id.ll_recharge_root));
        btn_recharge_bfb = ((Button) findViewById(id.btn_recharge_bfb));
        btn_recharge_jdpay = ((Button) findViewById(id.btn_recharge_jdpay));
        btn_recharge_alipay = ((Button) findViewById(id.btn_recharge_alipay));
        btn_recharge_wechat = ((Button) findViewById(id.btn_recharge_wechat));
        ((ToastComponent_) toast).afterSetContentView_();
        ((RechargeComponent_) rechargeComponent).afterSetContentView_();
        ((MainHeaderComponent_) mainHeaderComponent).afterSetContentView_();
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

    public static RechargeActivity_.IntentBuilder_ intent(Context context) {
        return new RechargeActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, RechargeActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public RechargeActivity_.IntentBuilder_ flags(int flags) {
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

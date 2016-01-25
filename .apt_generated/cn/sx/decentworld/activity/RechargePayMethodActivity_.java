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
import cn.sx.decentworld.component.ui.RechargeComponent_;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class RechargePayMethodActivity_
    extends RechargePayMethodActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_recharge_pay_method);
    }

    private void init_(Bundle savedInstanceState) {
        toast = ToastComponent_.getInstance_(this);
        rechargeComponent = RechargeComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        llAliPay = ((LinearLayout) findViewById(id.ll_ali_pay));
        llWX = ((LinearLayout) findViewById(id.ll_wx));
        tvTitle = ((TextView) findViewById(id.tv_header_title));
        ivBack = ((ImageView) findViewById(id.iv_back));
        {
            View view = findViewById(id.iv_back);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        RechargePayMethodActivity_.this.back();
                    }

                }
                );
            }
        }
        ((ToastComponent_) toast).afterSetContentView_();
        ((RechargeComponent_) rechargeComponent).afterSetContentView_();
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

    public static RechargePayMethodActivity_.IntentBuilder_ intent(Context context) {
        return new RechargePayMethodActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, RechargePayMethodActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public RechargePayMethodActivity_.IntentBuilder_ flags(int flags) {
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

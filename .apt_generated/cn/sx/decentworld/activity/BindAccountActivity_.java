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
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.network.request.GetUserInfo_;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class BindAccountActivity_
    extends BindAccountActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_bind_account);
    }

    private void init_(Bundle savedInstanceState) {
        toast = ToastComponent_.getInstance_(this);
        getUserInfo = GetUserInfo_.getInstance_(this);
    }

    private void afterSetContentView_() {
        tv_bind_account_wx = ((TextView) findViewById(id.tv_bind_account_wx));
        tv_bind_account_alipay = ((TextView) findViewById(id.tv_bind_account_alipay));
        {
            View view = findViewById(id.tv_bind_account_alipay);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BindAccountActivity_.this.bindAlipay(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.tv_bind_account_wx);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        BindAccountActivity_.this.bindWx(view);
                    }

                }
                );
            }
        }
        ((ToastComponent_) toast).afterSetContentView_();
        ((GetUserInfo_) getUserInfo).afterSetContentView_();
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

    public static BindAccountActivity_.IntentBuilder_ intent(Context context) {
        return new BindAccountActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, BindAccountActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public BindAccountActivity_.IntentBuilder_ flags(int flags) {
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
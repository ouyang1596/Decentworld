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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.TitleBar_;
import cn.sx.decentworld.component.ToastComponent_;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class ModifyIpActivity_
    extends ModifyIpActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_modify_ip);
    }

    private void init_(Bundle savedInstanceState) {
        titleBar = TitleBar_.getInstance_(this);
        toast = ToastComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        et_modify_one = ((EditText) findViewById(id.et_modify_one));
        tv_modify_result = ((TextView) findViewById(id.tv_modify_result));
        et_modify_two = ((EditText) findViewById(id.et_modify_two));
        main_header_left = ((LinearLayout) findViewById(id.main_header_left));
        main_header_right = ((RelativeLayout) findViewById(id.main_header_right));
        et_modify_three = ((EditText) findViewById(id.et_modify_three));
        et_modify_four = ((EditText) findViewById(id.et_modify_four));
        lv_modify_ip = ((ListView) findViewById(id.lv_modify_ip));
        {
            View view = findViewById(id.btn_modify_complete);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ModifyIpActivity_.this.complete(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.main_header_left);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ModifyIpActivity_.this.back(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.main_header_right);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ModifyIpActivity_.this.right(view);
                    }

                }
                );
            }
        }
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

    public static ModifyIpActivity_.IntentBuilder_ intent(Context context) {
        return new ModifyIpActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ModifyIpActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ModifyIpActivity_.IntentBuilder_ flags(int flags) {
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

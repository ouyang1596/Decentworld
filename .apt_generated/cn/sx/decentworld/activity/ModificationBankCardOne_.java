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
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.TitleBar_;
import cn.sx.decentworld.component.ToastComponent_;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class ModificationBankCardOne_
    extends ModificationBankCardOne
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_modification_bank_card_one);
    }

    private void init_(Bundle savedInstanceState) {
        toast = ToastComponent_.getInstance_(this);
        titleBar = TitleBar_.getInstance_(this);
    }

    private void afterSetContentView_() {
        btn_modification_bank_card_one_next = ((Button) findViewById(id.btn_modification_bank_card_one_next));
        tvRealName = ((TextView) findViewById(id.et_modification_bank_card_one_name));
        etBankCard = ((EditText) findViewById(id.et_modification_bank_card_one_account));
        {
            View view = findViewById(id.main_header_left);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ModificationBankCardOne_.this.setBackBtn();
                    }

                }
                );
            }
        }
        ((ToastComponent_) toast).afterSetContentView_();
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

    public static ModificationBankCardOne_.IntentBuilder_ intent(Context context) {
        return new ModificationBankCardOne_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ModificationBankCardOne_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ModificationBankCardOne_.IntentBuilder_ flags(int flags) {
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

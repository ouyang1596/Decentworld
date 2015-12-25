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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.TitleBar_;
import cn.sx.decentworld.component.ToastComponent_;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class ChatSettingWhistleblowingActivity_
    extends ChatSettingWhistleblowingActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.chat_setting_whistleblowing);
    }

    private void init_(Bundle savedInstanceState) {
        titleBar = TitleBar_.getInstance_(this);
        toast = ToastComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        activity_whistleblowing_reason_3 = ((RadioButton) findViewById(id.activity_whistleblowing_reason_3));
        activity_whistleblowing_reason_5 = ((RadioButton) findViewById(id.activity_whistleblowing_reason_5));
        activity_whistleblowing_reason_2 = ((RadioButton) findViewById(id.activity_whistleblowing_reason_2));
        ll_chat_setting_whistleblowing_2 = ((LinearLayout) findViewById(id.ll_chat_setting_whistleblowing_2));
        activity_whistleblowing_reason_1 = ((RadioButton) findViewById(id.activity_whistleblowing_reason_1));
        activity_whistleblowing_reason_4 = ((RadioButton) findViewById(id.activity_whistleblowing_reason_4));
        ll_chat_setting_whistleblowing_3 = ((LinearLayout) findViewById(id.ll_chat_setting_whistleblowing_3));
        ll_chat_setting_whistleblowing_1 = ((LinearLayout) findViewById(id.ll_chat_setting_whistleblowing_1));
        ll_chat_setting_whistleblowing_6 = ((LinearLayout) findViewById(id.ll_chat_setting_whistleblowing_6));
        chat_setting_whistleblowing_supplement = ((LinearLayout) findViewById(id.chat_setting_whistleblowing_supplement));
        ll_chat_setting_whistleblowing_5 = ((LinearLayout) findViewById(id.ll_chat_setting_whistleblowing_5));
        activity_whistleblowing_reason_6 = ((RadioButton) findViewById(id.activity_whistleblowing_reason_6));
        ll_chat_setting_whistleblowing_4 = ((LinearLayout) findViewById(id.ll_chat_setting_whistleblowing_4));
        {
            View view = findViewById(id.ll_chat_setting_whistleblowing_1);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatSettingWhistleblowingActivity_.this.radioCheck(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.ll_chat_setting_whistleblowing_2);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatSettingWhistleblowingActivity_.this.radioCheck(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.ll_chat_setting_whistleblowing_3);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatSettingWhistleblowingActivity_.this.radioCheck(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.ll_chat_setting_whistleblowing_4);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatSettingWhistleblowingActivity_.this.radioCheck(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.ll_chat_setting_whistleblowing_5);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatSettingWhistleblowingActivity_.this.radioCheck(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.ll_chat_setting_whistleblowing_6);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatSettingWhistleblowingActivity_.this.radioCheck(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.chat_setting_whistleblowing_supplement);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatSettingWhistleblowingActivity_.this.supplement();
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
                        ChatSettingWhistleblowingActivity_.this.setBackBtn();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.main_header_right_tv);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ChatSettingWhistleblowingActivity_.this.submit();
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

    public static ChatSettingWhistleblowingActivity_.IntentBuilder_ intent(Context context) {
        return new ChatSettingWhistleblowingActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ChatSettingWhistleblowingActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ChatSettingWhistleblowingActivity_.IntentBuilder_ flags(int flags) {
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

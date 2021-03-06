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
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.component.ui.MainFragmentComponent_;
import cn.sx.decentworld.widget.ListViewForScrollView;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class GroupContactActivity_
    extends GroupContactActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.fragment_chat_chat_group);
    }

    private void init_(Bundle savedInstanceState) {
        component = MainFragmentComponent_.getInstance_(this);
        compon = ToastComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        groupListView = ((ListViewForScrollView) findViewById(id.lv_group_chat_joined));
        progressBar = ((LinearLayout) findViewById(id.progress_bar));
        {
            View view = findViewById(id.main_header_right_btn);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GroupContactActivity_.this.tobuild_group();
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
                        GroupContactActivity_.this.click_back();
                    }

                }
                );
            }
        }
        ((MainFragmentComponent_) component).afterSetContentView_();
        ((ToastComponent_) compon).afterSetContentView_();
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

    public static GroupContactActivity_.IntentBuilder_ intent(Context context) {
        return new GroupContactActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, GroupContactActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public GroupContactActivity_.IntentBuilder_ flags(int flags) {
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

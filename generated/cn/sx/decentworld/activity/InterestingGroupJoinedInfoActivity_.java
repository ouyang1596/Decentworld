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
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.widget.ExpandGridView;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class InterestingGroupJoinedInfoActivity_
    extends InterestingGroupJoinedInfoActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_interesting_group_joined_item_info);
    }

    private void init_(Bundle savedInstanceState) {
        toast = ToastComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        main_header_title = ((TextView) findViewById(id.main_header_title));
        main_header_left = ((LinearLayout) findViewById(id.main_header_left));
        main_header_right = ((RelativeLayout) findViewById(id.main_header_right));
        interesting_group_joined_info_gridview = ((ExpandGridView) findViewById(id.interesting_group_joined_info_gridview));
        main_header_left_tv = ((TextView) findViewById(id.main_header_left_tv));
        inter_group_joined_no_interrupt = ((ImageView) findViewById(id.inter_group_joined_no_interrupt));
        inter_group_joined_settop = ((ImageView) findViewById(id.inter_group_joined_settop));
        {
            View view = findViewById(id.inter_group_joined_settop);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        InterestingGroupJoinedInfoActivity_.this.change_icon();
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
                        InterestingGroupJoinedInfoActivity_.this.doback();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.inter_group_joined_no_interrupt);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        InterestingGroupJoinedInfoActivity_.this.change_icon2();
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

    public static InterestingGroupJoinedInfoActivity_.IntentBuilder_ intent(Context context) {
        return new InterestingGroupJoinedInfoActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, InterestingGroupJoinedInfoActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public InterestingGroupJoinedInfoActivity_.IntentBuilder_ flags(int flags) {
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

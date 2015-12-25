//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent_;

public final class MagnateToMeActivity_
    extends MagnateToMeActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_magnate_to_me);
    }

    private void init_(Bundle savedInstanceState) {
        toast = ToastComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        main_header_right_btn = ((ImageView) findViewById(id.main_header_right_btn));
        ll_magnate_to_me_root = ((LinearLayout) findViewById(id.ll_magnate_to_me_root));
        main_header_title = ((TextView) findViewById(id.main_header_title));
        main_header_left_tv = ((TextView) findViewById(id.main_header_left_tv));
        main_header_left = ((LinearLayout) findViewById(id.main_header_left));
        main_header_right_tv = ((TextView) findViewById(id.main_header_right_tv));
        tv_magnate_to_me_name = ((TextView) findViewById(id.tv_magnate_to_me_name));
        {
            View view = findViewById(id.rl_magnate_to_me_add_magnate);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        MagnateToMeActivity_.this.addMagnate();
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
                        MagnateToMeActivity_.this.setBackBtn();
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

    public static MagnateToMeActivity_.IntentBuilder_ intent(Context context) {
        return new MagnateToMeActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, MagnateToMeActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public MagnateToMeActivity_.IntentBuilder_ flags(int flags) {
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

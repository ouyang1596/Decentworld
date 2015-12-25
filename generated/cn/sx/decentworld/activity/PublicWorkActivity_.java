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
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ChoceAndTakePictureComponent_;

public final class PublicWorkActivity_
    extends PublicWorkActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_publicwork);
    }

    private void init_(Bundle savedInstanceState) {
        c = ChoceAndTakePictureComponent_.getInstance_(this);
    }

    private void afterSetContentView_() {
        {
            View view = findViewById(id.button1);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        PublicWorkActivity_.this.button1();
                    }

                }
                );
            }
        }
        ((ChoceAndTakePictureComponent_) c).afterSetContentView_();
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

    public static PublicWorkActivity_.IntentBuilder_ intent(Context context) {
        return new PublicWorkActivity_.IntentBuilder_(context);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        PublicWorkActivity_.super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case  10 :
                PublicWorkActivity_.this.onResult(resultCode, data);
                break;
        }
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, PublicWorkActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public PublicWorkActivity_.IntentBuilder_ flags(int flags) {
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

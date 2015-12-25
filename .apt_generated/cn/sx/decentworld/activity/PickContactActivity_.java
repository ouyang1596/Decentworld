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
import android.widget.ListView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.TitleBar_;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.component.ui.MainFragmentComponent_;
import cn.sx.decentworld.network.request.SetUserInfo_;
import cn.sx.decentworld.widget.HorizontalListView;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class PickContactActivity_
    extends PickContactActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_pick_contact);
    }

    private void init_(Bundle savedInstanceState) {
        titleBar = TitleBar_.getInstance_(this);
        maincompon = MainFragmentComponent_.getInstance_(this);
        toast = ToastComponent_.getInstance_(this);
        setUserInfo = SetUserInfo_.getInstance_(this);
    }

    private void afterSetContentView_() {
        lv_pick_contact_person = ((ListView) findViewById(id.lv_pick_contact_person));
        ll_listview_all_picked = ((LinearLayout) findViewById(id.ll_listview_all_picked));
        gv_people_had_added = ((HorizontalListView) findViewById(id.imgList));
        {
            View view = findViewById(id.main_header_right_tv);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        PickContactActivity_.this.Confirm();
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
                        PickContactActivity_.this.back();
                    }

                }
                );
            }
        }
        ((TitleBar_) titleBar).afterSetContentView_();
        ((MainFragmentComponent_) maincompon).afterSetContentView_();
        ((ToastComponent_) toast).afterSetContentView_();
        ((SetUserInfo_) setUserInfo).afterSetContentView_();
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

    public static PickContactActivity_.IntentBuilder_ intent(Context context) {
        return new PickContactActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, PickContactActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public PickContactActivity_.IntentBuilder_ flags(int flags) {
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

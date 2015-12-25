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
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.network.request.GetStrangerInfo_;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class NearCardDetail2Activity_
    extends NearCardDetail2Activity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_near_card_detail1);
    }

    private void init_(Bundle savedInstanceState) {
        toast = ToastComponent_.getInstance_(this);
        getStrangerInfo = GetStrangerInfo_.getInstance_(this);
    }

    private void afterSetContentView_() {
        btnOK = ((Button) findViewById(id.btn_OK));
        lvAnonymousInfo = ((ListView) findViewById(id.lv_anonymous_info));
        imgv2 = ((ImageView) findViewById(id.imgv2));
        ivBack = ((ImageView) findViewById(id.iv_back));
        ivSex = ((ImageView) findViewById(id.iv_sex));
        tvAge = ((TextView) findViewById(id.tv_age));
        tvUserSign = ((TextView) findViewById(id.tv_user_detail_info_sign));
        tvTitle = ((TextView) findViewById(id.tv_header_title));
        tvAddr = ((TextView) findViewById(id.tv_addr));
        ivShowIcon = ((ImageView) findViewById(id.iv_show_icon));
        imgv1 = ((ImageView) findViewById(id.imgv1));
        tvRealNameNickname = ((TextView) findViewById(id.tv_realname_nickname));
        imgv3 = ((ImageView) findViewById(id.imgv3));
        ((ToastComponent_) toast).afterSetContentView_();
        ((GetStrangerInfo_) getStrangerInfo).afterSetContentView_();
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

    public static NearCardDetail2Activity_.IntentBuilder_ intent(Context context) {
        return new NearCardDetail2Activity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, NearCardDetail2Activity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public NearCardDetail2Activity_.IntentBuilder_ flags(int flags) {
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
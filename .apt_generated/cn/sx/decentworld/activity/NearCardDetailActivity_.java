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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.sx.decentworld.R.id;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.network.request.GetStrangerInfo_;
import cn.sx.decentworld.widget.HackyViewPager;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class NearCardDetailActivity_
    extends NearCardDetailActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_near_card_detail);
    }

    private void init_(Bundle savedInstanceState) {
        toast = ToastComponent_.getInstance_(this);
        getStrangerInfo = GetStrangerInfo_.getInstance_(this);
    }

    private void afterSetContentView_() {
        tvDetail = ((TextView) findViewById(id.tv_detail));
        ivSex = ((ImageView) findViewById(id.iv_sex));
        ivBack = ((ImageView) findViewById(id.iv_back));
        lvStrangerInfo = ((ListView) findViewById(id.lv_anonymous_info));
        mPager = ((HackyViewPager) findViewById(id.vp_near_card_detail_pager));
        llDetail = ((LinearLayout) findViewById(id.ll_detail));
        ivTalkToStranger = ((ImageView) findViewById(id.iv_talk_stranger));
        llRootWorks = ((LinearLayout) findViewById(id.ll_root_works));
        tvLocation = ((TextView) findViewById(id.item_nearby_stranger_location));
        ivLike = ((ImageView) findViewById(id.iv_like));
        tvWorth = ((TextView) findViewById(id.item_nearby_stranger_worth));
        llWorks = ((LinearLayout) findViewById(id.ll_works));
        tvAge = ((TextView) findViewById(id.tv_age));
        fCardDetail = ((FrameLayout) findViewById(id.activity_near_card_detail_root));
        ivDislike = ((ImageView) findViewById(id.iv_dislike));
        tvUserSign = ((TextView) findViewById(id.tv_user_detail_info_sign));
        tvRealNameNickname = ((TextView) findViewById(id.tv_realname_nickname));
        tvWorks = ((TextView) findViewById(id.tv_works));
        llRootDetailInfo = ((LinearLayout) findViewById(id.user_detail_info_root));
        ivDetailLine = ((ImageView) findViewById(id.line_iv_detail));
        tvTitle = ((TextView) findViewById(id.tv_header_title));
        ivWorksLine = ((ImageView) findViewById(id.line_iv_works));
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

    public static NearCardDetailActivity_.IntentBuilder_ intent(Context context) {
        return new NearCardDetailActivity_.IntentBuilder_(context);
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, NearCardDetailActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public NearCardDetailActivity_.IntentBuilder_ flags(int flags) {
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

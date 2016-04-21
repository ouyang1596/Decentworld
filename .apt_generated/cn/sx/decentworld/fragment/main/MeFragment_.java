//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.network.request.GetUserInfo_;
import cn.sx.decentworld.widget.CustomScrollView;
import cn.sx.decentworld.widget.HackyViewPager;

public final class MeFragment_
    extends MeFragment
{

    private View contentView_;

    private void init_(Bundle savedInstanceState) {
        getUserInfo = GetUserInfo_.getInstance_(getActivity());
        toast = ToastComponent_.getInstance_(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_() {
        iv_me_recharge = ((ImageView) findViewById(cn.sx.decentworld.R.id.iv_me_recharge));
        iv_me_setting = ((ImageView) findViewById(cn.sx.decentworld.R.id.iv_me_setting));
        tv_me_short_introduce = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_me_short_introduce));
        mTvWorth = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_me_worth));
        mLlWealthContainer = ((LinearLayout) findViewById(cn.sx.decentworld.R.id.ll_me_wealth));
        tv_me_name = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_me_name));
        tv_me_age = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_me_age));
        tv_me_innate = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_me_innate));
        tv_me_sign = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_me_sign));
        cScroll = ((CustomScrollView) findViewById(cn.sx.decentworld.R.id.sv_chat_me));
        ll = ((LinearLayout) findViewById(cn.sx.decentworld.R.id.ll));
        iv_me_name_type = ((ImageView) findViewById(cn.sx.decentworld.R.id.iv_me_name_type));
        ivGender = ((ImageView) findViewById(cn.sx.decentworld.R.id.iv_me_gender));
        mPager = ((HackyViewPager) findViewById(cn.sx.decentworld.R.id.vp_me_icon));
        tv_me_achievement = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_me_achievement));
        mTvWealth = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_me_wealth));
        tv_me_distance = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_me_distance));
        tv_me_work = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_me_work));
        tv_me_gender = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_me_gender));
        mIvUserType = ((ImageView) findViewById(cn.sx.decentworld.R.id.iv_me_doubt));
        tv_me_occupation = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_me_occupation));
        ((GetUserInfo_) getUserInfo).afterSetContentView_();
        ((ToastComponent_) toast).afterSetContentView_();
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.fragment_me, container, false);
        }
        return contentView_;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterSetContentView_();
    }

    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    public static MeFragment_.FragmentBuilder_ builder() {
        return new MeFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public MeFragment build() {
            MeFragment_ fragment_ = new MeFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}

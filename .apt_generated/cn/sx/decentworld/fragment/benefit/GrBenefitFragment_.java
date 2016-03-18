//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.fragment.benefit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent_;
import cn.sx.decentworld.network.request.GetUserInfo_;
import cn.sx.decentworld.network.request.SetUserInfo_;
import cn.sx.decentworld.widget.CircularImage;
import cn.sx.decentworld.widget.ListViewForScrollView;

public final class GrBenefitFragment_
    extends GrBenefitFragment
{

    private View contentView_;

    private void init_(Bundle savedInstanceState) {
        toast = ToastComponent_.getInstance_(getActivity());
        getUserInfo = GetUserInfo_.getInstance_(getActivity());
        setUserInfo = SetUserInfo_.getInstance_(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_() {
        lv_gr_list = ((ListViewForScrollView) findViewById(cn.sx.decentworld.R.id.lv_gr_list));
        tv_gr_delete = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_gr_delete));
        iv_gr_icon = ((CircularImage) findViewById(cn.sx.decentworld.R.id.iv_gr_icon));
        tv_gr_rule = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_gr_rule));
        tv_gr_modify = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_gr_modify));
        ll_gr_reminds = ((LinearLayout) findViewById(cn.sx.decentworld.R.id.ll_gr_remind));
        tv_gr_all_benefit = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_gr_all_benefit));
        sl_gr_benefit = ((ScrollView) findViewById(cn.sx.decentworld.R.id.sl_gr_benefit));
        ((ToastComponent_) toast).afterSetContentView_();
        ((GetUserInfo_) getUserInfo).afterSetContentView_();
        ((SetUserInfo_) setUserInfo).afterSetContentView_();
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.fragment_gr_benefit, container, false);
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

    public static GrBenefitFragment_.FragmentBuilder_ builder() {
        return new GrBenefitFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public GrBenefitFragment build() {
            GrBenefitFragment_ fragment_ = new GrBenefitFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
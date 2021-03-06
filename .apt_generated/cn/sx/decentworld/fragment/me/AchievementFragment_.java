//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.fragment.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent_;

public final class AchievementFragment_
    extends AchievementFragment
{

    private View contentView_;

    private void init_(Bundle savedInstanceState) {
        toast = ToastComponent_.getInstance_(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_() {
        ivAchievement = ((ImageView) findViewById(cn.sx.decentworld.R.id.iv_achievement));
        tvDetailIntroduce = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_detail_introduce));
        llIntroduce = ((LinearLayout) findViewById(cn.sx.decentworld.R.id.ll_introduce));
        tvAchievement = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_achievement));
        ivEditIntroduce = ((ImageView) findViewById(cn.sx.decentworld.R.id.iv_edit_introduce));
        tvShortIntroduce = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_short_introduce));
        flAchievement = ((FrameLayout) findViewById(cn.sx.decentworld.R.id.fl_achievement));
        ((ToastComponent_) toast).afterSetContentView_();
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.fragment_achievement, container, false);
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

    public static AchievementFragment_.FragmentBuilder_ builder() {
        return new AchievementFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public AchievementFragment build() {
            AchievementFragment_ fragment_ = new AchievementFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}

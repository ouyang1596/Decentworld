//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.fragment.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ToastComponent_;

public final class PrivacySettingFragment_
    extends PrivacySettingFragment
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
        mTvPhoneNum = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_phone_number));
        mLlRoot = ((LinearLayout) findViewById(cn.sx.decentworld.R.id.ll_privacy_setting_root));
        {
            View view = findViewById(cn.sx.decentworld.R.id.privacy_setting_modification_phone_number);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        PrivacySettingFragment_.this.bankCardModification(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(cn.sx.decentworld.R.id.privacy_setting_modification_password);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        PrivacySettingFragment_.this.bankCardModification(view);
                    }

                }
                );
            }
        }
        {
            View view = findViewById(cn.sx.decentworld.R.id.main_header_left);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        PrivacySettingFragment_.this.setBackBtn();
                    }

                }
                );
            }
        }
        ((ToastComponent_) toast).afterSetContentView_();
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.activity_privacy_setting, container, false);
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

    public static PrivacySettingFragment_.FragmentBuilder_ builder() {
        return new PrivacySettingFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public PrivacySettingFragment build() {
            PrivacySettingFragment_ fragment_ = new PrivacySettingFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}

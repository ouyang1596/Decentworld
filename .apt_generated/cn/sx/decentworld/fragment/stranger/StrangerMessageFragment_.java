//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.fragment.stranger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.network.request.GetUserInfo_;

public final class StrangerMessageFragment_
    extends StrangerMessageFragment
{

    private View contentView_;

    private void init_(Bundle savedInstanceState) {
        getUserInfo = GetUserInfo_.getInstance_(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_() {
        lvStrnagerMessage = ((ListView) findViewById(cn.sx.decentworld.R.id.lv_stranger_message));
        ((GetUserInfo_) getUserInfo).afterSetContentView_();
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.fragment_stranger_chat, container, false);
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

    public static StrangerMessageFragment_.FragmentBuilder_ builder() {
        return new StrangerMessageFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public StrangerMessageFragment build() {
            StrangerMessageFragment_ fragment_ = new StrangerMessageFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}

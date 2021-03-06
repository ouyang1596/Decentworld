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
import cn.sx.decentworld.widget.ContactScrollView;
import cn.sx.decentworld.widget.ListViewForScrollView;

public final class ContactFragment_
    extends ContactFragment
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
        fragment_chat_contact_lv = ((ListViewForScrollView) findViewById(cn.sx.decentworld.R.id.fragment_chat_contact_lv));
        llNewFriend = ((LinearLayout) findViewById(cn.sx.decentworld.R.id.ll_new_friend));
        tvAddNewFriends = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_add_new_friends));
        ivSearch = ((ImageView) findViewById(cn.sx.decentworld.R.id.iv_search));
        sclView = ((ContactScrollView) findViewById(cn.sx.decentworld.R.id.sclView));
        llContactList = ((LinearLayout) findViewById(cn.sx.decentworld.R.id.ll_contact_list));
        llContact = ((LinearLayout) findViewById(cn.sx.decentworld.R.id.ll_contact));
        ivInvite = ((ImageView) findViewById(cn.sx.decentworld.R.id.iv_invite));
        ivRecommend = ((ImageView) findViewById(cn.sx.decentworld.R.id.iv_recommend));
        new_friends_ll_unread_msg_number = ((TextView) findViewById(cn.sx.decentworld.R.id.new_friends_ll_unread_msg_number));
        ((ToastComponent_) toast).afterSetContentView_();
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.fragment_main_contact, container, false);
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

    public static ContactFragment_.FragmentBuilder_ builder() {
        return new ContactFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public ContactFragment build() {
            ContactFragment_ fragment_ = new ContactFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}

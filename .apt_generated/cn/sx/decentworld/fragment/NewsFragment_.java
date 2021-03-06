//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sx.decentworld.R.layout;
import cn.sx.decentworld.component.ui.ChatRoomComponent_;
import cn.sx.decentworld.network.request.GetRoomInfo_;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public final class NewsFragment_
    extends NewsFragment
{

    private View contentView_;

    private void init_(Bundle savedInstanceState) {
        chatRoomComponent = ChatRoomComponent_.getInstance_(getActivity());
        getRoomInfo = GetRoomInfo_.getInstance_(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void afterSetContentView_() {
        tvTitle = ((TextView) findViewById(cn.sx.decentworld.R.id.tv_header_title));
        ll_layout_news_root = ((LinearLayout) findViewById(cn.sx.decentworld.R.id.ll_layout_news_root));
        main_layout_news_lv = ((PullToRefreshListView) findViewById(cn.sx.decentworld.R.id.main_layout_news_lv));
        add_topic_detail = ((ImageView) findViewById(cn.sx.decentworld.R.id.add_topic_detail));
        chat_room_title_root = ((RelativeLayout) findViewById(cn.sx.decentworld.R.id.chat_room_title_root));
        ivMe = ((ImageView) findViewById(cn.sx.decentworld.R.id.iv_me));
        {
            View view = findViewById(cn.sx.decentworld.R.id.add_topic_detail);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        NewsFragment_.this.todetail();
                    }

                }
                );
            }
        }
        ((ChatRoomComponent_) chatRoomComponent).afterSetContentView_();
        ((GetRoomInfo_) getRoomInfo).afterSetContentView_();
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.main_layout_news, container, false);
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

    public static NewsFragment_.FragmentBuilder_ builder() {
        return new NewsFragment_.FragmentBuilder_();
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public NewsFragment build() {
            NewsFragment_ fragment_ = new NewsFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}

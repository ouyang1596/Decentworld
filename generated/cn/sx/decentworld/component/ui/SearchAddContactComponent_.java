//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package cn.sx.decentworld.component.ui;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import cn.sx.decentworld.component.ToastComponent_;
import com.googlecode.androidannotations.api.BackgroundExecutor;

public final class SearchAddContactComponent_
    extends SearchAddContactComponent
{

    private Context context_;

    private SearchAddContactComponent_(Context context) {
        context_ = context;
        init_();
    }

    public void afterSetContentView_() {
        if (!(context_ instanceof Activity)) {
            return ;
        }
        ((ToastComponent_) toast).afterSetContentView_();
        init();
    }

    /**
     * You should check that context is an activity before calling this method
     * 
     */
    public View findViewById(int id) {
        Activity activity_ = ((Activity) context_);
        return activity_.findViewById(id);
    }

    @SuppressWarnings("all")
    private void init_() {
        if (context_ instanceof Activity) {
            Activity activity = ((Activity) context_);
        }
        if (context_ instanceof Activity) {
            activity = ((Activity) context_);
        }
        context = context_;
        toast = ToastComponent_.getInstance_(context_);
    }

    public static SearchAddContactComponent_ getInstance_(Context context) {
        return new SearchAddContactComponent_(context);
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

    @Override
    public void addContact(final String values, final String request) {
        BackgroundExecutor.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    SearchAddContactComponent_.super.addContact(values, request);
                } catch (RuntimeException e) {
                    Log.e("SearchAddContactComponent_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

}
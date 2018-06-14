package com.huizhong.baselibs.Tools;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.huizhong.baselibs.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shaochun on 14-3-26.
 */
public class DisplayDialog extends Dialog {
    private final View layout;
    private LinearLayout parent_layout;
    public final static  int Dismiss=0;

    //定义回调事件
    public interface ClickListener {
        public void onclick(int types);
    }

    private Context context;

    private ClickListener clickListener;

    public DisplayDialog(Context context,View layout, ClickListener clickListener) {
        super(context, R.style.dialog);
        this.context = context;
        this.clickListener = clickListener;
        this.layout=layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_dialog);
        parent_layout=(LinearLayout)findViewById(R.id.parent_layout);
        parent_layout.addView(layout);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        init();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        clickListener.onclick(Dismiss);
    }

    private void init() {


    }



}

package com.huizhong.baselibs.Tools;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huizhong.baselibs.R;


/**
 * Created by shaochun on 14-3-26.
 */
public class DialogAd extends Dialog {

    private String title;
    private String txtInfo;
    private Boolean isMiss = false;
    private boolean isTouchOutside = true;

    private OnCustomDialogListener customDialogListener;

    public DialogAd(Context context, boolean isTouchOutside, String title, OnCustomDialogListener customDialogListener) {
        super(context, R.style.dialog);
        this.isTouchOutside = isTouchOutside;
        this.customDialogListener = customDialogListener;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ad);

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(this.title);


        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(clickListenerHander);

        setOnDismissListener(dismissListenerHander);
        setCanceledOnTouchOutside(this.isTouchOutside);
    }

    private View.OnClickListener clickListenerHander = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isMiss = true;
            customDialogListener.back("submit");
            DialogAd.this.dismiss();
        }
    };

    private OnDismissListener dismissListenerHander = new OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            if (!isMiss) customDialogListener.back("dismiss");
        }
    };

    //定义回调事件
    public interface OnCustomDialogListener {
        public void back(String types);
    }


}

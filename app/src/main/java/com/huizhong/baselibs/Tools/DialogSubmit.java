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
public class DialogSubmit extends Dialog {
    //定义回调事件
    public interface OnCustomDialogListener {
        public void back(String types);
    }

    private String title;
    private String txtInfo;
    private Boolean isMiss = false;
    private boolean isTouchOutside = true;

    private OnCustomDialogListener customDialogListener;

    public DialogSubmit(Context context, boolean isTouchOutside, String title, String txtInfo, OnCustomDialogListener customDialogListener) {
        super(context, R.style.dialog);
        this.isTouchOutside = isTouchOutside;
        this.customDialogListener = customDialogListener;
        this.title = title;
        this.txtInfo = txtInfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_submit);

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(this.title);

        TextView txtInfo = (TextView) findViewById(R.id.txtInfo);
        txtInfo.setText(this.txtInfo);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(clickListenerHander);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setVisibility(View.GONE);

        setOnDismissListener(dismissListenerHander);
        setCanceledOnTouchOutside(this.isTouchOutside);
    }

    private View.OnClickListener clickListenerHander = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isMiss = true;
            customDialogListener.back("submit");
            DialogSubmit.this.dismiss();
        }
    };

    private OnDismissListener dismissListenerHander = new OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            if (!isMiss) customDialogListener.back("dismiss");
        }
    };


}

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
public class DialogDownVersion extends Dialog {
    //定义回调事件
    public interface OnCustomDialogListener {
        public void back(String types);
    }

    private String title;
    private String txtInfo;
    private Boolean isMiss = false;

    private OnCustomDialogListener customDialogListener;

    public DialogDownVersion(Context context, String title, String txtInfo, OnCustomDialogListener customDialogListener) {
        super(context, R.style.dialog);
        this.customDialogListener = customDialogListener;
        this.title = title;
        this.txtInfo = txtInfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_down_version);

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(this.title);

        TextView txtInfo = (TextView) findViewById(R.id.txtInfo);
        txtInfo.setText(this.txtInfo);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(clickListenerHander);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(clickListenerHander2);

        setOnDismissListener(dismissListenerHander);
    }

    private View.OnClickListener clickListenerHander = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isMiss = true;
            customDialogListener.back("submit");
            DialogDownVersion.this.dismiss();
        }
    };

    private View.OnClickListener clickListenerHander2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };

    private OnDismissListener dismissListenerHander = new OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            if (!isMiss) customDialogListener.back("dismiss");
        }
    };


}

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
public class DeleteDialog extends Dialog {
    //定义回调事件
    public interface OnCustomDialogListener {
        public void back(String types);
    }

    private String title;
    private String submitText;
    private Boolean isMiss = false;

    private OnCustomDialogListener customDialogListener;

    public DeleteDialog(Context context, String title, String submitText, OnCustomDialogListener customDialogListener) {
        super(context, R.style.dialog);
        this.customDialogListener = customDialogListener;
        this.title = title;
        this.submitText = submitText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete);

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(this.title);

        Button submit = (Button) findViewById(R.id.submit);
        if (this.submitText!=null&&!this.submitText.equals("")) {
            submit.setText(this.submitText);
        }
        submit.setOnClickListener(clickListenerHander);
        setOnDismissListener(dismissListenerHander);
    }

    private View.OnClickListener clickListenerHander = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isMiss = true;
            customDialogListener.back("del");
            DeleteDialog.this.dismiss();
        }
    };
    private OnDismissListener dismissListenerHander = new OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            if (!isMiss) customDialogListener.back("dismiss");
        }
    };


}

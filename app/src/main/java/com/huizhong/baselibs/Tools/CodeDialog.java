package com.huizhong.baselibs.Tools;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huizhong.baselibs.R;


public class CodeDialog extends Dialog {
    private Context mContext;
    private Resources mResources;

    private onTextViewDialogClickListener mListener;
    private TextView code_ok_btn;
    private ImageView dialog_view_code;
    private LinearLayout dialog_code_rotate;
    private onRotateClickListener mRotateListener;
    private TextView text_rotate_repeat;
    private EditText edit_group_1;
    private EditText edit_group_2;
    private EditText edit_group_3;
    private EditText edit_group_4;

    public CodeDialog(Context context, int theme) {
        super(context, theme);

    }

    public CodeDialog(Context context) {
        super(context, R.style.custom_dialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_code_view);
        setCanceledOnTouchOutside(false);
        mResources = mContext.getResources();
        dialog_code_rotate = (LinearLayout) findViewById(R.id.dialog_code_rotate);
        dialog_view_code = (ImageView) findViewById(R.id.dialog_view_code);
        code_ok_btn = (TextView) findViewById(R.id.dialog_view_code_ok_btn);
        text_rotate_repeat = (TextView) findViewById(R.id.text_rotate_repeat);
        edit_group_1 = (EditText) findViewById(R.id.edit_group_1);
        edit_group_2 = (EditText) findViewById(R.id.edit_group_2);
        edit_group_3 = (EditText) findViewById(R.id.edit_group_3);
        edit_group_4 = (EditText) findViewById(R.id.edit_group_4);
        code_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOkClick();
            }
        });
        dialog_code_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickRotate();
            }
        });
        edit_group_1.requestFocus();
        edit_group_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    edit_group_2.requestFocus();
                }else{
                    edit_group_1.requestFocus();
                }
            }
        });
        edit_group_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.toString().equals("")){
                    edit_group_3.requestFocus();
                }else{
                    edit_group_1.requestFocus();
                }
            }
        });
        edit_group_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.toString().equals("")){
                    edit_group_4.requestFocus();
                }else{
                    edit_group_2.requestFocus();
                }
            }
        });
        edit_group_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.toString().trim().equals("")){
                    edit_group_4.requestFocus();
                }else{
                    edit_group_3.requestFocus();
                }
            }
        });
    }

    public String getSendCode(){
        String e1=edit_group_1.getText().toString().trim();
        String e2=edit_group_2.getText().toString().trim();
        String e3=edit_group_3.getText().toString().trim();
        String e4=edit_group_4.getText().toString().trim();

        if(e1.equals("")||e2.equals("")||e3.equals("")||e4.equals("")){
            return "";
        }

        return e1+e2+e3+e4;
    }


    public void setRepeat(boolean isrepeat){
        if(isrepeat){
            text_rotate_repeat.setVisibility(View.VISIBLE);
        }else{
            text_rotate_repeat.setVisibility(View.INVISIBLE);
        }
    }

    private void onClickRotate() {
        if (mRotateListener != null){
            mRotateListener.onOnRotateClick();
        }
    }


    public void onCreateCodeImageview(String str){
       Bitmap bt= CodeTools.getInstance().createBitmap(str);
        if(bt==null){
            return;
        }
       dialog_view_code.setImageBitmap(bt);
    }



    private void  onOkClick(){
        if (mListener != null){
            mListener.onTextViewDialogOkClick();
        }
    }

    public void setOnRotateClickListener(onRotateClickListener mRotateListener){
        this.mRotateListener = mRotateListener;
    }

    public interface onRotateClickListener{
        public void onOnRotateClick();
    }
    public void setOnTextViewDialogClickListener(onTextViewDialogClickListener listener){
        this.mListener = listener;
    }

    public interface onTextViewDialogClickListener{
        public void onTextViewDialogOkClick();
    }


}
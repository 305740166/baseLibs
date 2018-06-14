package com.huizhong.baselibs.Tools;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.huizhong.baselibs.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shaochun on 14-3-26.
 */
public class CameraDialog extends Dialog {
    //定义回调事件
    public interface OnCustomDialogListener {
        public void back(String types);
    }

    private Context context;
    private BottomCameraAdapter bottomCameraAdapter;

    private OnCustomDialogListener customDialogListener;

    public CameraDialog(Context context, OnCustomDialogListener customDialogListener) {
        super(context, R.style.dialog);
        this.context = context;
        this.customDialogListener = customDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_dialog);

        setCancelable(true);
        setCanceledOnTouchOutside(true);
        init();


    }

    private void init() {

        bottomCameraAdapter = new BottomCameraAdapter(context, getBottomCameraData());
        ListView listViewMenu = (ListView) CameraDialog.this.findViewById(R.id.listViewMenu);
        listViewMenu.setAdapter(bottomCameraAdapter);

        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String types = ((Map<String, String>) adapterView.getAdapter().getItem(position)).get("types");

                customDialogListener.back(types);
                dismiss();
            }
        });
    }

    private List<Map<String, Object>> getBottomCameraData() {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();

        map = new HashMap<String, Object>();
        map.put("types", "camera");
        map.put("title", "拍照");
        map.put("img", R.drawable.ico_camera);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("types", "photo");
        map.put("title", "从相册选择");
        map.put("img", R.drawable.ico_photo);
        list.add(map);

        return list;
    }

}

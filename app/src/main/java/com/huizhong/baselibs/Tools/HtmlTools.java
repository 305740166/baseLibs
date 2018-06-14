package com.huizhong.baselibs.Tools;

import android.text.TextUtils;

/**
 * Created by wyx on 18/3/14.
 */
public class HtmlTools {
    //转化可读内容
    public static String parseContent(String content) {
        if(!TextUtils.isEmpty(content)){
            content = content.replace("\n","<br>");
        }
        return content;
    }
}

package com.huizhong.baselibs.Tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by shaochun on 14-3-28.
 */

public class ImageViewRound extends ImageView {

    public ImageViewRound(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ImageViewRound(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewRound(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        int w = this.getWidth();
        int h = this.getHeight();

        /**
         * RectF  圆角矩形
         * **/
        clipPath.addRoundRect(new RectF(0, 0, w, h), 10.0f, 10.0f, Path.Direction.CW);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        try{
            canvas.clipPath(clipPath);
        }catch (Exception e){
            e.printStackTrace();
        }

        super.onDraw(canvas);
    }


}

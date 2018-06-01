package com.blog.www.guideview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by binIoter on 16/6/17.
 */
public class SimpleComponent implements Component {
  private LinearLayout mLayoutView=null;
  int layoutview=0;
  private int mDirection= Component.ANCHOR_TOP;
  private int mStyle= Component.FIT_CENTER;
  private int x=0;
  private int y=0;
  private int mtype=0;

  public SimpleComponent(){
  }

  public SimpleComponent(int layoutview){
    this.layoutview=layoutview;
  }

  public SimpleComponent(LinearLayout layview,int type){
    if(type==1){
      mLayoutView=layview;
      mtype=type;
    }
  }

  @Override public View getView(LayoutInflater inflater) {
    LinearLayout ll=null;
    if(mtype==0){

       ll = (LinearLayout) inflater.inflate(layoutview, null);
      ll.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
//        Toast.makeText(view.getContext(), "引导层被点击了", Toast.LENGTH_SHORT).show();

        }
      });
    }else {
      ll=mLayoutView;
    }
    return ll;
  }

  //自定义插图片方向
  public void setAnchor(int direction) {

    this.mDirection= direction;
  }

  @Override public int getAnchor() {
    return this.mDirection;
  }

  //自定义插图适配方式
  public void setFitPosition(int style) {

    this.mStyle= style;
  }

  @Override public int getFitPosition() {
    return this.mStyle;
  }

  //自定义相对偏移X
  public void setXOffset(Activity mActivity, int x) {

    this.x= DataTools.getInstance().dip2px(mActivity,x);
  }

  @Override public int getXOffset() {
    return this.x;
  }

  //自定义相对偏移Y
  public void setYOffset(Activity mActivity, int y) {

    this.y= DataTools.getInstance().dip2px(mActivity,y);
  }
  @Override public int getYOffset() {
    return this.y;
  }



}

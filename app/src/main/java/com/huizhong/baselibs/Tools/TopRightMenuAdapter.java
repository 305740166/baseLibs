package com.huizhong.baselibs.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huizhong.baselibs.R;

import java.util.List;
import java.util.Map;

/**
 * Created by shaochun on 14-3-27.
 */
public class TopRightMenuAdapter extends BaseAdapter {

    private List<Map<String, Object>> mData;
    private LayoutInflater mInflater;

    public TopRightMenuAdapter(Context context, List<Map<String, Object>> menuData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = menuData;
    }

    public final class ViewHolder {
        public ImageView img;
        public TextView title;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mData.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {

            holder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.top_right_menu_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.leftIcon);
            holder.title = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        holder.img.setImageResource((Integer) mData.get(position).get("img"));
        holder.title.setText((String) mData.get(position).get("title"));

        return convertView;
    }

}

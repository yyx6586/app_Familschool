package com.aloogn.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloogn.famil_school.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author : zxl
 * e-mail : loogn.zou@foxmail.com
 * date   : 2020/7/234:31 PM
 * desc   :
 */
public class HomeGridViewAdpater extends BaseAdapter{

    private LayoutInflater layoutInflater;
    private int[] images;
    private String[] text;
    public HomeGridViewAdpater(Context context, int[] images, String[] text){
        this.images = images;
        this.text = text;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = layoutInflater.inflate(R.layout.gridview_item,null);
        CircleImageView gridView_item_circleImageView = (CircleImageView) v.findViewById(R.id.gridView_item_circleImageView);
        TextView tv_gridView_item = (TextView) v.findViewById(R.id.tv_gridView_item);
        gridView_item_circleImageView.setImageResource(images[position]);
        tv_gridView_item.setText(text[position]);
        return v;
    }
}

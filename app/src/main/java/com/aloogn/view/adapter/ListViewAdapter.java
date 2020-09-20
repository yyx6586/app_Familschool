package com.aloogn.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aloogn.famil_school.R;

import java.util.List;

/**
 * author : zxl
 * e-mail : loogn.zou@foxmail.com
 * date   : 2020/9/195:00 PM
 * desc   :
 */
public class ListViewAdapter extends BaseAdapter{

    private final List<List<String>> mlist;
    private final Context mContext;

    public ListViewAdapter(List<List<String>> mlist, Context mContext) {
        this.mlist = mlist;
        this.mContext = mContext;
    }

    /**
     * 数据总条数
     * @return
     */
    @Override
    public int getCount() {
        //如果 mlist 为空，返回 0
        return mlist == null ? 0 : mlist.size();
    }

    /**
     * 获取一条的 item 数据
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    /**
     * 获取某一条的 ID
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 数据与布局是如何对应的
     * @param position   当前 item 的位置
     * @param convertView     复用的 View 的布局
     * @param parent     容器
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //每一条 item 是如何显示的
        convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item_layout, parent, false);

//        TextView textView = null;
//        if(convertView == null){
//            textView = new TextView(mContext);
//        }else {
//            textView = (TextView) convertView;
//        }

        //拿到一条数据
        TextView tv_information = convertView.findViewById(R.id.listView_tv_information);
        TextView tv_time = convertView.findViewById(R.id.listView_tv_time);
        tv_information.setText(mlist.get(position).get(position));
        tv_time.setText(mlist.get(position).get(position + 1));

//        textView.setText(mlist.get(position));
//        textView.setPadding(10,10,10,10);
//        textView.setTextSize(30);

        //将拿到的数据设置到 一个item 上
        return convertView;


//        TextView mTextView;
//        if (convertView == null) {
//            mTextView = new TextView(NoticeSchoolAreledyActivity.this);
//        } else {
//            mTextView = (TextView) convertView;
//        }
//        mTextView.setText("Item " + position);
//        mTextView.setTextSize(20f);
//        mTextView.setGravity(Gravity.CENTER);
//        mTextView.setHeight(60);
//        return mTextView;
    }
}

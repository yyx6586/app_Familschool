package com.aloogn.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.aloogn.famil_school.R;

import java.util.Collections;
import java.util.List;

/**
 * author : zxl
 * e-mail : loogn.zou@foxmail.com
 * date   : 2020/9/109:49 AM
 * desc   :
 */
public class ExpandableLIstViewAdapter extends BaseExpandableListAdapter{

    List<String> groupList;     //一级List
    List<List<String>> childList;     //二级List 注意!这里是List里面套了一个List<String>,实际项目你可以写一个pojo类来管理2层数据

    public ExpandableLIstViewAdapter(List<String> groupList, List<List<String>> childList) {
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {    //返回第一级List长度
        //如果groupList 为空，返回0
        return groupList == null ? 0 : groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) { //返回指定groupPosition的第二级List长度
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {      //返回一级List里的内容
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {     //返回二级List的内容
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {     //返回一级View的id 保证id唯一
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {     //返回二级View的id 保证id唯一
        return groupPosition + childPosition;
    }

    /**
     * 指示在对基础数据进行更改时子ID和组ID是否稳定
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     *  返回一级父View
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandablelistview_partent_item, parent,false);
        ((TextView)convertView).setText((String)getGroup(groupPosition));
        return convertView;
    }

    /**
     *  返回二级子View
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandbalelistview_child_item, parent,false);
        ((TextView)convertView).setText((String)getChild(groupPosition,childPosition));
        return convertView;
    }

    /**
     *  指定位置的子项是否可选
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

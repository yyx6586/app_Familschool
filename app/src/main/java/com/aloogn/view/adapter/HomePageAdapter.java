package com.aloogn.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * author : zxl
 * e-mail : loogn.zou@foxmail.com
 * date   : 2020/7/67:43 PM
 * desc   :
 */
public class HomePageAdapter extends PagerAdapter {

    Context context;
    //放置图片的集合
    List<ImageView> imageViewList;

    public HomePageAdapter(Context context, List<ImageView> imageViewList) {
        this.context = context;
        this.imageViewList = imageViewList;
    }

    @Override
    public int getCount() {
        return imageViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        //判断两次对象是否一致
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //获取加载时， View 对象
        ImageView imageView = imageViewList.get(position);
        //进行加载
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //获取需要销毁的 View
        ImageView imageView = imageViewList.get(position);
        //进行销毁
        container.removeView(imageView);
    }
}

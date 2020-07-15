package com.aloogn.view.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aloogn.famil_school.R;
import com.aloogn.view.adapter.HomePageAdpater;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageTeacherFragment extends Fragment {

    ViewPager home_teacher_viewPager;
    LinearLayout home_teacher_linearLayout;

    //声明数组图片
    int[] imgIds = {R.mipmap.timg,R.mipmap.yuedu,R.mipmap.find};
    //声明 ViewPager 的数据源
    List<ImageView> imageViewList;
    //声明管理指示器小圆点的集合
    List<ImageView> pointList;
    //完成定时装置，实现自动滑动的效果
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                //获取当前 ViewPager 显示的页面
                int currentItem = home_teacher_viewPager.getCurrentItem();
                //判断是否为最后一张，如果是最后一张，则回到第一张，否则显示到最后一张
                if (currentItem == imageViewList.size() - 1) {
                    imageViewList.get(0);
                }else {
                    currentItem ++;
                    home_teacher_viewPager.setCurrentItem(currentItem);
                }
                //形成循环发送，接收信息的效果，在接收信息的同时，也要进行信息的发送
                handler.sendEmptyMessageDelayed(1,5000);
            }

        }
    };

    public HomePageTeacherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page_teacher, container, false);
        initView(view);
        initPager();
        setVpList();
        //延迟 5 秒钟发送一条信息，通知可以切换 ViewPager 的图片了
        handler.sendEmptyMessageDelayed(1,5000);
        return view;

    }

    //设置 ViewPager 显示的页面
    private void initPager(){
        //数据源初始化
        imageViewList = new ArrayList<>();
        pointList = new ArrayList<>();
        for(int i = 0; i < imgIds.length; i ++){
            ImageView iv = new ImageView(getContext());
            iv.setImageResource(imgIds[i]);
            //设置图片放大
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //设置图片 view 的宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置到图形的宽高当中
            iv.setLayoutParams(layoutParams);
            //将图片 view 加载到集合中
            imageViewList.add(iv);
            //创建图片对应的指示器小圆点
            ImageView piv = new ImageView(getContext());
            piv.setImageResource(R.mipmap.point_normal);
            //设置小圆点的大小
            LinearLayout.LayoutParams plp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置小圆点的外边框
            plp.setMargins(20,0,0,0);
            //将小圆点的设置添加到小圆点中
            piv.setLayoutParams(plp);
            //将小圆点添加到布局中
            home_teacher_linearLayout.addView(piv);
            //为了便于管理，将小圆点添加到统一管理的集合中
            pointList.add(piv);
        }
        //默认第一个小圆点是获取焦点的状态
        pointList.get(0).setImageResource(R.mipmap.point_focus);
        //设置适配器
        HomePageAdpater homePageAdpater = new HomePageAdpater(getContext(),imageViewList);
        //设置给 View
        home_teacher_viewPager.setAdapter(homePageAdpater);
    }

    //设置 ViewPager 的监听函数
    private void setVpList(){
        home_teacher_viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < pointList.size(); i ++){
                    pointList.get(i).setImageResource(R.mipmap.point_normal);
                }
                pointList.get(position).setImageResource(R.mipmap.point_focus);
            }
        });

    }

    //初始化控件
    private void initView(View view){
        home_teacher_viewPager = view.findViewById(R.id.home_teacher_viewPager);
        home_teacher_linearLayout = view.findViewById(R.id.home_teacher_linearLayout);
    }

}

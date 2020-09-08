package com.aloogn.view.fragment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.aloogn.famil_school.R;
import com.aloogn.view.adapter.HomeGridViewAdpater;
import com.aloogn.view.adapter.HomePageAdpater;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageTeacherFragment extends Fragment {

    private Spinner spinner;
    private GridView homePageTeacher_gridView;
    ViewPager home_teacher_viewPager;
    LinearLayout home_teacher_linearLayout;

    private int[] images = {R.mipmap.tongzhi, R.mipmap.zuoye, R.mipmap.chengji, R.mipmap.jilu, R.mipmap.tongxunlu,
                            R.mipmap.banji, R.mipmap.tianjia};

    private String [] text = {"通知", "作业", "成绩", "成长记录", "通讯录", "创建班级", "添加成员"};

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
                handler.sendEmptyMessageDelayed(1,4000);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homePageTeacher_gridView = getActivity().findViewById(R.id.homePageTeacher_gridView);
        homePageTeacher_gridView.setAdapter(new HomeGridViewAdpater(getContext(), images, text));
        homePageTeacher_gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        spinner = (Spinner) getActivity().findViewById(R.id.homePageTeacher_spinner);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(),R.layout.spinner_item,
                new String[] {"语文", "数学", "英语", "科学"});
        spinner.setAdapter(adapter);
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
        homePageTeacher_gridView = view.findViewById(R.id.homePageTeacher_gridView);
        home_teacher_viewPager = view.findViewById(R.id.home_teacher_viewPager);
        home_teacher_linearLayout = view.findViewById(R.id.home_teacher_linearLayout);
    }

}

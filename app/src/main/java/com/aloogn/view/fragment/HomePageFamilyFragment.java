package com.aloogn.view.fragment;


import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aloogn.famil_school.R;
import com.aloogn.view.AchievementFamilyActivity;
import com.aloogn.view.AddressBookActivity;
import com.aloogn.view.GrowRecordFamilyActivity;
import com.aloogn.view.HomeworkFamilyActivity;
import com.aloogn.view.NoticeFamilyActivity;
import com.aloogn.view.PersonalInformationFamilyActivity;
import com.aloogn.view.adapter.HomeGridViewAdpater;
import com.aloogn.view.adapter.HomePageAdpater;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFamilyFragment extends Fragment {

    private GridView homePageFamily_gridView;
    private ViewPager home_family_viewPager;
    private LinearLayout home_family_linearLayout;

    private int[] images = {R.mipmap.tongzhi, R.mipmap.zuoye, R.mipmap.chengji,R.mipmap.jilu,
                            R.mipmap.tongxunlu};

    private String [] text = {"通知", "作业", "成绩", "成长记录", "通讯录"};

    //声明数组图片
    int[] imgIds = {R.mipmap.timg, R.mipmap.yuedu, R.mipmap.find};
    //声明 ViewPager 的数据源
    List<ImageView> imageViewList;
    //声明管理指示器小圆点的集合
    List<ImageView> pointList;
    //完成定时装置，实现自动滑动的效果
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                //获取当前 ViewPager 显示的页面
                int currentItem = home_family_viewPager.getCurrentItem();
                //判断是否为最后一张，如果是最后一张，则回到第一张，否则显示到最后一张
                if (currentItem == imageViewList.size() - 1) {
                    imageViewList.get(0);
                }else {
                    currentItem ++;
                    home_family_viewPager.setCurrentItem(currentItem);
                }
                //形成循环发送，接收信息的效果，在接收信息的同时，也要进行信息的发送
                handler.sendEmptyMessageDelayed(1,4000);
            }

        }
    };

    public HomePageFamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page_family, container, false);
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
        homePageFamily_gridView = getActivity().findViewById(R.id.homePageFamily_gridView);
        homePageFamily_gridView.setAdapter(new HomeGridViewAdpater(getContext(), images, text));
        homePageFamily_gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        homePageFamily_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(getActivity(), NoticeFamilyActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), HomeworkFamilyActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), AchievementFamilyActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), GrowRecordFamilyActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(), AddressBookActivity.class));
                        break;
                }
            }
        });
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
            home_family_linearLayout.addView(piv);
            //为了便于管理，将小圆点添加到统一管理的集合中
            pointList.add(piv);
        }
        //默认第一个小圆点是获取焦点的状态
        pointList.get(0).setImageResource(R.mipmap.point_focus);
        //设置适配器
        HomePageAdpater homePageAdpater = new HomePageAdpater(getContext(),imageViewList);
        //设置给 View
        home_family_viewPager.setAdapter(homePageAdpater);
    }

    //设置 ViewPager 的监听函数
    private void setVpList(){
        home_family_viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

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
        homePageFamily_gridView = view.findViewById(R.id.homePageFamily_gridView);
        home_family_viewPager = view.findViewById(R.id.home_family_viewPager);
        home_family_linearLayout = view.findViewById(R.id.home_family_linearLayout);
    }

}

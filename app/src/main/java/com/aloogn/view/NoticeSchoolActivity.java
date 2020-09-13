package com.aloogn.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aloogn.famil_school.R;
import com.aloogn.view.common.TitleLayout;
import com.aloogn.view.fragment.SchoolNoticeAlreadyFragment;
import com.aloogn.view.fragment.SchoolNoticeReleaseFragment;

public class NoticeSchoolActivity extends AppCompatActivity implements View.OnClickListener{

    private TitleLayout mTitleLayout;
    private TextView mTitle, title_text_alreadyRelease;

    //声明 Fragment 对象
    Fragment schoolNoticeReleaseFragment, schoolNoticeAleadyReleaseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_school);

        mTitleLayout = (TitleLayout)findViewById(R.id.ManualBinding_title);
        mTitle = (TextView)mTitleLayout.findViewById(R.id.title_text);
        title_text_alreadyRelease = (TextView)mTitleLayout.findViewById(R.id.title_text_alreadyRelease);

        mTitle.setText("通知信息");
        title_text_alreadyRelease.setVisibility(View.VISIBLE);

        title_text_alreadyRelease.setOnClickListener(this);

        //初始化 Fragment 对象
        schoolNoticeReleaseFragment = new SchoolNoticeReleaseFragment();
        schoolNoticeAleadyReleaseFragment = new SchoolNoticeAlreadyFragment();

        //将 Fragment 动态加载到布局中
        addFragmentPage();
    }

    //将 Fragment 一起动态加载到主页面中，有用的显示，暂时无用的隐藏
    private void addFragmentPage() {
        //创建管理对象
        FragmentManager manager = getSupportFragmentManager();
        //创建事务处理对象
        FragmentTransaction transaction = manager.beginTransaction();
        //将 Fragment 加载到布局中
        transaction.add(R.id.main_linearLayout,schoolNoticeReleaseFragment);
        transaction.add(R.id.main_linearLayout,schoolNoticeAleadyReleaseFragment);
        //隐藏后面的 Fragment
        transaction.hide(schoolNoticeAleadyReleaseFragment);
        //提交改变后的事物
        transaction.commit();
    }


    @Override
    public void onClick(View v) {

        FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();

        switch (v.getId()){
            case R.id.title_text_alreadyRelease:
                transaction.show(schoolNoticeAleadyReleaseFragment);
                transaction.hide(schoolNoticeReleaseFragment);
                break;
        }
        transaction.commit();
    }
}

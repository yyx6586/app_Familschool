package com.aloogn.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.aloogn.famil_school.R;
import com.aloogn.view.fragment.FindTeacherFragment;
import com.aloogn.view.fragment.HomePageTeacherFragment;
import com.aloogn.view.fragment.MyFragment;

public class TeacherMainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    RadioGroup main_radioGroup;

    //声明 Fragment 对象
    Fragment homePageTeacherFragment, findTeacherFragment, myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);


        main_radioGroup = (RadioGroup) findViewById(R.id.main_radioGroup);

        main_radioGroup.setOnCheckedChangeListener(this);

        //初始化 Fragment 对象
        homePageTeacherFragment = new HomePageTeacherFragment();
        findTeacherFragment = new FindTeacherFragment();
        myFragment = new MyFragment();

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
        transaction.add(R.id.main_linearLayout,homePageTeacherFragment);
        transaction.add(R.id.main_linearLayout,findTeacherFragment);
        transaction.add(R.id.main_linearLayout,myFragment);
        //隐藏后面两个 Fragment
        transaction.hide(findTeacherFragment);
        transaction.hide(myFragment);
        //提交改变后的事物
        transaction.commit();
    }

    //返回时修改此Activity为启动窗口
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, final int checkedId) {
        FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();

        switch (checkedId){
            case R.id.main_rb_homePage:
                transaction.show(homePageTeacherFragment);
                transaction.hide(findTeacherFragment);
                transaction.hide(myFragment);
                break;
            case R.id.main_rb_find:
                transaction.show(findTeacherFragment);
                transaction.hide(homePageTeacherFragment);
                transaction.hide(myFragment);
                break;
            case R.id.main_rb_my:
                transaction.show(myFragment);
                transaction.hide(homePageTeacherFragment);
                transaction.hide(findTeacherFragment);
                break;
        }
        transaction.commit();
    }
}

package com.aloogn.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aloogn.MyApplication;
import com.aloogn.famil_school.R;
import com.aloogn.util.MyToastUtil;
import com.aloogn.util.OkHttpUtil;
import com.aloogn.util.StringUtil;
import com.aloogn.view.common.TitleLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NoticeSchoolActivity extends AppCompatActivity implements View.OnClickListener{

    private TitleLayout mTitleLayout;
    private TextView mTitle, title_text_alreadyRelease;
    private EditText schoolNoticeRelease;
    private Button schoolNoticeRelease_btn;
    private String token;
    private String information, account;

//    //声明 Fragment 对象
//    Fragment schoolNoticeReleaseFragment, schoolNoticeAleadyFragment;


    protected class MyTask extends AsyncTask<String, Integer, String> {

        private String account, information;

        public MyTask(String account, String information) {
            this.account = account;
            this.information = information;
        }

        @Override
        protected String doInBackground(String... strings) {

            token = (String) ((MyApplication)getApplication()).get("token", null);
            account = (String) (((MyApplication) getApplication()).get("account",null));

            Map<String,Object> map = new HashMap<>();
            map.put("account",account);
            map.put("information", information);

            Call call = OkHttpUtil.getInstance().post("noticeInformation/schoolNoticeInformationRelease", map, token);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        int code = jsonObject.optInt("code");
                        String msg = jsonObject.optString("msg");

                        if(code == 1){
                            Looper.prepare();
                            MyToastUtil.showLongToast(NoticeSchoolActivity.this,msg);
                            Looper.loop();
                        }else {
                            Looper.prepare();
                            MyToastUtil.showLongToast(NoticeSchoolActivity.this,msg);
                            Looper.loop();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_school);

        mTitleLayout = (TitleLayout)findViewById(R.id.ManualBinding_title);
        mTitle = (TextView)mTitleLayout.findViewById(R.id.title_text);
        title_text_alreadyRelease = (TextView)mTitleLayout.findViewById(R.id.title_text_alreadyRelease);
        schoolNoticeRelease = (EditText) findViewById(R.id.schoolNoticeRelease);
        schoolNoticeRelease_btn = (Button) findViewById(R.id.schoolNoticeRelease_btn);

        mTitle.setText("通知信息");
        title_text_alreadyRelease.setVisibility(View.VISIBLE);

        title_text_alreadyRelease.setOnClickListener(this);
        schoolNoticeRelease_btn.setOnClickListener(this);

//        //初始化 Fragment 对象
//        schoolNoticeReleaseFragment = new SchoolNoticeReleaseFragment();
//        schoolNoticeAleadyFragment = new SchoolNoticeAlreadyFragment();
//
//        //将 Fragment 动态加载到布局中
//        addFragmentPage();
    }

//    //将 Fragment 一起动态加载到主页面中，有用的显示，暂时无用的隐藏
//    private void addFragmentPage() {
//        //创建管理对象
//        FragmentManager manager = getSupportFragmentManager();
//        //创建事务处理对象
//        FragmentTransaction transaction = manager.beginTransaction();
//        //将 Fragment 加载到布局中
//        transaction.add(R.id.noticeSchool_ll,schoolNoticeReleaseFragment);
//        transaction.add(R.id.noticeSchool_ll,schoolNoticeAleadyFragment);
//        //隐藏后面的 Fragment
//        transaction.hide(schoolNoticeAleadyFragment);
//        //提交改变后的事物
//        transaction.commit();
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.schoolNoticeRelease_btn:
                information = schoolNoticeRelease.getText().toString();

                if(StringUtil.isNullOrEmpty(information)){
                    MyToastUtil.showLongToast(NoticeSchoolActivity.this,"发布的信息不能为空");
                    return;
                }

                MyTask myTask = new MyTask(account,information);
                myTask.execute();
                break;

            case R.id.title_text_alreadyRelease:
                startActivity(new Intent(NoticeSchoolActivity.this, NoticeSchoolAreledyActivity.class));
                break;
        }
    }

//        FragmentManager manager = getSupportFragmentManager();
//        final FragmentTransaction transaction = manager.beginTransaction();
//
//        switch (v.getId()){
//            case R.id.title_text_alreadyRelease:
//                transaction.show(schoolNoticeAleadyFragment);
//                transaction.hide(schoolNoticeReleaseFragment);
//                break;
//        }
//        transaction.commit();
//    }
}

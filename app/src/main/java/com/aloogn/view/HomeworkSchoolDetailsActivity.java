package com.aloogn.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aloogn.MyApplication;
import com.aloogn.famil_school.R;
import com.aloogn.util.MyToastUtil;
import com.aloogn.util.OkHttpUtil;
import com.aloogn.view.common.TitleLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeworkSchoolDetailsActivity extends AppCompatActivity {

    private TitleLayout mTitleLayout;
    private TextView mTitle, title_text_alreadyRelease;
    private TextView homeworkSchoolDetails_tv_time;
    private TextView homeworkSchoolDetails_tv_homework;
    private String token;
    private String account;
    private String grade_id = "22";
    private String homework;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_school_details);

        //控件初始化
        init();

        mTitle.setText("作业详情");

        title_text_alreadyRelease.setVisibility(View.VISIBLE);
        title_text_alreadyRelease.setText("删除");

        //获取当前用户账号
        account = (String) ((MyApplication)getApplication()).get("account", null);

        //获取 token
        token = (String) ((MyApplication)getApplication()).get("token", null);

        //获取作业信息
        Intent intent = getIntent();
        String values = intent.getStringExtra("values");
        homework = values.substring(1,values.length() - 8);
        String time = values.substring(values.length() - 6, values.length() - 1);

        homeworkSchoolDetails_tv_homework.setText(homework);
        homeworkSchoolDetails_tv_time.setText(time);

        //删除设置点击事件
        title_text_alreadyRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask myTask = new MyTask(account,grade_id,homework);
                myTask.execute();
            }
        });
    }

    protected class MyTask extends AsyncTask<String, Integer, String>{
        private String account;
        private String grade_id;
        private String homework;

        public MyTask(String account, String grade_id, String homework) {
            this.account = account;
            this.grade_id = grade_id;
            this.homework = homework;
        }

        @Override
        protected String doInBackground(String... strings) {

            Map<String, Object> map = new HashMap<>();
            map.put("account", account);
            map.put("grade_id", grade_id);
            map.put("homework", homework);

            Call call = OkHttpUtil.getInstance().post("userHomework/homeworkSchoolDetails",map,token);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        String msg = jsonObject.optString("msg");

                        Looper.prepare();
                        MyToastUtil.showLongToast(HomeworkSchoolDetailsActivity.this, msg);
                        Looper.loop();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }
    }

    public void init(){
        mTitleLayout = (TitleLayout) findViewById(R.id.ManualBinding_title);
        mTitle = (TextView) findViewById(R.id.title_text);
        title_text_alreadyRelease = (TextView)mTitleLayout.findViewById(R.id.title_text_alreadyRelease);
        homeworkSchoolDetails_tv_time = (TextView) findViewById(R.id.homeworkSchoolDetails_tv_time);
        homeworkSchoolDetails_tv_homework = (TextView) findViewById(R.id.homeworkSchoolDetails_tv_homework);
    }
}

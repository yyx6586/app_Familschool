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
import com.aloogn.util.JSONArryUtil;
import com.aloogn.util.MyToastUtil;
import com.aloogn.util.OkHttpUtil;
import com.aloogn.view.common.TitleLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeworkSchoolActivity extends AppCompatActivity {

    private TitleLayout mTitleLayout;
    private TextView mTitle, title_text_alreadyRelease;
    private EditText homeworkSchool_subjectName;
    private EditText homeworkSchool_homework;
    private Button homeworkSchoolRelease_btn;
    private String grade_id = "22";
    private String subject_name;
    private String homework;
    private String account;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_school);

        //初始化控件
        init();

        mTitle.setText("发布作业");
        title_text_alreadyRelease.setVisibility(View.VISIBLE);

        //获取科目名称和作业信息
        subject_name = homeworkSchool_subjectName.getText().toString();
        homework = homeworkSchool_homework.getText().toString();

        //获取当前用户账号
        account = (String) ((MyApplication)getApplication()).get("account", null);

        //获取 token
        token = (String) ((MyApplication)getApplication()).get("token", null);

        //给按钮设置点击事件
        homeworkSchoolRelease_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask myTask = new MyTask(account,grade_id,homework,subject_name);
                myTask.execute();
            }
        });

        //给 "已发布" 文档设置点击事件
        title_text_alreadyRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeworkSchoolActivity.this, HomeworkSchoolAreledyActivity.class));

            }
        });

    }

    protected class MyTask extends AsyncTask<String, Integer, String>{
        private String account;
        private String grade_id;
        private String homework;
        private String subject_name;

        public MyTask(String account, String grade_id, String homework, String subject_name) {
            this.account = account;
            this.grade_id = grade_id;
            this.homework = homework;
            this.subject_name = subject_name;
        }

        @Override
        protected String doInBackground(String... strings) {

            Map<String, Object> map = new HashMap<>();
            map.put("account", account);
            map.put("grade_id", grade_id);
            map.put("homework", homework);
            map.put("subject_name", subject_name);

            Call call = OkHttpUtil.getInstance().post("userHomework/homeworkSchoolRelease",map,token);
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
                        MyToastUtil.showLongToast(HomeworkSchoolActivity.this, msg);
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
        mTitleLayout = (TitleLayout)findViewById(R.id.ManualBinding_title);
        mTitle = (TextView)mTitleLayout.findViewById(R.id.title_text);
        title_text_alreadyRelease = (TextView)mTitleLayout.findViewById(R.id.title_text_alreadyRelease);
        homeworkSchool_homework = (EditText) findViewById(R.id.homeworkSchool_homework);
        homeworkSchool_subjectName = (EditText) findViewById(R.id.homeworkSchool_subjectName);
        homeworkSchoolRelease_btn = (Button) findViewById(R.id.homeworkSchoolRelease_btn);
    }
}

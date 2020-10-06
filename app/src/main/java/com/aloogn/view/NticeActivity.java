package com.aloogn.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aloogn.MyApplication;
import com.aloogn.famil_school.R;
import com.aloogn.util.DateUtil;
import com.aloogn.util.JSONArryUtil;
import com.aloogn.util.MyToastUtil;
import com.aloogn.util.OkHttpUtil;
import com.aloogn.view.common.TitleLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NticeActivity extends AppCompatActivity {

    private TextView ntice_tv_information;
    private TextView ntice_tv_time;
    private TitleLayout mTitleLayout;
    private TextView mTitle, title_text_alreadyRelease;
    private String grade_id = "22";
    private String account;
    private String token;
    private String information;

    protected class MyTask extends AsyncTask<String, Integer, String>{

        private String account;
        private String grade_id;
        private String information;

        public MyTask(String account, String grade_id, String information) {
            this.account = account;
            this.grade_id = grade_id;
            this.information = information;
        }

        @Override
        protected String doInBackground(String... strings) {

            Map<String,Object> map = new HashMap<>();
            map.put("account", account);
            map.put("grade_id",grade_id);
            map.put("information",information);

            Call call = OkHttpUtil.getInstance().post("noticeInformation/noticeSchool",map,token);
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
                        MyToastUtil.showLongToast(NticeActivity.this,msg);
                        Looper.loop();

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
        setContentView(R.layout.activity_ntice);

        ntice_tv_information = (TextView) findViewById(R.id.ntice_tv_information);
        ntice_tv_time = (TextView) findViewById(R.id.ntice_tv_time);
        mTitleLayout = (TitleLayout) findViewById(R.id.ManualBinding_title);
        mTitle = (TextView) findViewById(R.id.title_text);
        title_text_alreadyRelease = (TextView)mTitleLayout.findViewById(R.id.title_text_alreadyRelease);

        mTitle.setText("信息详情");
        title_text_alreadyRelease.setText("删除");
        title_text_alreadyRelease.setVisibility(View.VISIBLE);


        Intent intent = getIntent();
        String values = intent.getStringExtra("values");
        information = values.substring(1,values.length() - 8);
        String time = values.substring(values.length() - 6, values.length() - 1);

        ntice_tv_information.setText(information);
        ntice_tv_time.setText(time);

        account = (String) ((MyApplication)getApplication()).get("account",null);
        token = (String) ((MyApplication)getApplication()).get("token",null);

        title_text_alreadyRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask myTask = new MyTask(account,grade_id,information);
                myTask.execute();
            }
        });

    }
}

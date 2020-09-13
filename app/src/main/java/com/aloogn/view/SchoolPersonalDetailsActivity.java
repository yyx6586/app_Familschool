package com.aloogn.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.aloogn.MyApplication;
import com.aloogn.famil_school.R;
import com.aloogn.util.OkHttpUtil;
import com.aloogn.util.StringUtil;
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

public class SchoolPersonalDetailsActivity extends AppCompatActivity {

    private TitleLayout mTitleLayout;
    private TextView mTitle;
    private TextView schoolPersonalDetails_tv_teacherNumber, schoolPersonalDetails_tv_teacherName, schoolPersonalDetails_tv_teacherSex,
            schoolPersonalDetails_tv_teacherSubiect, schoolPersonalDetails_tv_teacherPhone, schoolPersonalDetails_tv_teacherQQ,
            schoolPersonalDetails_tv_teacherWechat, schoolPersonalDetails_tv_teacherEmail;

    protected class MyTask extends AsyncTask<String, Integer, String>{

        private String account;

        public MyTask(String account) {
            this.account = account;
        }

        @Override
        protected String doInBackground(String... strings) {
            String token = (String) ((MyApplication)getApplication()).get("token",null);

            Map<String,Object> map = new HashMap<>();
            map.put("account", account);

            Call call = OkHttpUtil.getInstance().post("userSubject/schoolPersonalDetails",map,token);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        JSONArray data = jsonObject.optJSONArray("data");

                        if(data.length() > 0 ){
                            for(int i = 0; i < data.length(); i ++){
                                JSONObject obj = data.getJSONObject(i);
                                String id = obj.optString("id");
                                String name = obj.optString("name");
                                String sex = obj.optString("sex");
                                String phone = obj.optString("phone");
                                String QQ = obj.optString("QQ");
                                String wechat = obj.optString("wechat");
                                String email = obj.optString("email");
                                String subject_name = obj.optString("subject_name");

                                if(!StringUtil.isNullOrEmptyOrEmptyNull(id)){
                                    schoolPersonalDetails_tv_teacherNumber.setText(id);
                                }

                                if(!StringUtil.isNullOrEmptyOrEmptyNull(name)){
                                    schoolPersonalDetails_tv_teacherName.setText(name);
                                }

                                if(!StringUtil.isNullOrEmptyOrEmptyNull(sex)){
                                    schoolPersonalDetails_tv_teacherSex.setText(sex);
                                }

                                if(!StringUtil.isNullOrEmptyOrEmptyNull(phone)){
                                    schoolPersonalDetails_tv_teacherPhone.setText(phone);
                                }

                                if(!StringUtil.isNullOrEmptyOrEmptyNull(QQ)){
                                    schoolPersonalDetails_tv_teacherQQ.setText(QQ);
                                }

                                if(!StringUtil.isNullOrEmptyOrEmptyNull(wechat)){
                                    schoolPersonalDetails_tv_teacherWechat.setText(wechat);
                                }

                                if(!StringUtil.isNullOrEmptyOrEmptyNull(email)){
                                    schoolPersonalDetails_tv_teacherEmail.setText(email);
                                }

                                if(!StringUtil.isNullOrEmptyOrEmptyNull(subject_name)){
                                    schoolPersonalDetails_tv_teacherSubiect.setText(subject_name);
                                }
                            }
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
        setContentView(R.layout.activity_school_personal_details);

        init();

        mTitle.setText("详细信息");

        Intent intent = getIntent();
        String str = intent.getStringExtra("values");
        String account = str.substring(str.length() - 6, str.length() - 2);

        MyTask myTask = new MyTask(account);
        myTask.execute();
    }

    public void init(){
        mTitleLayout = (TitleLayout)findViewById(R.id.ManualBinding_title);
        mTitle = (TextView)mTitleLayout.findViewById(R.id.title_text);
        schoolPersonalDetails_tv_teacherNumber = (TextView) findViewById(R.id.schoolPersonalDetails_tv_teacherNumber);
        schoolPersonalDetails_tv_teacherName = (TextView) findViewById(R.id.schoolPersonalDetails_tv_teacherName);
        schoolPersonalDetails_tv_teacherSex = (TextView) findViewById(R.id.schoolPersonalDetails_tv_teacherSex);
        schoolPersonalDetails_tv_teacherSubiect = (TextView) findViewById(R.id.schoolPersonalDetails_tv_teacherSubiect);
        schoolPersonalDetails_tv_teacherPhone = (TextView) findViewById(R.id.schoolPersonalDetails_tv_teacherPhone);
        schoolPersonalDetails_tv_teacherQQ = (TextView) findViewById(R.id.schoolPersonalDetails_tv_teacherQQ);
        schoolPersonalDetails_tv_teacherWechat = (TextView) findViewById(R.id.schoolPersonalDetails_tv_teacherWechat);
        schoolPersonalDetails_tv_teacherEmail = (TextView) findViewById(R.id.schoolPersonalDetails_tv_teacherEmail);
    }
}

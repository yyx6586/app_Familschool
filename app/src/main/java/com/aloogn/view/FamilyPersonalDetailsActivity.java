package com.aloogn.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.aloogn.MyApplication;
import com.aloogn.famil_school.R;
import com.aloogn.util.MyToastUtil;
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

public class FamilyPersonalDetailsActivity extends AppCompatActivity {

    private TitleLayout mTitleLayout;
    private TextView mTitle;
    private TextView familyPersonalDetails_tv_studentNumber, familyPersonalDetails_tv_studentName, familyPersonalDetails_tv_studentSex,
            familyPersonalDetails_tv_parentName, familyPersonalDetails_tv_parentPhone, familyPersonalDetails_tv_parentQQ,
            familyPersonalDetails_tv_parentWechat, familyPersonalDetails_tv_parentEmail;
    private String data;

    protected class MyTask extends AsyncTask<String, Integer, String>{

        private String account;

        public MyTask(String account) {
            this.account = account;
        }

        @Override
        protected String doInBackground(String... strings) {
            String token = (String) ((MyApplication)getApplication()).get("token",null);

            Map<String, Object> map = new HashMap<>();
            map.put("account",account);

            Call call = OkHttpUtil.getInstance().post("user/familyPersonalDetails",map,token);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        data = jsonObject.optString("data");

                        JSONObject obj = new JSONObject(data);

                        String id = obj.optString("id");
                        String name = obj.optString("name");
                        int role = obj.optInt("role");
                        String sex = obj.getString("sex");
                        String parent_name = obj.optString("parent_name");
                        String parent_phone = obj.optString("parent_phone");
                        String parent_QQ = obj.optString("parent_QQ");
                        String parent_wechat = obj.optString("parent_wechat");
                        String email = obj.optString("email");

                        if(!StringUtil.isNullOrEmptyOrEmptyNull(id)){
                            familyPersonalDetails_tv_studentNumber.setText(id);
                        }

                        if(!StringUtil.isNullOrEmptyOrEmptyNull(name)){
                            familyPersonalDetails_tv_studentName.setText(name);
                        }

                        if(!StringUtil.isNullOrEmptyOrEmptyNull(sex)){
                            familyPersonalDetails_tv_studentSex.setText(sex);
                        }

                        if(!StringUtil.isNullOrEmptyOrEmptyNull(parent_name)){
                            familyPersonalDetails_tv_parentName.setText(parent_name);
                        }

                        if(!StringUtil.isNullOrEmptyOrEmptyNull(parent_phone)){
                            familyPersonalDetails_tv_parentPhone.setText(parent_phone);
                        }

                        if(!StringUtil.isNullOrEmptyOrEmptyNull(parent_QQ)){
                            familyPersonalDetails_tv_parentQQ.setText(parent_QQ);
                        }

                        if(!StringUtil.isNullOrEmptyOrEmptyNull(parent_wechat)){
                            familyPersonalDetails_tv_parentWechat.setText(parent_wechat);
                        }

                        if(!StringUtil.isNullOrEmptyOrEmptyNull(email)){
                            familyPersonalDetails_tv_parentEmail.setText(email);
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
        setContentView(R.layout.activity_family_personal_details);

        init();

        mTitle.setText("详细信息");

        Intent intent = getIntent();
        String str = intent.getStringExtra("value");
        String account = str.substring(str.length() - 6, str.length() - 2);

        MyTask myTask = new MyTask(account);
        myTask.execute();

//        try{
//            if(data.length() > 0){
//                JSONObject obj = new JSONObject(data);
//                String id = obj.optString("id");
//                String name = obj.optString("name");
//                int role = obj.optInt("role");
//                String sex = obj.getString("sex");
//                String parent_name = obj.optString("parent_name");
//                String parent_phone = obj.optString("parent_phone");
//
//                familyPersonalDetails_tv_studentNumber.setText(id);
//                familyPersonalDetails_tv_studentName.setText(name);
//                familyPersonalDetails_tv_studentSex.setText(sex);
//                familyPersonalDetails_tv_parentName.setText(parent_name);
//                familyPersonalDetails_tv_parentPhone.setText(parent_phone);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    public void init(){
        mTitleLayout = (TitleLayout)findViewById(R.id.ManualBinding_title);
        mTitle = (TextView)mTitleLayout.findViewById(R.id.title_text);
        familyPersonalDetails_tv_studentNumber = (TextView) findViewById(R.id.familyPersonalDetails_tv_studentNumber);
        familyPersonalDetails_tv_studentName = (TextView) findViewById(R.id.familyPersonalDetails_tv_studentName);
        familyPersonalDetails_tv_studentSex = (TextView) findViewById(R.id.familyPersonalDetails_tv_studentSex);
        familyPersonalDetails_tv_parentName = (TextView) findViewById(R.id.familyPersonalDetails_tv_parentName);
        familyPersonalDetails_tv_parentPhone = (TextView) findViewById(R.id.familyPersonalDetails_tv_parentPhone);
        familyPersonalDetails_tv_parentQQ = (TextView) findViewById(R.id.familyPersonalDetails_tv_parentQQ);
        familyPersonalDetails_tv_parentWechat = (TextView) findViewById(R.id.familyPersonalDetails_tv_parentWechat);
        familyPersonalDetails_tv_parentEmail = (TextView) findViewById(R.id.familyPersonalDetails_tv_parentEmail);
    }
}

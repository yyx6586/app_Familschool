package com.aloogn.view;

import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class PersonalInformationSchoolActivity extends AppCompatActivity implements View.OnClickListener {

    private TitleLayout mTitleLayout;
    private TextView mTitle;
    private EditText schoolPersonalInformation_et_Name, schoolPersonalInformation_et_Sex, schoolPersonalInformation_et_phone,
            schoolPersonalInformation_et_QQ, schoolPersonalInformation_et_wechat, schoolPersonalInformation_et_email;
    private TextView schoolPersonalInformation_tv_jobNumber ;
    private Button schoolPersonalInformation_btn_save;

    protected class MyTask extends AsyncTask<String, Integer, String>{

        private String account, name, sex, phone, QQ, wechat, email;

        public MyTask(String account, String name, String sex, String phone, String QQ, String wechat, String email) {
            this.account = account;
            this.name = name;
            this.sex = sex;
            this.phone = phone;
            this.QQ = QQ;
            this.wechat = wechat;
            this.email = email;
        }

        @Override
        protected String doInBackground(String... strings) {

            String token = (String) ((MyApplication)getApplication()).get("token",null);

            Map<String,Object> map = new HashMap<>();
            map.put("account",account);
            map.put("name",name);
            map.put("sex",sex);
            map.put("phone",phone);
            map.put("QQ",QQ);
            map.put("wechat",wechat);
            map.put("email",email);

            Call call = OkHttpUtil.getInstance().post("user/personalInformationSchool",map,token);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().toString();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        String msg = jsonObject.optString("msg");

                        Looper.prepare();
                        MyToastUtil.showLongToast(PersonalInformationSchoolActivity.this,msg);
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
        setContentView(R.layout.activity_personal_information_school);

        init();

        mTitle.setText("个人信息");

        schoolPersonalInformation_btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.schoolPersonalInformation_btn_save:
                String account = schoolPersonalInformation_tv_jobNumber.getText().toString();
                String name = schoolPersonalInformation_et_Name.getText().toString();
                String sex = schoolPersonalInformation_et_Sex.getText().toString();
                String phone = schoolPersonalInformation_et_phone.getText().toString();
                String QQ = schoolPersonalInformation_et_QQ.getText().toString();
                String wechat = schoolPersonalInformation_et_wechat.getText().toString();
                String email = schoolPersonalInformation_et_email.getText().toString();

                if(StringUtil.isNullOrEmpty(name)){
                    MyToastUtil.showLongToast(PersonalInformationSchoolActivity.this,"姓名不能为空");
                    return;
                }

                if(StringUtil.isNullOrEmpty(sex)){
                    MyToastUtil.showLongToast(PersonalInformationSchoolActivity.this,"性别不能为空");
                    return;
                }

                if(StringUtil.isNullOrEmpty(phone)){
                    MyToastUtil.showLongToast(PersonalInformationSchoolActivity.this,"手机号不能为空");
                    return;
                }

                if(!StringUtil.isPhone(phone)){
                    MyToastUtil.showLongToast(PersonalInformationSchoolActivity.this,"手机号码格式不正确，请重新输入");
                    return;
                }

                MyTask myTask = new MyTask(account,name,sex,phone,QQ,wechat,email);
                myTask.execute();
                break;
        }

    }

    public void init(){
        mTitleLayout = (TitleLayout)findViewById(R.id.ManualBinding_title);
        mTitle = (TextView)mTitleLayout.findViewById(R.id.title_text);
        schoolPersonalInformation_tv_jobNumber = (TextView) findViewById(R.id.schoolPersonalInformation_tv_jobNumber);
        schoolPersonalInformation_et_Name = (EditText) findViewById(R.id.schoolPersonalInformation_et_Name);
        schoolPersonalInformation_et_Sex = (EditText) findViewById(R.id.schoolPersonalInformation_et_Sex);
        schoolPersonalInformation_et_phone = (EditText) findViewById(R.id.schoolPersonalInformation_et_phone);
        schoolPersonalInformation_et_QQ = (EditText) findViewById(R.id.schoolPersonalInformation_et_QQ);
        schoolPersonalInformation_et_wechat = (EditText) findViewById(R.id.schoolPersonalInformation_et_wechat);
        schoolPersonalInformation_et_email = (EditText) findViewById(R.id.schoolPersonalInformation_et_email);
        schoolPersonalInformation_btn_save = (Button) findViewById(R.id.schoolPersonalInformation_btn_save);
    }
}

package com.aloogn.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aloogn.MyApplication;
import com.aloogn.famil_school.R;
import com.aloogn.util.MD5Util;
import com.aloogn.util.MyToastUtil;
import com.aloogn.util.OkHttpUtil;
import com.aloogn.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Context context;
    Button login_btn_login;
    TextView login_tv_forget;
    EditText login_et_account;
    EditText login_et_password;
//    private Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch ((int)msg.obj){
//                case 1:
//
//                    break;
//            }
//        }
//    };

    private class MyTask extends AsyncTask<String, Integer, Void> {

        private String account;
        private String password;

        public MyTask(String account, String password) {
            this.account = account;
            this.password = password;

        }

        @Override
        protected Void doInBackground(String... strings) {

//            Call call = OkHttpUtilZXL.getInstance().post("loginServlet", new OkHttpUtilZXL.PostFormBuilder() {
//                @Override
//                public FormBody onBuild() {
//                    return new FormBody.Builder()
//                            .add("account", account)
//                            .add("password", MD5Util.md5(password))
//                            .build();
//                }
//            });

            Map<String,Object> map = new HashMap<>();
            map.put("account",account);
            map.put("password",MD5Util.md5(password));

            Call call = OkHttpUtil.getInstance().post("loginServlet",map);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String str = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        int code = (int) jsonObject.get("code");
                        int role = (int) jsonObject.optInt("data");
                        String msg = (String) jsonObject.get("msg");

                        if(code == 1){
                            ((MyApplication)getApplication()).putSharedPreference("user","account");
                            ((MyApplication)getApplication()).putSharedPreference("user","password");
                            ((MyApplication)getApplication()).putSharedPreference("user",role);

//                            Integer s = (Integer) ((MyApplication)getApplication()).getSharedPreference("user",role);
//                            Log.d("s", String.valueOf(s));

                            if (role == 1){


                                Intent intent = new Intent(LoginActivity.this,TeacherMainActivity.class);
                                startActivity(intent);
                                return;
                            }

                            if (role == 2){
                                Intent intent = new Intent(LoginActivity.this,FamilyMainActivity.class);
                                startActivity(intent);
                                return;
                            }
                        }else {
                            Looper.prepare();
                            MyToastUtil.message(msg);
//                            Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.activity_login);

        context = this;

        login_btn_login = (Button) findViewById(R.id.login_btn_login);
        login_tv_forget = (TextView) findViewById(R.id.login_tv_forget);
        login_et_account = (EditText) findViewById(R.id.login_et_account);
        login_et_password = (EditText) findViewById(R.id.login_et_password);

        login_btn_login.setOnClickListener(this);
        login_tv_forget.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_login:

//                final String TestApp="TestApp";

                String account = login_et_account.getText().toString();
                String password = login_et_password.getText().toString();

                if(StringUtil.size(account) || StringUtil.init(account)){
                    MyToastUtil.message("帐号不能为空");
//                    Toast.makeText(LoginActivity.this,"帐号不能为空",Toast.LENGTH_LONG).show();
                    return;
                }

                if(StringUtil.init(password) || StringUtil.size(password)){
                    MyToastUtil.message("密码不能为空");
//                    Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
                    return;
                }

                MyTask myTask = new MyTask(account,password);
                myTask.execute();

//                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(MyApplication.getAppContext(),"user");
//                sharedPreferencesUtil.putSharedPreference(account,password);
//                String s = sharedPreferencesUtil.getSharedPreference(account,password).toString();
//                Log.d(TestApp,s);

//                FormBody formBody = new FormBody
//                        .Builder()
//                        .add("account",account)
//                        .add("password",password)
//                        .build();
//
//                Call call = OkHttpUtilZXL.getInstance().post("loginServlet",formBody);
//
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
////                        Toast.makeText(LoginActivity.this,"账号与密码不匹配",Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//
//                        String str = response.body().string();
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(str);
//                            int code = (int) jsonObject.get("code");
//                            int role = (int) jsonObject.optInt("data");
//                            String msg = (String) jsonObject.get("msg");
//
//                            if(code == 1 && msg.equals("登录成功") && role == 1){
//                                Intent intent = new Intent(LoginActivity.this,TeacherMainActivity.class);
//                                startActivity(intent);
//                                return;
//                            }
//
//                            if(code == 1 && msg.equals("登录成功") && role == 2){
//                                Intent intent = new Intent(LoginActivity.this,FamilyMainActivity.class);
//                                startActivity(intent);
//                                return;
//                            }
//
//                            if(msg.equals("账号与密码不匹配")){
//                                Toast.makeText(LoginActivity.this,"账号与密码不匹配",Toast.LENGTH_LONG).show();
//                                return;
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });

//                String url = "http://10.0.2.2:8080/loginServlet";
//                OkHttpClient okHttpClient = new OkHttpClient();
//                FormBody formBody = new FormBody
//                        .Builder()
//                        .add("account",account)
//                        .add("password",password)
//                        .build();
//
//                final Request request = new Request.Builder()
//                        .url(url)
//                        .post(formBody)//默认就是GET请求，可以不写
//                        .build();
//                Call call = okHttpClient.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
////                        Toast.makeText(LoginActivity.this,"账号与密码不匹配",Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//
//                        String str = response.body().string();
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(str);
//                            int code = (int) jsonObject.get("code");
//                            int role = (int) jsonObject.optInt("data");
//                            String msg = (String) jsonObject.get("msg");
//
//                            if(code == 1 && msg.equals("登录成功") && role == 1){
//                                Intent intent = new Intent(LoginActivity.this,TeacherMainActivity.class);
//                                startActivity(intent);
//                                return;
//                            }
//
//                            if(code == 1 && msg.equals("登录成功") && role == 2){
//                                Intent intent = new Intent(LoginActivity.this,FamilyMainActivity.class);
//                                startActivity(intent);
//                                return;
//                            }
//
//                            if(msg.equals("账号与密码不匹配")){
//                                Toast.makeText(LoginActivity.this,"账号与密码不匹配",Toast.LENGTH_LONG).show();
//                                return;
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
                break;

            case R.id.login_tv_forget:
//                Intent intent = new Intent(LoginActivity.this,ResetPasswordActivity.class);
//                startActivity(intent);
                break;
        }
    }
}

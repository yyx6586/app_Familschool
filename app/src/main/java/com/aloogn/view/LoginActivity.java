package com.aloogn.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import okhttp3.MediaType;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Context context;
    private Button login_btn_login;
    private TextView login_tv_forget,login_tv;
    private EditText login_et_account, login_et_password;
    private ProgressBar progressBar;


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

    private class MyTask extends AsyncTask<String, Integer, String> {

        private String account;
        private String password;
        public MyTask(String account, String password) {
            this.account = account;
            this.password = password;

        }

        @Override
        protected void onPreExecute() {
            login_tv.setText("登录中......");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

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

            String token = null;

            Call call = OkHttpUtil.getInstance().post("user/signIn",map,token);

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
                        String data = jsonObject.optString("data");
                        String msg = jsonObject.optString("msg");

                        JSONObject object = new JSONObject(data);
                        int role = object.optInt("role");
                        String token = object.optString("token");
                        String name = object.optString("name");

                        if(code == 1){
                            ((MyApplication)getApplication()).put("account",account);
                            ((MyApplication)getApplication()).put("password",MD5Util.md5(password));
                            ((MyApplication)getApplication()).put("role",role);
                            ((MyApplication)getApplication()).put("name",name);
                            ((MyApplication)getApplication()).put("token",token);

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
                            MyToastUtil.showLongToast(LoginActivity.this,msg);
                            Looper.loop();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    try {
//                        JSONObject jsonObject = new JSONObject(str);
//                        int code = (int) jsonObject.get("code");
//                        int role = (int) jsonObject.optInt("data");
//                        String msg = (String) jsonObject.get("msg");
//
//                        if(code == 1){
//                            ((MyApplication)getApplication()).put("account",account);
//                            ((MyApplication)getApplication()).put("password",MD5Util.md5(password));
//                            ((MyApplication)getApplication()).put("role",role);
//
////                            String s1 = ((MyApplication)getApplication()).getSharedPreference("account","account").toString();
////                            String s2 = ((MyApplication)getApplication()).getSharedPreference("password","password").toString();
////                            Integer s = (Integer) ((MyApplication)getApplication()).getSharedPreference("role",role);
//
//                            if (role == 1){
//
//                                Intent intent = new Intent(LoginActivity.this,TeacherMainActivity.class);
//                                startActivity(intent);
//                                return;
//                            }
//
//                            if (role == 2){
//                                Intent intent = new Intent(LoginActivity.this,FamilyMainActivity.class);
//                                startActivity(intent);
//                                return;
//                            }
//                        }else {
//                            Looper.prepare();
//                            MyToastUtil.showLongToast(LoginActivity.this,msg);
////                            Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_LONG).show();
//                            Looper.loop();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }
            });

            for(int i = 0; i<100; i++){
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            login_tv.setText("登录成功！");
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            login_tv.setText("登录中" + values[0] + "%");
            super.onProgressUpdate(values);
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
        login_tv = (TextView) findViewById(R.id.login_tv);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


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

                if(StringUtil.isNullOrEmpty(account)){
                    MyToastUtil.showLongToast(LoginActivity.this,"帐号不能为空");
//                    Toast.makeText(LoginActivity.this,"帐号不能为空",Toast.LENGTH_LONG).show();
                    return;
                }

                if(StringUtil.isNullOrEmpty(password)){
                    MyToastUtil.showLongToast(LoginActivity.this,"密码不能为空");
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
                Intent intent = new Intent(LoginActivity.this,FindPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}

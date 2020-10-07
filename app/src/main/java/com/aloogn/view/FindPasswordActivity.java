package com.aloogn.view;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class FindPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText reset_et_account;
    private EditText reset_et_name;
    private EditText reset_et_sex;
    private EditText reset_et_phone;
    private Button reset_btn_complete;
    private String account, name, sex, phone, password;
    private String token = null;
    private String password1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        reset_et_account = (EditText) findViewById(R.id.reset_et_account);
        reset_et_name = (EditText) findViewById(R.id.reset_et_name);
        reset_et_sex = (EditText) findViewById(R.id.reset_et_sex);
        reset_et_phone = (EditText) findViewById(R.id.reset_et_phone);
        reset_btn_complete = (Button) findViewById(R.id.reset_btn_complete);

        reset_btn_complete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset_btn_complete:
                account = reset_et_account.getText().toString();
                name = reset_et_name.getText().toString();
                sex = reset_et_sex.getText().toString();
                phone = reset_et_phone.getText().toString();

                if(StringUtil.isNullOrEmpty(account)){
                    MyToastUtil.showLongToast(FindPasswordActivity.this,"账号不能为空");
                    return;
                }

                if(StringUtil.isNullOrEmpty(name)){
                    MyToastUtil.showLongToast(FindPasswordActivity.this,"学生姓名不能为空");
                    return;
                }

                if(StringUtil.isNullOrEmpty(sex)){
                    MyToastUtil.showLongToast(FindPasswordActivity.this,"学生性别不能为空");
                    return;
                }

                if(StringUtil.isNullOrEmpty(phone)){
                    MyToastUtil.showLongToast(FindPasswordActivity.this,"家长手机号码不能为空");
                    return;
                }

                if(!StringUtil.isPhone(phone)){
                    MyToastUtil.showLongToast(FindPasswordActivity.this,"请输入正确格式的手机号码");
                    return;
                }

                MyTask myTask = new MyTask(account);
                myTask.execute();

                AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                        .setTitle("密码")//标题
                        .setMessage(password)//内容
                        .create();
                alertDialog1.show();
        }
    }

    protected class MyTask extends AsyncTask<String, Integer, String>{

        private String account;

        public MyTask(String account) {
            this.account = account;
        }

        @Override
        protected String doInBackground(String... strings) {

            Map<String,Object> map = new HashMap<>();
            map.put("account", account);

            Call call = OkHttpUtil.getInstance().post("user/findPassword", map, token);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        String data = jsonObject.optString("data");

                        JSONObject object = new JSONObject(data);
                        String account1 = object.optString("account");
                        String name1 = object.optString("name");
                        String sex1 = object.optString("sex");
                        String parent_phone = object.optString("parent_phone");
                        String user_phoe = object.optString("phone");
                        int role = object.optInt("role");
                        password1 = object.optString("password");
                        password = MD5Util.convertMD5(password1);

                        if(!account.equals(account1)){
                            Looper.prepare();
                            MyToastUtil.showLongToast(FindPasswordActivity.this,"该账号不存在，请重新输入");
                            Looper.loop();
                            return;
                        }

                        if(!name.equals(name1)){
                            Looper.prepare();
                            MyToastUtil.showLongToast(FindPasswordActivity.this,"学生姓名错误，请重新输入");
                            Looper.loop();
                            return;
                        }

                        if(!sex.equals(sex1)){
                            Looper.prepare();
                            MyToastUtil.showLongToast(FindPasswordActivity.this,"学生姓名错误，请重新输入");
                            Looper.loop();
                            return;
                        }

                        if(role == 1 && !phone.equals(user_phoe)){
                            Looper.prepare();
                            MyToastUtil.showLongToast(FindPasswordActivity.this,"用户手机号码错误，请重新输入");
                            Looper.loop();
                            return;
                        }

                        if(role == 2 && !phone.equals(parent_phone)){
                            Looper.prepare();
                            MyToastUtil.showLongToast(FindPasswordActivity.this,"家长手机号码错误，请重新输入");
                            Looper.loop();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }
    }

}

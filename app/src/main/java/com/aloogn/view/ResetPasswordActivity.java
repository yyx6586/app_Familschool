package com.aloogn.view;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aloogn.famil_school.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    EditText reset_et_account;
    EditText reset_et_password;
    EditText reset_et_resetPassword;
    Button reset_btn_complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        reset_et_account = (EditText) findViewById(R.id.reset_et_account);
        reset_et_password = (EditText) findViewById(R.id.reset_et_password);
        reset_et_resetPassword = (EditText) findViewById(R.id.reset_et_resetPassword);
        reset_btn_complete = (Button) findViewById(R.id.reset_btn_complete);

        reset_btn_complete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset_btn_complete:
                String account = reset_et_account.getText().toString();
                String password = reset_et_password.getText().toString();
                String resetPassword = reset_et_resetPassword.getText().toString();

                if(account == null || "".equals(account)){
                    Toast.makeText(ResetPasswordActivity.this,"账号不能为空",Toast.LENGTH_LONG).show();
                    return;
                }

                if(password == null || "".equals(password)){
                    Toast.makeText(ResetPasswordActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
                    return;
                }

                if(resetPassword == null || "".equals(resetPassword)){
                    Toast.makeText(ResetPasswordActivity.this,"请再次输入密码，不能为空",Toast.LENGTH_LONG).show();
                    return;
                }

                if (!password.equals(resetPassword)){
                    Toast.makeText(ResetPasswordActivity.this,"两次输入密码不一样，请重新输入",Toast.LENGTH_LONG).show();
                    return;
                }

                String url = "http://10.0.2.2:8080/resetPasswordServlet";
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody formBody = new FormBody
                        .Builder()
                        .add("account",account)
                        .add("password",password)
                        .build();

                final Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)//默认就是GET请求，可以不写
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("id", "onFailure: ");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();

                        try{
                            JSONObject jsonObject = new JSONObject(str);
                            String msg  = (String)jsonObject.get("msg");
                            if(msg.equals("密码修改成功")){
                                Looper.prepare();
                                Toast.makeText(ResetPasswordActivity.this,"密码修改成功",Toast.LENGTH_LONG).show();
                                Looper.loop();
                                return;
                            }

//                            if(msg.equals("密码修改失败")){
//                                Looper.prepare();
//                                Toast.makeText(ResetPasswordActivity.this,"密码修改失败",Toast.LENGTH_LONG).show();
//                                Looper.loop();
//                                return;
//                            }

                            if(msg.equals("该用户不存在")){
                                Looper.prepare();
                                Toast.makeText(ResetPasswordActivity.this,"该用户不存在",Toast.LENGTH_LONG).show();
                                Looper.loop();
                                return;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

                break;
        }
    }
}

package com.aloogn.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aloogn.famil_school.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button login_btn_login;
    TextView login_tv_forget;
    EditText login_et_account;
    EditText login_et_password;
    CheckBox login_cb_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn_login = (Button) findViewById(R.id.login_btn_login);
        login_tv_forget = (TextView) findViewById(R.id.login_tv_forget);
        login_et_account = (EditText) findViewById(R.id.login_et_account);
        login_et_password = (EditText) findViewById(R.id.login_et_password);
        login_cb_remember = (CheckBox) findViewById(R.id.login_cb_remember);

        login_btn_login.setOnClickListener(this);
        login_tv_forget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_login:
                String account = login_et_account.getText().toString();
                String password = login_et_password.getText().toString();

                if(account == null || "".equals(account)){
                    Toast.makeText(LoginActivity.this,"帐号不能为空",Toast.LENGTH_LONG).show();
                    return;
                }

                if(password == null || "".equals(password)){
                    Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
                    return;
                }

                String url = "http://10.0.2.2:8080/loginServlet";
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
                        Log.d("id", "onResponse: " + response.body().string());
                    }
                });
//                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
                break;
        }
    }
}

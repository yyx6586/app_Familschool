package com.aloogn.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aloogn.MyApplication;
import com.aloogn.famil_school.R;
import com.aloogn.util.MD5Util;
import com.aloogn.util.MyToastUtil;
import com.aloogn.util.OkHttpUtil;
import com.aloogn.util.StringUtil;
import com.aloogn.view.common.TitleLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdatePasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private TitleLayout mTitleLayout;
    private TextView mTitle;
    private EditText updatePassword_et_password, updatePassword_et_resetPassword, updatePassword_et_confirmPassword;
    private Button updatePassword_btn_save;

    protected class MyTask extends AsyncTask<String, Integer, String>{

        private String account, password, resetPassword, token;

        public MyTask(String account, String password, String resetPassword, String token) {
            this.account = account;
            this.password = password;
            this.resetPassword = resetPassword;
            this.token = token;
        }

        @Override
        protected String doInBackground(String... strings) {
            Map<String, Object> map = new HashMap<>();
            map.put("account",account);
            map.put("password",password);
            map.put("resetPassword", resetPassword);
            map.put("token",token);

            Call call = OkHttpUtil.getInstance().post("user/updatePassword",map,token);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String str = response.body().string();

                    try {
                        if(StringUtil.isNullOrEmpty(str)){
                            return;
                        }

                        JSONObject jsonObject = new JSONObject(str);
                        int code = (int) jsonObject.optInt("code");
                        final String msg = (String) jsonObject.optString("msg");

//                        if(code == 401){
//                            startActivity(new Intent(UpdatePasswordActivity.this,LoginActivity.class));
//                            return;
//                        }

                        if(code == 1){
                            ((MyApplication)getApplication()).remove("password");
                            ((MyApplication)getApplication()).put("password",resetPassword);
                            Looper.prepare();
                            MyToastUtil.showLongToast(UpdatePasswordActivity.this,msg);
                            Looper.loop();
                        }else {
                            Looper.prepare();
                            MyToastUtil.showLongToast(UpdatePasswordActivity.this,msg);
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
        setContentView(R.layout.activity_update_password);

        mTitleLayout = (TitleLayout)findViewById(R.id.ManualBinding_title);
        mTitle = (TextView)mTitleLayout.findViewById(R.id.title_text);

        updatePassword_et_password = (EditText) findViewById(R.id.updatePassword_et_password);
        updatePassword_et_resetPassword = (EditText) findViewById(R.id.updatePassword_et_resetPassword);
        updatePassword_et_confirmPassword = (EditText) findViewById(R.id.updatePassword_et_confirmPassword);
        updatePassword_btn_save = (Button) findViewById(R.id.updatePassword_btn_save);

        mTitle.setText("修改密码");

        updatePassword_btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.updatePassword_btn_save:
                String account = (String) ((MyApplication)getApplication()).get("account",null);
                String password = (String) ((MyApplication)getApplication()).get("password",null);
                String token = (String) ((MyApplication)getApplication()).get("token",null);

                String passwordPut = MD5Util.md5(updatePassword_et_password.getText().toString());
                String resetPassword = MD5Util.md5(updatePassword_et_resetPassword.getText().toString());
                String confirmPassword = MD5Util.md5(updatePassword_et_confirmPassword.getText().toString());

                if(StringUtil.isNullOrEmpty(passwordPut)){
                    MyToastUtil.showLongToast(UpdatePasswordActivity.this,"密码不能为空");
                    return;
                }

                if(StringUtil.isNullOrEmpty(resetPassword)){
                    MyToastUtil.showLongToast(UpdatePasswordActivity.this,"重置密码不能为空");
                    return;
                }

                if(StringUtil.isNullOrEmpty(confirmPassword)){
                    MyToastUtil.showLongToast(UpdatePasswordActivity.this,"确认密码不能为空");
                    return;
                }

                if(!password.equals(passwordPut)){
                    MyToastUtil.showLongToast(UpdatePasswordActivity.this,"密码错误，请重新输入");
                    return;
                }

                if(passwordPut.equals(resetPassword) || passwordPut.equals(confirmPassword)){
                    MyToastUtil.showLongToast(UpdatePasswordActivity.this,"新旧密码一样，请重新输入");
                    return;
                }

                if(!resetPassword.equals(confirmPassword)){
                    MyToastUtil.showLongToast(UpdatePasswordActivity.this,"两次输入的新密码不一样，请重新输入");
                    return;
                }
                MyTask myTask = new MyTask(account,password,resetPassword,token);
                myTask.execute();
                break;
        }
    }


//    public static String JSONTokener(String in) {
//        // consume an optional byte order mark (BOM) if it exists
//        if (in != null && in.startsWith("\ufeff")) {
//            in = in.substring(1);
//        }
//        return in;
//    }

    private void returnLogin(){
//        final String font = "登录";
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("密码修改成功，请返回重新");
//        builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                startActivity(new Intent(UpdatePasswordActivity.this,LoginActivity.class));
//            }
//        }).create().show();
//        builder.setTitle("密码修改成功");
//        builder.setMessage("请返回登录");
//        builder.setPositiveButton("返回登录", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Button positiveButton = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);
//                positiveButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(UpdatePasswordActivity.this,LoginActivity.class));
//                    }
//                });
//            }
//        });
    }
    }

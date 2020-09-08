package com.aloogn.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.aloogn.MyApplication;
import com.aloogn.famil_school.R;
import com.aloogn.util.StringUtil;

import static java.lang.String.*;

public class WelcomeActivity extends AppCompatActivity{

    private TextView welcome_tv_leap;
    private String account, password;
    Integer role;
//    private static int count = 5;
//    private static final int TIMER_COUNT = 99999;

//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
////            switch (msg.what){
////                case TIMER_COUNT:
////                    count --;
////                    if(count == 0){
////
////                        String account = ((MyApplication)getApplication()).getSharedPreference("user","account").toString();
////                        String password = ((MyApplication)getApplication()).getSharedPreference("user","password").toString();
////                        Integer userRole = (Integer) ((MyApplication)getApplication()).getSharedPreference("user","role");
////
////                        if((!StringUtil.init(account) && !StringUtil.size(account)) && (!StringUtil.init(password) && !StringUtil.size(password))){
////                            if(userRole == 1){
////                                Intent intent = new Intent(WelcomeActivity.this,TeacherMainActivity.class);
////                                startActivity(intent);
////                                return;
////                            }
////
////                            if(userRole == 2){
////                                Intent intent = new Intent(WelcomeActivity.this,FamilyMainActivity.class);
////                                startActivity(intent);
////                                return;
////                            }
////                        }else {
////                            Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
////                            startActivity(intent);
////                        }
////                    }
////                    handler.sendEmptyMessageDelayed(1,10000);
////            }
//        }
//    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if((!StringUtil.isNullOrEmpty(account)) && (!StringUtil.isNullOrEmpty(password))){
                role = Integer.parseInt(String.valueOf(((MyApplication) getApplication()).get("role",null)));
                if(role == 1){
                    Intent intent = new Intent(WelcomeActivity.this,TeacherMainActivity.class);
                    startActivity(intent);
                }

                if(role == 2){
                    Intent intent = new Intent(WelcomeActivity.this,FamilyMainActivity.class);
                    startActivity(intent);
                }

            }else {
                Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        welcome_tv_leap = (TextView) findViewById(R.id.welcome_tv_leap);

        account = (String) ((MyApplication)getApplication()).get("account",null);
        password = (String) ((MyApplication)getApplication()).get("password",null);
//        welcome_tv_leap.setOnClickListener(this);

        new Handler().postDelayed(runnable,2000);
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.welcome_tv_leap:
//                if((!StringUtil.init(account) && !StringUtil.size(account)) && (!StringUtil.init(password) && !StringUtil.size(password))){
//                    if(role == 1){
//                        Intent intent = new Intent(WelcomeActivity.this,TeacherMainActivity.class);
//                        startActivity(intent);
//                        return;
//                    }
//
//                    if(role == 2){
//                        Intent intent = new Intent(WelcomeActivity.this,FamilyMainActivity.class);
//                        startActivity(intent);
//                        return;
//                    }
//                }else {
//                    Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                }
//                break;
//        }
//    }
}

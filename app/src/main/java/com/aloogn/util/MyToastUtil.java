package com.aloogn.util;

import android.content.Context;
import android.os.Message;
import android.widget.Toast;

import com.aloogn.MyApplication;

import static com.aloogn.MyApplication.*;

/**
 * author : zxl
 * e-mail : loogn.zou@foxmail.com
 * date   : 2020/7/2110:53 PM
 * desc   :
 */
public class MyToastUtil {

//    private static Context context;
    private static Toast toast;

    public static void message(String msg){

        if(MyApplication.getAppContext() != null){
            if(toast == null)
                toast = Toast.makeText(MyApplication.getAppContext(), msg, Toast.LENGTH_LONG);
            else {
                toast.setText(msg);
            }
            toast.show();
        }
    }
}

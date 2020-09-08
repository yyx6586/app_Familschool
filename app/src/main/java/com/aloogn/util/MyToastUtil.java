package com.aloogn.util;

import android.content.Context;
import android.os.Message;
import android.view.Gravity;
import android.widget.GridView;
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

    //长时间显示 Toast
    public static void showLongToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    //短时间显示 Toast
    public static void showShortToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    //自定义 Toast 显示的时间    duration 单位:毫秒
    public static void showTime(Context context, String msg, int duration) {
        Toast.makeText(context, msg, duration).show();
    }

    //自定义 Toast 长时间显示的位置   gravity方向:上中下等
    public static void showLongAndLocation(Context context, String msg, int gravity, int xOffset, int yOffset){
        Toast toast;
        toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.show();
    }

    //自定义 Toast 短时间显示的位置
    public static void showShortAndLocation(Context context, String msg, int gravity, int xOffset, int yOffset){
        Toast toast;
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.show();
    }

    //自定义 Toast 显示的位置和时间
    public static void showLocationAndTime(Context context, String msg, int duration, int gravity, int xOffset, int yOffset){
        Toast toast;
        toast = Toast.makeText(context, msg, duration);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.show();
    }
}

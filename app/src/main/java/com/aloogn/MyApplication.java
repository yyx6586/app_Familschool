package com.aloogn;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * author : zxl
 * e-mail : loogn.zou@foxmail.com
 * date   : 2020/7/610:26 PM
 * desc   :
 */
public class MyApplication extends Application{
    private static Context context;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String LOCAL_DATA = "local_data";


    public static Context getAppContext(){
        return context.getApplicationContext();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        context = this;
        sharedPreferences = context.getSharedPreferences(LOCAL_DATA, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    //保存
    public void putSharedPreference(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.commit();
    }

    //读取
    public Object getSharedPreference(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        }

        if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        }

        if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        }

        if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        }

        if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        }

        return sharedPreferences.getString(key, null);

    }

    //移除某个key值已经对应的值
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    //清除所有数据
    public void clear() {
        editor.clear();
        editor.commit();
    }

    //查询某个key是否存在
    public Boolean contain(String key) {
        return sharedPreferences.contains(key);
    }
}

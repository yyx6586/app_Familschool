package com.aloogn.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * author : zxl
 * e-mail : loogn.zou@foxmail.com
 * date   : 2020/7/197:34 PM
 * desc   :
 */
public class SharedPreferencesUtilYYX {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

//    public SharedPreferencesUtil(Context context,String FILE_NAME) {
//        sharedPreferences = ApplicationSharedPreferencesUtil.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//    }
//
//    //保存
//    public void putSharedPreference(String key, Object object) {
//        if (object instanceof String) {
//            editor.putString(key, (String) object);
//        } else if (object instanceof Integer) {
//            editor.putInt(key, (Integer) object);
//        } else if (object instanceof Boolean) {
//            editor.putBoolean(key, (Boolean) object);
//        } else if (object instanceof Float) {
//            editor.putFloat(key, (Float) object);
//        } else if (object instanceof Long) {
//            editor.putLong(key, (Long) object);
//        } else {
//            editor.putString(key, object.toString());
//        }
//        editor.commit();
//    }
//
//    //读取
//    public Object getSharedPreference(String key, Object defaultObject) {
//        if (defaultObject instanceof String) {
//            return sharedPreferences.getString(key, (String) defaultObject);
//        } else if (defaultObject instanceof Integer) {
//            return sharedPreferences.getInt(key, (Integer) defaultObject);
//        } else if (defaultObject instanceof Boolean) {
//            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
//        } else if (defaultObject instanceof Float) {
//            return sharedPreferences.getFloat(key, (Float) defaultObject);
//        } else if (defaultObject instanceof Long) {
//            return sharedPreferences.getLong(key, (Long) defaultObject);
//        } else {
//            return sharedPreferences.getString(key, null);
//        }
//    }
//
//    //移除某个key值已经对应的值
//    public void remove(String key) {
//        editor.remove(key);
//        editor.commit();
//    }
//
//    //清除所有数据
//    public void clear() {
//        editor.clear();
//        editor.commit();
//    }
//
//    //查询某个key是否存在
//    public Boolean contain(String key) {
//        return sharedPreferences.contains(key);
//    }


//    public static void setParem(Context context, String key, Object object){
//        String type = object.getClass().getSimpleName();
//        SharedPreferences sp = MyApplication.getAppContext().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        if ("String".equals(type)){
//            editor.putString(key,(String)object);
//        }else if ("Boolean".equals(type)){
//            editor.putBoolean(key,(Boolean)object);
//        }else if ("Integer".equals(type)){
//            editor.putInt(key,(Integer)object);
//        }else if ("Float".equals(type)){
//            editor.putFloat(key,(Float)object);
//        }else if ("Long".equals(type)){
//            editor.putLong(key,(Long)object);
//        }
//        editor.commit();
//    }
//
//    public static Object getParem(Context context,String key,Object defaultObject){
//        String type = defaultObject.getClass().getSimpleName();
//        SharedPreferences sp = MyApplication.getAppContext().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
//        if ("String".equals(type)){
//            sp.getString(key,(String)defaultObject);
//        }else if ("Boolean".equals(type)){
//            sp.getBoolean(key,(Boolean)defaultObject);
//        }else if ("Integer".equals(type)){
//            sp.getInt(key,(Integer)defaultObject);
//        }else if ("Float".equals(type)){
//            sp.getFloat(key,(Float)defaultObject);
//        }else if ("Long".equals(type)){
//            sp.getLong(key,(Long)defaultObject);
//        }
//        return null;
//    }
//

//    //存入:String类型
//    public static void putString(String key, String value){
//        SharedPreferences sp =  MyApplication.getAppContext().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString(key,value).apply();
//    }
//
//    //获取:String类型
//    public static String getString(String key, String defValue){
//        SharedPreferences sp =  MyApplication.getAppContext().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
//        return sp.getString(key,defValue);
//    }
//
//    //存入:int类型
//    public static void putInt(String key, int value){
//        SharedPreferences sp =  MyApplication.getAppContext().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putInt(key,value).apply();
//    }
//
//    //获取:int类型
//    public static int getInt(String key, int defValue){
//        SharedPreferences sp =  MyApplication.getAppContext().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
//        return sp.getInt(key,defValue);
//    }
//
//    //存入:boolean类型
//    public static void putBoolean(String key, boolean value){
//        SharedPreferences sp =  MyApplication.getAppContext().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putBoolean(key,value).apply();
//    }
//
//    //获取:boolean类型
//    public static boolean getBoolean(String key, boolean defValue){
//        SharedPreferences sp =  MyApplication.getAppContext().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
//        return sp.getBoolean(key,defValue);
//    }
//
//    //刪除 单个
//    public static void deleShare(String key){
//        SharedPreferences sp =  MyApplication.getAppContext().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.remove(key).apply();
//    }
//
//    //刪除 全部
//    public static void deleAll(){
//        SharedPreferences sp =  MyApplication.getAppContext().getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.clear().apply();
//    }
}

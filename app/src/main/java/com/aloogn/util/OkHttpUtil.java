package com.aloogn.util;

import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * author : zxl
 * e-mail : loogn.zou@foxmail.com
 * date   : 2020/7/1710:57 PM
 * desc   : 单例模式
 */
public class OkHttpUtil {

    String url = "http://10.0.2.2:8080//";

    OkHttpClient okHttpClient = new OkHttpClient();

    //1.自定义一个自己
    private static OkHttpUtil instance = null;

    //2.将构造方法私有化
    private OkHttpUtil(){

    }

    public static OkHttpUtil getInstance(){
        if(instance == null){
            instance = new OkHttpUtil();
        }

        return instance;
    }

    public Call post(String api, Map<String,Object> map) {

        FormBody.Builder builder = new FormBody.Builder();
        if(map != null && map.size() > 0){
            for(String key : map.keySet()){
                builder.add(key, (String) map.get(key));
            }
        }

        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url+api)
                .post(formBody)
                .build();


//        Request.Builder builder=new Request.Builder();
//        if(head != null && head.size() > 0){
//            for (Map.Entry<String, String> entry : head.entrySet()) {
//                builder.addHeader(entry.getKey(),entry.getValue());
//            }
//        }
//        FormBody formBody = new FormBody.Builder().build();
//
//        if(map != null && map.size() > 0){
//            for (Map.Entry<String, String> entry : head.entrySet()) {
//                formBody.add(entry.getKey(),entry.getValue());
//            }
//        }

//        Request request = new Request.Builder()
//                .url(url+api)
//                .post(formBody)
//                .build();

//        Request request = new Request.Builder()
//                .url(url+api)
//                .post(formBody)//默认就是GET请求，可以不写
//                .build();
        Call call = okHttpClient.newCall(request);

        return call;
    }

//    public interface PostFormBuilder{
//        FormBody onBuild();
//    }
}

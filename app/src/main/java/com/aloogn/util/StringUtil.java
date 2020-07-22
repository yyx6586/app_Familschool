package com.aloogn.util;

/**
 * author : zxl
 * e-mail : loogn.zou@foxmail.com
 * date   : 2020/7/2110:53 PM
 * desc   :
 */
public class StringUtil {
    private static Boolean isEmpty = true;

    public StringUtil(){

    }

    public static Boolean init(String string){
        if(string == null){
            isEmpty = true;
        }else {
            isEmpty = false;
        }
        return isEmpty;
    }

    public static Boolean size(String string){
        if(string.equals("")){
            isEmpty = true;
        }else {
            isEmpty = false;
        }
        return isEmpty;
    }

}

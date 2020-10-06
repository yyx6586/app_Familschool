package com.aloogn.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author : zxl
 * e-mail : loogn.zou@foxmail.com
 * date   : 2020/9/2710:46 AM
 * desc   :
 */
public class DateUtil {

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp2Date(String seconds) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(Long.valueOf(seconds)));
    }
}

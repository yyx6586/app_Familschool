package com.aloogn.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * author : zxl
 * e-mail : loogn.zou@foxmail.com
 * date   : 2020/9/293:08 PM
 * desc   :
 */
public class JSONArryUtil {
    /**
     * 按照JSONArray中的对象的某个字段进行排序(采用fastJson)
     *
     * @param jsonArrStr
     *            json数组字符串
     *
     */

    public static JSONArray jsonArraySort(JSONArray jsonArrStr) throws JSONException {
        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonArrStr.length(); i++) {
            jsonValues.add(jsonArrStr.getJSONObject(i));
        }

        Collections.sort(jsonValues, new Comparator<JSONObject>() {

            // You can change "Name" with "ID" if you want to sort by ID
            private static final String KEY_NAME = "id";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();
                try {
                    // 这里是a、b需要处理的业务，需要根据你的规则进行修改。
                    String aStr = a.getString(KEY_NAME);
                    valA = aStr.replaceAll("-", "");
                    String bStr = b.getString(KEY_NAME);
                    valB = bStr.replaceAll("-", "");
                } catch (JSONException e) {
                    // do something
                }
                return -valA.compareTo(valB);
                // if you want to change the sort order, simply use the following:
                // return -valA.compareTo(valB);
            }
        });
        for (int i = 0; i < jsonArrStr.length(); i++) {
            sortedJsonArray.put(jsonValues.get(i));
        }
        return sortedJsonArray;
    }
}

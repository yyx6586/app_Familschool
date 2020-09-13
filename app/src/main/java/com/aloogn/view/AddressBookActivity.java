package com.aloogn.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.aloogn.MyApplication;
import com.aloogn.famil_school.R;
import com.aloogn.util.MyToastUtil;
import com.aloogn.util.OkHttpUtil;
import com.aloogn.view.adapter.ExpandableLIstViewAdapter;
import com.aloogn.view.common.TitleLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddressBookActivity extends AppCompatActivity {
    private TitleLayout mTitleLayout;
    private TextView mTitle;
    private ExpandableListView addressBook_expandableListView;
    final String grade_id = "22";
    private JSONArray data;
    private List<String> groupList;
    private List<List<String>> childList;
    private List<String> childList1;
    private List<String> childList2;

    protected class MyTask extends AsyncTask<String,Integer,String>{

        private String grade_id;

        public MyTask(String grade_id) {
            this.grade_id = grade_id;
        }

        @Override
        protected String doInBackground(String... strings) {
            String token = (String) ((MyApplication)getApplication()).get("token",null);

            Map<String,Object> map = new HashMap<>();
            map.put("grade_id",grade_id);

            Call call = OkHttpUtil.getInstance().post("userGrade/userTeacher",map,token);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        data = jsonObject.optJSONArray("data");

                        if(data.length() > 0){
                            for(int i = 0; i < data.length(); i ++){
                                JSONObject job = data.getJSONObject(i);
                                String account = job.getString("id");
                                int role = job.optInt("role");
                                String name = job.optString("name");

                                if(role == 1){
                                    childList1.add(name + " ( " + account + " )");
                                    childList.add(childList1);
                                }

                                if(role == 2){
                                    childList2.add(name + " ( " + account + " )");
                                    childList.add(childList2);
                                }
                            }
                        }else {
                            MyToastUtil.showLongToast(AddressBookActivity.this,"获取联系人错误，请重新登录!");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }
    }

//    public void test(View v){
////        final String str  = 网络请求结果;
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                //更改UI；
//                tvCode.setText(str);
//            }
//        });
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);

        mTitleLayout = (TitleLayout)findViewById(R.id.ManualBinding_title);
        mTitle = (TextView)mTitleLayout.findViewById(R.id.title_text);
        addressBook_expandableListView = (ExpandableListView) findViewById(R.id.addressBook_expandableListView);

        mTitle.setText("通讯录");

        groupList = new ArrayList<>();
        groupList.add("教师列表");
        groupList.add("学生列表");

        childList = new ArrayList<>();
        childList1 = new ArrayList<>();
        childList2 = new ArrayList<>();

        MyTask myTask = new MyTask(grade_id);
        myTask.execute();
//        ExpandableListAdapter expandableListAdapter = new ExpandableLIstViewAdapter(groupList,childList);
//        addressBook_expandableListView.setAdapter(expandableListAdapter);
//
//        //一级点击监听
//        addressBook_expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                return false;
//            }
//        });
//
//        //二级点击监听
//        addressBook_expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                return false;
//            }
//        });
//        try {
//            if(data.length() > 0){
//                for(int i = 0; i < data.length(); i ++){
//                    JSONObject job = data.getJSONObject(i);
//                    String account = job.getString("id");
//                    int role = job.optInt("role");
//                    String name = job.optString("name");
//
//                    if(role == 1){
//                         childList1.add(name + " ( " + account + " )");
//                         childList.add(childList1);
//                    }
//
//                    if(role == 2){
//                        childList2.add(name + " ( " + account + " )");
//                        childList.add(childList2);
//                    }
//                }
//
//                ExpandableListAdapter expandableListAdapter = new ExpandableLIstViewAdapter(groupList,childList);
//                addressBook_expandableListView.setAdapter(expandableListAdapter);
//
//                //一级点击监听
//                addressBook_expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//                    @Override
//                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                        return false;
//                    }
//                });
//
//                //二级点击监听
//                addressBook_expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//                    @Override
//                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                        String value = (String) parent.getExpandableListAdapter().getChild(groupPosition,childPosition);
//                        Intent intent = new Intent(AddressBookActivity.this,FamilyPersonalDetailsActivity.class);
//                        intent.putExtra("value",value);
//                        startActivity(intent);
//                        return true;
//                    }
//                });
//            }else {
//                MyToastUtil.showLongToast(AddressBookActivity.this,"获取联系人错误，请重新登录!");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        List<String> groupList = new ArrayList<>();
//        groupList.add("教师列表");
//        groupList.add("学生列表");
//
//        List<List<String>> childList = new ArrayList<>();
//        List<String> childList1 = new ArrayList<>();
//
//        List<String> childList2 = new ArrayList<>();
//
//        childList.add(childList1);
//        childList.add(childList2);
//
//        ExpandableListAdapter expandableListAdapter = new ExpandableLIstViewAdapter(groupList,childList);
//        addressBook_expandableListView.setAdapter(expandableListAdapter);
//
//        //一级点击监听
//        addressBook_expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                return false;
//            }
//        });
//
//        //二级点击监听
//        addressBook_expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                return false;
//            }
//        });

        ExpandableListAdapter expandableListAdapter = new ExpandableLIstViewAdapter(groupList,childList);
        addressBook_expandableListView.setAdapter(expandableListAdapter);

        //一级点击监听
        addressBook_expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        //二级点击监听
        addressBook_expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String value = (String) parent.getExpandableListAdapter().getChild(groupPosition,childPosition);

                if(groupPosition == 0){
                    Intent intent = new Intent(AddressBookActivity.this,SchoolPersonalDetailsActivity.class);
                    intent.putExtra("values",value);
                    startActivity(intent);
                }

                if(groupPosition == 1){
                    Intent intent = new Intent(AddressBookActivity.this,FamilyPersonalDetailsActivity.class);
                    intent.putExtra("value",value);
                    startActivity(intent);
                }
                return true;
            }
        });
    }
}

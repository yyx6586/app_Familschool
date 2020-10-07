package com.aloogn.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aloogn.MyApplication;
import com.aloogn.famil_school.R;
import com.aloogn.util.DateUtil;
import com.aloogn.util.JSONArryUtil;
import com.aloogn.util.MyToastUtil;
import com.aloogn.util.OkHttpUtil;
import com.aloogn.view.adapter.ListViewAdapter;
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

public class HomeworkSchoolAreledyActivity extends AppCompatActivity {

    private TitleLayout mTitleLayout;
    private TextView mTitle;
    private ListView homeworkSchoolAreledy_listView;
    private List<List<String>> mlist = new ArrayList<>();
    private String grade_id1 = "22";
    private String account;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_school_areledy);

        //控件初始化
        mTitleLayout = (TitleLayout) findViewById(R.id.ManualBinding_title);
        mTitle = (TextView) findViewById(R.id.title_text);
        homeworkSchoolAreledy_listView = (ListView) findViewById(R.id.homeworkSchoolAreledy_listView);

        mTitle.setText("作业详情");

        //获取当前账号
        account = (String) ((MyApplication)getApplication()).get("account", null);

        //获取 token
        token = (String) ((MyApplication)getApplication()).get("token", null);

        MyTask myTask = new MyTask(account);
        myTask.execute();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ListViewAdapter listViewAdapter = new ListViewAdapter(mlist);
        homeworkSchoolAreledy_listView.setAdapter(listViewAdapter);

        //给 listview 设置点击事件
        homeworkSchoolAreledy_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String values = parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(HomeworkSchoolAreledyActivity.this, HomeworkSchoolDetailsActivity.class);
                intent.putExtra("values", values);
                startActivity(intent);
            }
        });

    }

    protected class MyTask extends AsyncTask<String, Integer, String>{

        private String account;

        public MyTask(String account) {
            this.account = account;
        }

        @Override
        protected String doInBackground(String... strings) {

            Map<String, Object> map = new HashMap<>();
            map.put("account",account);

            Call call = OkHttpUtil.getInstance().post("userHomework/homeworkSchoolAreledy", map, token);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        JSONArray data = jsonObject.optJSONArray("data");

                        JSONArray data1 = JSONArryUtil.jsonArraySort(data);

                        for(int i = 0; i < data1.length(); i ++){
                            JSONObject object = data1.getJSONObject(i);

                            String homework = object.optString("homework");
                            String grade_id = object.optString("grade_id");
                            String creat_time = object.optString("creat_time");

                            String time = DateUtil.timeStamp2Date(creat_time);
                            String dateTime = time.substring(time.length() - 14, time.length() - 9);

                            if(grade_id.equals(grade_id1)){
                                List<String> list = new ArrayList<>();
                                list.add(homework);
                                list.add(dateTime);
                                mlist.add(list);
                            }else {
                                Looper.prepare();
                                MyToastUtil.showLongToast(HomeworkSchoolAreledyActivity.this,"获取作业信息失败，请重新登录");
                                Looper.loop();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }
    }
}

package com.aloogn.view;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.aloogn.util.OkHttpUtil;
import com.aloogn.view.adapter.ListViewAdapter;
import com.aloogn.view.common.TitleLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NoticeFamilyActivity extends AppCompatActivity {

    private TitleLayout mTitleLayout;
    private TextView mTitle;
    private ListView notice_family_listView;
    private List<List<String>> mlist = new ArrayList<>();
    private String grade_id = "22";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_family);

        mTitleLayout = (TitleLayout)findViewById(R.id.ManualBinding_title);
        mTitle = (TextView)mTitleLayout.findViewById(R.id.title_text);
        notice_family_listView = (ListView) findViewById(R.id.notice_family_listView);

        mTitle.setText("通知信息");

        MyTask myTask = new MyTask(grade_id);
        myTask.execute();

        /**
         * 组ListView组件设置Adapter,并设置滑动监听事件。
         */

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ListViewAdapter listViewAdapter = new ListViewAdapter(mlist);
        notice_family_listView.setAdapter(listViewAdapter);

        notice_family_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String values = parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(NoticeFamilyActivity.this, NoticeFamilyDetailsActivity.class);
                intent.putExtra("values", values);
                startActivity(intent);
            }
        });
    }

    protected class MyTask extends AsyncTask<String, Integer, String>{

        private String grade_id;

        public MyTask(String grade_id) {
            this.grade_id = grade_id;
        }

        @Override
        protected String doInBackground(String... strings) {

            Map<String, Object> map = new HashMap<>();
            map.put("grade_id", grade_id);

            String token = (String) ((MyApplication)getApplication()).get("token",null);

            Call call = OkHttpUtil.getInstance().post("noticeInformation/noticeFamilyDetails",map,token);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        JSONArray date = jsonObject.optJSONArray("data");

                        JSONArray data1 = JSONArryUtil.jsonArraySort(date);

                        for(int i = 0; i < data1.length(); i ++){
                            JSONObject object = data1.getJSONObject(i);

                            String information = object.optString("information");
                            String creat_time = object.getString("creat_time");

                            String time = DateUtil.timeStamp2Date(creat_time);
                            String dateTime = time.substring(time.length() - 14, time.length() - 9);

                            List<String> list = new ArrayList<>();
                            list.add(information);
                            list.add(dateTime);
                            mlist.add(list);
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

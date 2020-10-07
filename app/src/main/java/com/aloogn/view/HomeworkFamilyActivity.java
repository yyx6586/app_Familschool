package com.aloogn.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeworkFamilyActivity extends AppCompatActivity {

    private TitleLayout mTitleLayout;
    private TextView mTitle;
    private LinearLayout linearLayout;
//    private Spinner title_spinner;
    private ListView homework_family_listView;
    private List<List<String>> mlist = new ArrayList<>();
    private String token;
    private String subject_name;
    private String grade_id = "22";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_family);

        mTitleLayout = (TitleLayout)findViewById(R.id.ManualBinding_title);
        mTitle = (TextView)mTitleLayout.findViewById(R.id.title_text);
        linearLayout = (LinearLayout) mTitleLayout.findViewById(R.id.title_LinearLayout);
//        title_spinner = (Spinner) mTitleLayout.findViewById(R.id.title_spinner);
        homework_family_listView = (ListView) findViewById(R.id.homework_family_listView);

        mTitle.setText("作业");
//        linearLayout.setVisibility(View.VISIBLE);

//        //给 spinner设置数据
//        final String[] subjects = {"语文", "数学", "英语", "科学"};
//        ArrayAdapter adapter = new ArrayAdapter(HomeworkFamilyActivity.this,R.layout.spinner_item, subjects);
//        title_spinner.setAdapter(adapter);

        //获取 token
        token = (String) ((MyApplication)getApplication()).get("token", token);

//        //获取 spinner 选中的的数据
//        title_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                subject_name = subjects[position];
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        MyTask myTask = new MyTask(grade_id);
        myTask.execute();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ListViewAdapter listViewAdapter = new ListViewAdapter(mlist);
        homework_family_listView.setAdapter(listViewAdapter);

        //给 listview 设置点击事件
        homework_family_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String values = parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(HomeworkFamilyActivity.this, HomeworkFamilyDetailsActivity.class);
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

            final Map<String, Object> map = new HashMap<>();
            map.put("grade_id", grade_id);

            Call call = OkHttpUtil.getInstance().post("userHomework/homeworkFamily",map,token);
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
                            String subject_name = object.optString("subject_name");
                            String creat_time = object.optString("creat_time");

                            String time = DateUtil.timeStamp2Date(creat_time);
                            String dateTime = time.substring(time.length() - 14, time.length() - 9);

                            List<String> list = new ArrayList<>();
                            list.add(subject_name + ": " +homework);
                            list.add(dateTime);
                            mlist.add(list);

//                            if(subject_name.equals(subject_name1)){
//                                List<String> list = new ArrayList<>();
//                                list.add(dateTime);
//                                list.add(homework);
//                                mlist.add(list);
//                            }else {
//                                List<String> list = new ArrayList<>();
//                                list.add(dateTime);
//                                list.add(homework);
//                                mlist.add(list);
//                            }
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

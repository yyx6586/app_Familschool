package com.aloogn.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aloogn.MyApplication;
import com.aloogn.famil_school.R;
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

public class NoticeSchoolAreledyActivity extends AppCompatActivity{

    private String account, token;
    private JSONArray data;
    private TitleLayout mTitleLayout;
    private TextView mTitle;
    private ListView noticeSchoolAreledy_listView;
    private LinearLayout mLoadLayout;
    private List<List<String>> mlist = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private int mCount = 30; //一次加载的数量
    private String grade_id1 = "22";
//    private int mLastItem = 0;
//    private ListViewAdapter listViewAdapter;
    private boolean isBottom = false;   //用于标记是否到达顶端


    private final LinearLayout.LayoutParams mProgressBarLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    private final LinearLayout.LayoutParams mTipContentLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    protected class MyTask extends AsyncTask<String, Integer, String>{

        private String account;

        public MyTask(String account) {
            this.account = account;
        }

        @Override
        protected String doInBackground(String... strings) {
            account = (String) ((MyApplication)getApplication()).get("account",null);
            token = (String) ((MyApplication)getApplication()).get("token",null);

            Map<String,Object> map = new HashMap<>();
            map.put("account", account);

            Call call = OkHttpUtil.getInstance().post("noticeInformation/schoolNoticeInformationAreledy",map,token);
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_school_areledy);

        init();
        mTitle.setText("通知信息");

        MyTask myTask = new MyTask(account);
        myTask.execute();

//        /**
//         * "加载项"布局，此布局被添加到ListView的Footer中。
//         */
//        mLoadLayout = new LinearLayout(this);
//        mLoadLayout.setMinimumHeight(60);
//        mLoadLayout.setGravity(Gravity.CENTER);
//        mLoadLayout.setOrientation(LinearLayout.HORIZONTAL);
//
//        /**
//         * 向"加载项"布局中添加一个圆型进度条。
//         */
//        ProgressBar progressBar = new ProgressBar(this);
//        progressBar.setPadding(0,0,15,0);
//        mLoadLayout.addView(progressBar,mProgressBarLayoutParams);
//
//        /**
//         * 向"加载项"布局中添加提示信息。
//         */
//        TextView textView = new TextView(this);
//        textView.setText("加载中...");
//        mLoadLayout.addView(textView,mTipContentLayoutParams);
//
//        /**
//         * 获取ListView组件，并将"加载项"布局添加到ListView组件的Footer中。
//         */
////        noticeSchoolAreledy_listView = getListView();
//        noticeSchoolAreledy_listView.addFooterView(mLoadLayout);

        /**
         * 组ListView组件设置Adapter,并设置滑动监听事件。
         */

        if(data.length() > 0){
            for(int i = 0; i < data.length(); i ++){
                try {
                    JSONObject obj = data.getJSONObject(i);
                    String information = obj.optString("information");
                    String grade_id = obj.optString("grade_id");
                    String creat_time = obj.getString("creat_time");

                    if(grade_id.equals(grade_id1)){
                        list.add(information);
                        list.add(creat_time);
                        mlist.add(list);
                    }else {
                        MyToastUtil.showLongToast(NoticeSchoolAreledyActivity.this,"获取信息错误，请重新登录");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ListViewAdapter listViewAdapter = new ListViewAdapter(mlist, this);
            noticeSchoolAreledy_listView.setAdapter(listViewAdapter);
        }

//        setListAdapter(new ListViewAdapter(mlist,context));
//        noticeSchoolAreledy_listView.setOnScrollListener(this);
    }

    public void init(){
        mTitleLayout = (TitleLayout) findViewById(R.id.ManualBinding_title);
        mTitle = (TextView) findViewById(R.id.title_text);
        noticeSchoolAreledy_listView = (ListView) findViewById(R.id.noticeSchoolAreledy_listView);
    }

//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//        if(isBottom && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
//            //简单模拟数据加载过程
//            if(data.length() > 0){
//                for(int i = 0; i < data.length(); i ++){
//                    try {
//                        JSONObject obj = data.getJSONObject(i);
//                        String information = obj.optString("information");
//
//                        mlist.add(information);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            ListViewAdapter listViewAdapter = new ListViewAdapter(mlist,this);
//            noticeSchoolAreledy_listView.setAdapter(listViewAdapter);
////            setListAdapter(new ListViewAdapter(mlist,context));
//        }
//        listViewAdapter.notifyDataSetChanged();
//        isBottom = false;
//    }
//
//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        if(firstVisibleItem + visibleItemCount == totalItemCount){
//            isBottom = true;
//        }else {
//            isBottom = false;
//        }
//    }

}

package com.aloogn.view.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.aloogn.MyApplication;
import com.aloogn.famil_school.R;
import com.aloogn.util.MyToastUtil;
import com.aloogn.util.OkHttpUtil;
import com.aloogn.util.StringUtil;
import com.aloogn.view.UpdatePasswordActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolNoticeReleaseFragment extends Fragment {

    private EditText fragmentSchoolNoticeRelease;
    private Button fragmentSchoolNoticeRelease_btn;
    private String token;


    public SchoolNoticeReleaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
                View view = inflater.inflate(R.layout.fragment_school_notice_release, container, false);
                return view;
    }

    protected class MyTask extends AsyncTask<String, Integer, String>{

        private String information;

        public MyTask(String information) {
            this.information = information;
        }

        @Override
        protected String doInBackground(String... strings) {

            Map<String,Object> map = new HashMap<>();
            map.put("information", information);

            token = (String) ((MyApplication)getActivity().getApplication()).get("token", null);

            Call call = OkHttpUtil.getInstance().post("noticeInformation/schoolNoticeInformationRelease", map, token);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String str = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        int code = jsonObject.optInt("code");
                        String msg = jsonObject.optString("msg");

                        if(code == 1){
                            Looper.prepare();
                            MyToastUtil.showLongToast(getActivity(),msg);
                            Looper.loop();
                        }else {
                            Looper.prepare();
                            MyToastUtil.showLongToast(getActivity(),msg);
                            Looper.loop();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragmentSchoolNoticeRelease = (EditText) getActivity().findViewById(R.id.fragmentSchoolNoticeRelease);
        fragmentSchoolNoticeRelease_btn = (Button) getActivity().findViewById(R.id.fragmentSchoolNoticeRelease_btn);

        final String information = fragmentSchoolNoticeRelease.getText().toString();

        fragmentSchoolNoticeRelease_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(StringUtil.isNullOrEmpty(information)){
                    MyToastUtil.showLongToast(getActivity(),"发布的信息不能为空");
                    return;
                }
                MyTask myTask = new MyTask(information);
                myTask.execute();
            }
        });


    }
}

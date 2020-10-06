package com.aloogn.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.aloogn.famil_school.R;
import com.aloogn.view.common.TitleLayout;

public class NoticeFamilyDetailsActivity extends AppCompatActivity {

    private TitleLayout mTitleLayout;
    private TextView mTitle;
    private String information;
    private String time;
    private TextView noticeFamily_tv_time, noticeFamily_tv_information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_family_details);

        mTitleLayout = (TitleLayout) findViewById(R.id.ManualBinding_title);
        mTitle = (TextView) findViewById(R.id.title_text);
        noticeFamily_tv_time = (TextView) findViewById(R.id.noticeFamily_tv_time);
        noticeFamily_tv_information = (TextView) findViewById(R.id.noticeFamily_tv_information);

        mTitle.setText("信息详情");

        Intent intent = getIntent();
        String values = intent.getStringExtra("values");
        information = values.substring(1,values.length() - 8);
        time = values.substring(values.length() - 6, values.length() - 1);

        noticeFamily_tv_time.setText(time);
        noticeFamily_tv_information.setText(information);
    }
}

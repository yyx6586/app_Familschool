package com.aloogn.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.aloogn.famil_school.R;
import com.aloogn.view.common.TitleLayout;

public class HomeworkFamilyDetailsActivity extends AppCompatActivity {

    private TitleLayout mTitleLayout;
    private TextView mTitle;
    private TextView homeworkFamilyDetails_tv_time;
    private TextView homeworkFamilyDetails_tv_homework;
    private String homework;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_family_details);

        mTitleLayout = (TitleLayout) findViewById(R.id.ManualBinding_title);
        mTitle = (TextView) findViewById(R.id.title_text);
        homeworkFamilyDetails_tv_time = (TextView) findViewById(R.id.homeworkFamilyDetails_tv_time);
        homeworkFamilyDetails_tv_homework = (TextView) findViewById(R.id.homeworkFamilyDetails_tv_homework);

        mTitle.setText("作业详情");

        Intent intent = getIntent();
        String values = intent.getStringExtra("values");
        homework = values.substring(1,values.length() - 8);
        time = values.substring(values.length() - 6, values.length() - 1);

        homeworkFamilyDetails_tv_time.setText(time);
        homeworkFamilyDetails_tv_homework.setText(homework);
    }
}

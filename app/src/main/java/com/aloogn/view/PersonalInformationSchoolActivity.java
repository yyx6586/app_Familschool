package com.aloogn.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.aloogn.famil_school.R;
import com.aloogn.util.MyToastUtil;
import com.aloogn.view.common.TitleLayout;

public class PersonalInformationSchoolActivity extends AppCompatActivity {

    private Spinner spinner;
    private TitleLayout mTitleLayout;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information_school);

        spinner = (Spinner) findViewById(R.id.personalInformation_spinner_subject);

        ArrayAdapter adapter = new ArrayAdapter(PersonalInformationSchoolActivity.this,R.layout.spinner_item,
                new String[] {"语文", "数学", "英语", "科学"});
        spinner.setAdapter(adapter);

        mTitleLayout = (TitleLayout)findViewById(R.id.ManualBinding_title);
        mTitle = (TextView)mTitleLayout.findViewById(R.id.title_text);

        mTitle.setText("个人信息");
    }
}

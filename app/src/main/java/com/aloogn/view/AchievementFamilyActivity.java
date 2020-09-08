package com.aloogn.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aloogn.famil_school.R;
import com.aloogn.view.common.TitleLayout;

public class AchievementFamilyActivity extends AppCompatActivity {

    private TitleLayout mTitleLayout;
    private TextView mTitle;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_family);

        mTitleLayout = (TitleLayout)findViewById(R.id.ManualBinding_title);
        mTitle = (TextView)mTitleLayout.findViewById(R.id.title_text);
        linearLayout = (LinearLayout) mTitleLayout.findViewById(R.id.title_LinearLayout);

        mTitle.setText("成绩");
        linearLayout.setVisibility(View.VISIBLE);
    }
}

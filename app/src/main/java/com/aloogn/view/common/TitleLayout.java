package com.aloogn.view.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aloogn.famil_school.R;


/**
 * author : zxl
 * e-mail : loogn.zou@foxmail.com
 * date   : 2020/7/273:22 PM
 * desc   :
 */
public class TitleLayout extends LinearLayout {

    private ImageView mTitleLeftImage;
    private TextView mTitleText;

    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_layout,this);

        mTitleText = (TextView)findViewById(R.id.title_text);

        /*
        添加返回键点击事件
         */
        mTitleLeftImage = (ImageView)findViewById(R.id.title_left_Image);

        mTitleLeftImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
    }
}

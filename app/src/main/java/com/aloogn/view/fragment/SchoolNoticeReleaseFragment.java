package com.aloogn.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.aloogn.famil_school.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolNoticeReleaseFragment extends Fragment {

    private EditText fragmentSchoolNoticeRelease;
    private Button fragmentSchoolNoticeRelease_btn;


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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragmentSchoolNoticeRelease = (EditText) getActivity().findViewById(R.id.fragmentSchoolNoticeRelease);
        fragmentSchoolNoticeRelease_btn = (Button) getActivity().findViewById(R.id.fragmentSchoolNoticeRelease_btn);

        String information = fragmentSchoolNoticeRelease.getText().toString();

        fragmentSchoolNoticeRelease_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });


    }
}

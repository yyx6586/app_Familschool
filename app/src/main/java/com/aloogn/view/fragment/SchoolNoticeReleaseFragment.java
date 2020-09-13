package com.aloogn.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aloogn.famil_school.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolNoticeReleaseFragment extends Fragment {


    public SchoolNoticeReleaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_school_notice_release, container, false);
    }

}

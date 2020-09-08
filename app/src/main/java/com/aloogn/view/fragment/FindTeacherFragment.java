package com.aloogn.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.aloogn.famil_school.R;
import com.aloogn.view.PersonalInformationSchoolActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindTeacherFragment extends Fragment {

    public FindTeacherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_teacher, container, false);
    }

}

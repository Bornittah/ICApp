package com.example.bornittah.icapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bornittah.icapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanApplicationFragment extends Fragment {


    public LoanApplicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loan_application, container, false);
    }

}

package com.icommunicate.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.icommunicate.common.Navigation;


public class BaseFragment extends Fragment {


    public static String TAG = BaseFragment.class.getName();
    public static int CurrentCounter = 0;
    public Navigation navigation;
    public int counter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        counter = getArguments().getInt("count", 0);
        CurrentCounter = counter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getParentFragment() != null && getParentFragment() instanceof Navigation) {
            navigation = (Navigation) getParentFragment();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onDestroy() {
        CurrentCounter = CurrentCounter - 1;
        Log.d(TAG, "onDestroy" + counter);
        super.onDestroy();
    }


}

package com.icommunicate.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.icommunicate.R;
import com.icommunicate.common.Navigation;
import com.icommunicate.fragment.subFragment.FragmentKeypad;

public class HolderKeypadFragment extends Fragment implements Navigation {
    private final static String TAG = HolderKeypadFragment.class.getName();

    FrameLayout holderFrame;

    public HolderKeypadFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.holder_fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        holderFrame = view.findViewById(R.id.holderFrame);


        if (getChildFragmentManager().findFragmentById(R.id.holderFrame) == null) {
            Fragment fragment = FragmentKeypad.newInstance(getContext(), "Keypad", 0);
            getChildFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fragment_slide_left_enter,
                            R.anim.fragment_slide_left_exit)
                    .replace(R.id.holderFrame, fragment)
                    .commitAllowingStateLoss();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.holderFrame);
        fragment.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void showNewCounterFragment(Fragment fragment) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setEnterTransition(new Slide(Gravity.END));
        }

        getChildFragmentManager().beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(R.anim.fragment_slide_left_enter,
                        R.anim.fragment_slide_left_exit,
                        R.anim.fragment_slide_right_enter,
                        R.anim.fragment_slide_right_exit)
                .setReorderingAllowed(true)
                .replace(R.id.holderFrame, fragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}

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
import com.icommunicate.fragment.subFragment.FragmentContacts;

/**
 * A simple {@link Fragment} subclass.
 */
public class HolderContactsFragment extends Fragment implements Navigation {
    private final static String TAG = HolderContactsFragment.class.getName();

    FrameLayout holderFrame;

    public HolderContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.holder_fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        holderFrame = view.findViewById(R.id.holderFrame);


        if (getChildFragmentManager().findFragmentById(R.id.holderFrame) == null) {
            Fragment fragment = FragmentContacts.newInstance(getContext(), "Contacts", 0);
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

        fragment.setEnterTransition(new Slide(Gravity.END));

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

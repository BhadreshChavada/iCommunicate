package com.icommunicate.fragment.subFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.icommunicate.R;
import com.icommunicate.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentPlayAudio extends BaseFragment {

    final public static String TAG = FragmentPlayAudio.class.getName();
    protected View root;
    @BindView(R.id.action_bar_title)
    AppCompatTextView actionBarTitle;
    @BindView(R.id.ic_action_back)
    AppCompatImageView icActionBack;

    public FragmentPlayAudio() {
    }

    public static Fragment newInstance(Context context, String page_name, int count) {
        FragmentPlayAudio f = new FragmentPlayAudio();
        Bundle bundle = new Bundle();
        bundle.putString("page_name", page_name);
        bundle.putInt("count", count);
        f.setArguments(bundle);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_play_audio, container, false);
        ButterKnife.bind(this, root);
        getBundleArguments();
        setUpView();
        return root;
    }

    private void setUpView() {
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getBundleArguments() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.containsKey("page_name")) {
                try {
                    icActionBack.setVisibility(View.VISIBLE);
                    actionBarTitle.setText(bundle.getString("page_name"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @OnClick(R.id.ic_action_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_action_back:
                try {
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}

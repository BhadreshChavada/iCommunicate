//package com.icommunicate.fragment.subFragment;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.os.SystemClock;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Chronometer;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.icommunicate.R;
//import com.icommunicate.fragment.BaseFragment;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class FragmentVoiceCalling extends BaseFragment {
//
//    final public static String TAG = FragmentVoiceCalling.class.getName();
//    protected View root;
//    @BindView(R.id.chronometer)
//    Chronometer chronometer;
//
//    public FragmentVoiceCalling() {
//    }
//
//    public static Fragment newInstance(Context context, String page_name, int count) {
//        FragmentVoiceCalling f = new FragmentVoiceCalling();
//        Bundle bundle = new Bundle();
//        bundle.putString("page_name", page_name);
//        bundle.putInt("count", count);
//        f.setArguments(bundle);
//
//        return f;
//    }
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        root = inflater.inflate(R.layout.activity_voice_duplicate_duplicate, container, false);
//        ButterKnife.bind(this, root);
//        chronometer.setVisibility(View.VISIBLE);
//        chronometer.setBase(SystemClock.elapsedRealtime());
//        chronometer.start();
//        return root;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//    }
//
//
//}

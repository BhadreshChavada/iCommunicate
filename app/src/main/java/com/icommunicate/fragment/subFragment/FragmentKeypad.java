package com.icommunicate.fragment.subFragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.icommunicate.R;
import com.icommunicate.adapterActions.SelectNumberCallbackCallback;
import com.icommunicate.apiCall.responseModels.defult_number.DefultNumberData;
import com.icommunicate.apiCall.responseModels.defult_number.DefultNumberResponse;
import com.icommunicate.common.CommonMethods;
import com.icommunicate.common.dailog.SelectNumberDailog;
import com.icommunicate.common.preferences.PreferenceUtil;
import com.icommunicate.fragment.BaseFragment;
import com.icommunicate.twillio.VoiceActivityDuplicate;
import com.sample.android.padlayout.DialPadKey;
import com.sample.android.padlayout.PadLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class FragmentKeypad extends BaseFragment {

    final public static String TAG = FragmentKeypad.class.getName();
    protected View root;
    @BindView(R.id.formula)
    AppCompatEditText formula;
    @BindView(R.id.contactName)
    TextView contactName;
    @BindView(R.id.display)
    LinearLayout display;
    @BindView(R.id.padLayout)
    PadLayout padLayout;
    @BindView(R.id.call_button)
    LinearLayout callButton;
    @BindView(R.id.button1)
    DialPadKey button1;
    @BindView(R.id.button2)
    DialPadKey button2;
    @BindView(R.id.button3)
    DialPadKey button3;
    @BindView(R.id.button4)
    DialPadKey button4;
    @BindView(R.id.button5)
    DialPadKey button5;
    @BindView(R.id.button6)
    DialPadKey button6;
    @BindView(R.id.button7)
    DialPadKey button7;
    @BindView(R.id.button8)
    DialPadKey button8;
    @BindView(R.id.button9)
    DialPadKey button9;
    @BindView(R.id.button10)
    DialPadKey button10;
    @BindView(R.id.button11)
    DialPadKey button11;
    @BindView(R.id.button12)
    DialPadKey button12;
    @BindView(R.id.row_recent_call_time)
    AppCompatTextView rowRecentCallTime;
    @BindView(R.id.dial_number)
    AppCompatTextView dialNumber;
    @BindView(R.id.dial_arrow)
    LinearLayout dialArrow;
    List<DefultNumberData> defultNumberData = new ArrayList<>();

    public FragmentKeypad() {
    }

    public static Fragment newInstance(Context context, String page_name, int count) {
        FragmentKeypad f = new FragmentKeypad();
        Bundle bundle = new Bundle();
        bundle.putString("page_name", page_name);
        bundle.putInt("count", count);
        f.setArguments(bundle);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dialpad_circle, container, false);
        ButterKnife.bind(this, root);
        getBundleArguments();

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CommonMethods.getValue(formula.getText().toString()).isEmpty()) {
                    Intent calling = new Intent(getContext(), VoiceActivityDuplicate.class);
                    calling.putExtra("phoneNumber", formula.getText().toString());
                    calling.putExtra("Name", getContactName(getContext(), formula.getText().toString()));
                    startActivity(calling);
                } else {
                    Toast.makeText(getActivity(), "Please dial the number.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        DefultNumberResponse response = new Gson().fromJson(PreferenceUtil.defultDailNumber().get(), DefultNumberResponse.class);
        response.getData().forEach(defultNumberData -> defultNumberData.setSelected(false));
        for (int i = 0; i < response.getData().size(); i++) {
            try {
                if (PreferenceUtil.byDefultDailNumber().get().equalsIgnoreCase(response.getData().get(i).getNumber())) {
                    dialNumber.setText(CommonMethods.getValue(response.getData().get(i).getNumber()));
                    response.getData().get(i).setSelected(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        defultNumberData = new ArrayList<>();
        defultNumberData.addAll(response.getData());
        response.getData().forEach(defultNumberData -> defultNumberData.setSelected(false));
        if (!CommonMethods.getValue(PreferenceUtil.byDefultDailNumber().get()).isEmpty()) {
            for (int i = 0; i < defultNumberData.size(); i++) {
                if (CommonMethods.getValue(dialNumber.getText().toString()).equalsIgnoreCase(defultNumberData.get(i).getNumber())) {
                    defultNumberData.get(i).setSelected(true);
                    dialNumber.setText(CommonMethods.getValue(defultNumberData.get(i).getNumber()));

                }
            }
        }
    }

    @OnClick({
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
            R.id.button10,
            R.id.button11,
            R.id.button12
    })
    void numberClick(View v) {
        String number = String.format(
                "%s%s", String.valueOf(formula.getText()), ((DialPadKey) v).
                        getNumberText());


        updateNumber(number);

        if (getContactName(getActivity(), number) != null) {
            contactName.setText(getContactName(getActivity(), number));
        } else {
            contactName.setText("");
        }
    }

    @OnLongClick({
            R.id.button11
    })
    boolean zeroLongClick() {
        updateNumber(String.format("%s%s", String.valueOf(formula.getText()), "+"));
        return true;
    }

    private void updateNumber(String number) {
        if (number.length() > 0) {
            if (display.getVisibility() == View.INVISIBLE) {
                display.setVisibility(View.VISIBLE);
            }
        } else {
            if (display.getVisibility() == View.VISIBLE) {
                display.setVisibility(View.INVISIBLE);
            }
        }
        formula.setText(number);
        if (getContactName(getActivity(), number) != null) {
            contactName.setText(getContactName(getActivity(), number));
        } else {
            contactName.setText("");
        }
    }

    public String getNumber() {
        if (formula != null) {
            return formula.getText().toString();
        }
        return "";
    }

    @OnClick(R.id.backspace_button)
    public void onViewClicked() {
        String mNumber = formula.getText().toString();

        if (!formula.getText().toString().isEmpty()) {
            formula.setText(
                    mNumber.substring(0, mNumber.length() - 1)
            );
        }

        if (getContactName(getActivity(), mNumber) != null) {
            contactName.setText(getContactName(getActivity(), mNumber));
        } else {
            contactName.setText("");
        }
    }

    @OnLongClick({
            R.id.backspace_button
    })
    public void onViewLongClicked() {

        if (!Objects.requireNonNull(formula.getText()).toString().isEmpty()) {
            formula.setText("");
            contactName.setText("");
        }

    }

    @OnClick({R.id.dial_number, R.id.dial_arrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dial_number:
                showNumberDialog();
                break;
            case R.id.dial_arrow:
                showNumberDialog();
                break;
        }
    }

    private void showNumberDialog() {
        SelectNumberDailog.show(defultNumberData, getFragmentManager(), new SelectNumberCallbackCallback() {
            @Override
            public void onClick(View view, int position) {

                dialNumber.setText(defultNumberData.get(position).getNumber());
                defultNumberData.get(position).setSelected(true);
                PreferenceUtil.byDefultDailNumber().set(defultNumberData.get(position).getNumber());
            }

            @Override
            public void onNo() {

            }
        });
    }

    public static String getContactName(Context context, String phoneNumber) {
        String contactName = "";
        try {
            ContentResolver cr = context.getContentResolver();
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
            Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
            if (cursor == null) {
                return null;
            }

            if (cursor.moveToFirst()) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            }

            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

            Log.d("NAme", String.valueOf(contactName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contactName;
    }


}

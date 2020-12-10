package com.icommunicate.fragment.subFragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icommunicate.R;
import com.icommunicate.activity.MessageActivity;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.requestCall.ApiUpdateWorkingHours;
import com.icommunicate.apiCall.requestModels.UpdateWorkingHourRequest;
import com.icommunicate.apiCall.responseModels.FetchMessageResponse;
import com.icommunicate.apiCall.responseModels.LoginData;
import com.icommunicate.apiCall.responseModels.defult_number.DefultNumberResponse;
import com.icommunicate.bean.HoursPojo;
import com.icommunicate.common.CommonMethods;
import com.icommunicate.common.preferences.PreferenceUtil;
import com.icommunicate.fragment.BaseFragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentSettingWorkingHours extends BaseFragment {

    final public static String TAG = FragmentSettingWorkingHours.class.getName();
    protected View root;
    @BindView(R.id.action_bar_title)
    AppCompatTextView actionBarTitle;


    @BindView(R.id.title)
    LinearLayout title;
    final Calendar sundayFromCalendar = Calendar.getInstance();
    final Calendar sundayToCalendar = Calendar.getInstance();

    final Calendar mondayFromCalendar = Calendar.getInstance();
    final Calendar mondayToCalendar = Calendar.getInstance();

    final Calendar tuesdayFromCalendar = Calendar.getInstance();
    final Calendar tuesdayToCalendar = Calendar.getInstance();

    final Calendar wednesdayFromCalendar = Calendar.getInstance();
    final Calendar wednesdayToCalendar = Calendar.getInstance();

    final Calendar thursdayFromCalendar = Calendar.getInstance();
    final Calendar thursdayToCalendar = Calendar.getInstance();

    final Calendar firdayFromCalendar = Calendar.getInstance();
    final Calendar firdayToCalendar = Calendar.getInstance();

    final Calendar saturdayFromCalendar = Calendar.getInstance();
    final Calendar saturdayToCalendar = Calendar.getInstance();
    int count = 0;

    @BindView(R.id.ic_action_back)
    AppCompatImageView icActionBack;
    @BindView(R.id.ic_action_done)
    AppCompatImageView icActionDone;
    @BindView(R.id.monday_select_from_time)
    AppCompatTextView mondaySelectFromTime;
    @BindView(R.id.monday_select_to_time)
    AppCompatTextView mondaySelectToTime;
    @BindView(R.id.tuesday_select_from_time)
    AppCompatTextView tuesdaySelectFromTime;
    @BindView(R.id.tuesday_select_to_time)
    AppCompatTextView tuesdaySelectToTime;
    @BindView(R.id.wednesday_select_from_time)
    AppCompatTextView wednesdaySelectFromTime;
    @BindView(R.id.wednesday_select_to_time)
    AppCompatTextView wednesdaySelectToTime;
    @BindView(R.id.thursday_select_from_time)
    AppCompatTextView thursdaySelectFromTime;
    @BindView(R.id.thursday_select_to_time)
    AppCompatTextView thursdaySelectToTime;
    @BindView(R.id.friday_select_from_time)
    AppCompatTextView fridaySelectFromTime;
    @BindView(R.id.friday_select_to_time)
    AppCompatTextView fridaySelectToTime;
    @BindView(R.id.saturday_select_from_time)
    AppCompatTextView saturdaySelectFromTime;
    @BindView(R.id.saturday_select_to_time)
    AppCompatTextView saturdaySelectToTime;
    @BindView(R.id.sunday_select_from_time)
    AppCompatTextView sundaySelectFromTime;
    @BindView(R.id.sunday_select_to_time)
    AppCompatTextView sundaySelectToTime;
    @BindView(R.id.is_monday)
    AppCompatCheckBox isMonday;
    @BindView(R.id.is_tuesday)
    AppCompatCheckBox isTuesday;
    @BindView(R.id.is_wednesday)
    AppCompatCheckBox isWednesday;
    @BindView(R.id.is_thursday)
    AppCompatCheckBox isThursday;
    @BindView(R.id.is_friday)
    AppCompatCheckBox isFriday;
    @BindView(R.id.is_saturday)
    AppCompatCheckBox isSaturday;
    @BindView(R.id.is_sunday)
    AppCompatCheckBox isSunday;

    public FragmentSettingWorkingHours() {
    }

    public static Fragment newInstance(Context context, String page_name, int count) {
        FragmentSettingWorkingHours f = new FragmentSettingWorkingHours();
        Bundle bundle = new Bundle();
        bundle.putString("page_name", page_name);
        bundle.putInt("count", count);
        f.setArguments(bundle);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_setting_working_hours, container, false);
        ButterKnife.bind(this, root);
        getBundleArguments();
        setUpView();
        return root;
    }

    private void setUpView() {


        if (PreferenceUtil.loginUserData().get() != null && CommonMethods.getValue(PreferenceUtil.loginUserData().get().getWorking_hours()) == null) {
            if (CommonMethods.getValue(PreferenceUtil.loginUserData().get().getWorking_hours()) != null) {
                Type type = new TypeToken<ArrayList<HoursPojo>>() {
                }.getType();
                List<HoursPojo> layerItems = new Gson().fromJson(PreferenceUtil.loginUserData().get().getWorking_hours().toString(), type);
                if (layerItems != null && layerItems.size() > 0) {
                    for (int i = 0; i < layerItems.size(); i++) {
                        HoursPojo hoursPojo = layerItems.get(i);

                        try {
                            switch (i) {
                                case 0:
                                    sundayFromCalendar.setTimeInMillis(Long.parseLong(hoursPojo.getFrom()));
                                    sundayToCalendar.setTimeInMillis(Long.parseLong(hoursPojo.getTo()));
                                    isSunday.setChecked(hoursPojo.getIsDayoff());
                                    setUpTextView(sundayFromCalendar, sundaySelectFromTime);
                                    setUpTextView(sundayToCalendar, sundaySelectToTime);
                                    break;
                                case 1:
                                    mondayFromCalendar.setTimeInMillis(Long.parseLong(hoursPojo.getFrom()));
                                    mondayToCalendar.setTimeInMillis(Long.parseLong(hoursPojo.getTo()));
                                    isMonday.setChecked(hoursPojo.getIsDayoff());
                                    setUpTextView(mondayFromCalendar, mondaySelectFromTime);
                                    setUpTextView(mondayToCalendar, mondaySelectToTime);
                                    break;
                                case 2:
                                    wednesdayFromCalendar.setTimeInMillis(Long.parseLong(hoursPojo.getFrom()));
                                    wednesdayToCalendar.setTimeInMillis(Long.parseLong(hoursPojo.getTo()));
                                    isWednesday.setChecked(hoursPojo.getIsDayoff());
                                    setUpTextView(wednesdayFromCalendar, wednesdaySelectFromTime);
                                    setUpTextView(wednesdayToCalendar, wednesdaySelectToTime);
                                    break;
                                case 3:
                                    tuesdayFromCalendar.setTimeInMillis(Long.parseLong(hoursPojo.getFrom()));
                                    tuesdayToCalendar.setTimeInMillis(Long.parseLong(hoursPojo.getTo()));
                                    isTuesday.setChecked(hoursPojo.getIsDayoff());
                                    setUpTextView(tuesdayFromCalendar, tuesdaySelectFromTime);
                                    setUpTextView(tuesdayToCalendar, tuesdaySelectToTime);
                                    break;
                                case 4:
                                    thursdayFromCalendar.setTimeInMillis(Long.parseLong(hoursPojo.getFrom()));
                                    thursdayToCalendar.setTimeInMillis(Long.parseLong(hoursPojo.getTo()));
                                    isThursday.setChecked(hoursPojo.getIsDayoff());
                                    setUpTextView(thursdayFromCalendar, thursdaySelectFromTime);
                                    setUpTextView(thursdayToCalendar, thursdaySelectToTime);
                                    break;
                                case 5:
                                    firdayFromCalendar.setTimeInMillis(Long.parseLong(hoursPojo.getFrom()));
                                    firdayToCalendar.setTimeInMillis(Long.parseLong(hoursPojo.getTo()));
                                    isFriday.setChecked(hoursPojo.getIsDayoff());
                                    setUpTextView(firdayFromCalendar, fridaySelectFromTime);
                                    setUpTextView(firdayToCalendar, fridaySelectToTime);
                                    break;
                                case 6:
                                    saturdayFromCalendar.setTimeInMillis(Long.parseLong(hoursPojo.getFrom()));
                                    saturdayToCalendar.setTimeInMillis(Long.parseLong(hoursPojo.getTo()));
                                    isSaturday.setChecked(hoursPojo.getIsDayoff());
                                    setUpTextView(saturdayFromCalendar, saturdaySelectFromTime);
                                    setUpTextView(saturdayToCalendar, saturdaySelectToTime);
                                    break;
                            }
                        }catch (Exception e){
                         e.printStackTrace();
                        }

                    }
                }
            }
        }
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
                    icActionDone.setVisibility(View.VISIBLE);
                    icActionBack.setVisibility(View.VISIBLE);
                    actionBarTitle.setText(bundle.getString("page_name"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClick({R.id.monday_select_from_time, R.id.monday_select_to_time, R.id.ic_action_back, R.id.ic_action_done,
            R.id.tuesday_select_from_time, R.id.tuesday_select_to_time, R.id.wednesday_select_from_time, R.id.wednesday_select_to_time,
            R.id.thursday_select_from_time, R.id.thursday_select_to_time, R.id.friday_select_from_time, R.id.friday_select_to_time,
            R.id.saturday_select_from_time, R.id.saturday_select_to_time, R.id.sunday_select_from_time, R.id.sunday_select_to_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.monday_select_from_time:
                showTimeDialog(mondaySelectFromTime, mondayFromCalendar);
                break;
            case R.id.monday_select_to_time:
                showTimeDialog(mondaySelectToTime, mondayToCalendar);
                break;
            case R.id.tuesday_select_from_time:
                showTimeDialog(tuesdaySelectFromTime, tuesdayFromCalendar);
                break;
            case R.id.tuesday_select_to_time:
                showTimeDialog(tuesdaySelectToTime, tuesdayToCalendar);
                break;
            case R.id.wednesday_select_from_time:
                showTimeDialog(wednesdaySelectFromTime, wednesdayFromCalendar);
                break;
            case R.id.wednesday_select_to_time:
                showTimeDialog(wednesdaySelectToTime, wednesdayToCalendar);
                break;
            case R.id.thursday_select_from_time:
                showTimeDialog(thursdaySelectFromTime, thursdayFromCalendar);
                break;
            case R.id.thursday_select_to_time:
                showTimeDialog(thursdaySelectToTime, thursdayToCalendar);
                break;
            case R.id.friday_select_from_time:
                showTimeDialog(fridaySelectFromTime, firdayFromCalendar);
                break;
            case R.id.friday_select_to_time:
                showTimeDialog(fridaySelectToTime, firdayToCalendar);
                break;
            case R.id.saturday_select_from_time:
                showTimeDialog(saturdaySelectFromTime, saturdayFromCalendar);
                break;
            case R.id.saturday_select_to_time:
                showTimeDialog(saturdaySelectToTime, saturdayToCalendar);
                break;
            case R.id.sunday_select_from_time:
                showTimeDialog(sundaySelectFromTime, sundayFromCalendar);
                break;
            case R.id.sunday_select_to_time:
                showTimeDialog(sundaySelectToTime, sundayToCalendar);
                break;
            case R.id.ic_action_back:
                try {
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ic_action_done:
                if (mondaySelectFromTime.getText().toString().equalsIgnoreCase("Select Time")) {
                    displayMessage("Please select From time.");
                    return;
                } else if (mondaySelectToTime.getText().toString().equalsIgnoreCase("Select Time")) {
                    displayMessage("please select To time.");
                    return;
                }

                List<HoursPojo> hoursPojos = new ArrayList<>();
                hoursPojos.add(new HoursPojo("sunday", CommonMethods.getValue(sundayFromCalendar.getTimeInMillis() + ""), CommonMethods.getValue(sundayToCalendar.getTimeInMillis() + ""), isSunday.isChecked()));
                hoursPojos.add(new HoursPojo("monday", CommonMethods.getValue(mondayFromCalendar.getTimeInMillis() + ""), CommonMethods.getValue(mondayToCalendar.getTimeInMillis() + ""), isMonday.isChecked()));
                hoursPojos.add(new HoursPojo("wednesday", CommonMethods.getValue(wednesdayFromCalendar.getTimeInMillis() + ""), CommonMethods.getValue(wednesdayToCalendar.getTimeInMillis() + ""), isWednesday.isChecked()));
                hoursPojos.add(new HoursPojo("tuesday", CommonMethods.getValue(tuesdayFromCalendar.getTimeInMillis() + ""), CommonMethods.getValue(tuesdayToCalendar.getTimeInMillis() + ""), isTuesday.isChecked()));
                hoursPojos.add(new HoursPojo("thursday", CommonMethods.getValue(thursdayFromCalendar.getTimeInMillis() + ""), CommonMethods.getValue(thursdayToCalendar.getTimeInMillis() + ""), isThursday.isChecked()));
                hoursPojos.add(new HoursPojo("friday", CommonMethods.getValue(firdayFromCalendar.getTimeInMillis() + ""), CommonMethods.getValue(firdayToCalendar.getTimeInMillis() + ""), isFriday.isChecked()));
                hoursPojos.add(new HoursPojo("saturday", CommonMethods.getValue(saturdayFromCalendar.getTimeInMillis() + ""), CommonMethods.getValue(saturdayToCalendar.getTimeInMillis() + ""), isSaturday.isChecked()));
                callApiWorkingHours(new Gson().toJson(hoursPojos));
                break;
        }
    }

    private void callApiWorkingHours(String workingHours) {
        UpdateWorkingHourRequest updateWorkingHourRequest = new UpdateWorkingHourRequest();
        updateWorkingHourRequest.setWorkingHours(workingHours);
        updateWorkingHourRequest.setUserId(PreferenceUtil.loginUserData().get().getId());

        ApiUpdateWorkingHours apiUpdateWorkingHours = new ApiUpdateWorkingHours(getActivity(), new IResult() {
            @Override
            public void notifySuccess(String requestType, Object response) {
                if (response instanceof DefultNumberResponse) {
                    DefultNumberResponse numberResponse = (DefultNumberResponse) response;
                    if (!numberResponse.isError()) {
                        Toast.makeText(getActivity(), "" + numberResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        LoginData loginData = PreferenceUtil.loginUserData().get();
                        loginData.setWorking_hours(Integer.valueOf(workingHours.toString()));
                        PreferenceUtil.loginUserData().set(loginData);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 1000);
                    } else {
                        Toast.makeText(getActivity(), "" + numberResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void notifyNetworkSuccess(String requestType) {

            }
        });
        apiUpdateWorkingHours.execute(updateWorkingHourRequest);
    }

    public void displayMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    private void showTimeDialog(AppCompatTextView selectToTime, Calendar myCalendar) {

        TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                setUpTextView(myCalendar, selectToTime);
            }
        };
        new TimePickerDialog(getActivity(), R.style.TimePickerTheme, mTimeSetListener, myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), false).show();
    }

    private void setUpTextView(Calendar myCalendar, AppCompatTextView selectToTime) {
        String am_pm = "";
        if (myCalendar.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (myCalendar.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";
        String strHrsToShow = (myCalendar.get(Calendar.HOUR) == 0) ? "12" : myCalendar.get(Calendar.HOUR) + "";
        //UIHelper.showLongToastInCenter(context, strHrsToShow + ":" + myCalendar.get(Calendar.MINUTE) + " " + am_pm);
        selectToTime.setText(strHrsToShow + ":" + myCalendar.get(Calendar.MINUTE) + " " + am_pm);
    }
}

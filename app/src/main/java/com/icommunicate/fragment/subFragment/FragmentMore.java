package com.icommunicate.fragment.subFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.brandongogetap.stickyheaders.StickyLayoutManager;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.icommunicate.R;
import com.icommunicate.activity.LoginActivity;
import com.icommunicate.adapter.MoreOptionAdapter;
import com.icommunicate.adapter.SettingsAdepter;
import com.icommunicate.adapterActions.SettingListner;
import com.icommunicate.apiCall.ApiConstant;
import com.icommunicate.bean.SettingBean;
import com.icommunicate.bean.SettingHeaderItem;
import com.icommunicate.common.IntentUtils;
import com.icommunicate.common.dailog.ConfirmationDialog;
import com.icommunicate.common.preferences.PreferenceUtil;
import com.icommunicate.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentMore extends BaseFragment {

    final public static String TAG = FragmentMore.class.getName();
    MoreOptionAdapter adapter;
    protected View root;
    @BindView(R.id.action_bar_title)
    AppCompatTextView actionBarTitle;
//    @BindView(R.id.more_recycle)
//    RecyclerView moreRecycle;

    @BindView(R.id.recyclerview_setting)
    RecyclerView recyclerviewSetting;
    List<SettingBean> settingBeans;
    SettingsAdepter settingsAdepter;

    public FragmentMore() {
    }

    public static Fragment newInstance(Context context, String page_name, int count) {
        FragmentMore f = new FragmentMore();
        Bundle bundle = new Bundle();
        bundle.putString("page_name", page_name);
        bundle.putInt("count", count);
        f.setArguments(bundle);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, root);
        getBundleArguments();
        getBundleArguments();
        setUpArrayList();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
//        setUpView();
    }

  /*  private void setUpView(){
        adapter = new MoreOptionAdapter(getContext(), generateSetting(), new MoreOptionListener() {
            @Override
            public void selected(MoreOptionModal moreOptionModal) {
                Fragment fragment = null;
                switch (moreOptionModal.getModule_id()) {
                     case 1:
                        openLogoutDialog();
                        break;
                }

            }
        });

        moreRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        moreRecycle.setItemAnimator(new DefaultItemAnimator());
        moreRecycle.setAdapter(adapter);
    }*/


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getBundleArguments() {
        try {
            actionBarTitle.setText("More");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public static List<MoreOptionModal> generateSetting() {
        List<MoreOptionModal> detailObject = null;
        try {

            detailObject = new ArrayList<>();
            detailObject.add(new MoreOptionModal(1, "Log Out"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detailObject;
    }*/


    private void setUpArrayList() {
        settingBeans = new ArrayList<>();
//        settingBeans.add(new SettingBean("0", "Listen to Recorded Calls", "Phone Settings", R.drawable.ic_play_button));
        settingBeans.add(new SettingBean("1", "Default Dial Numbers", "Phone Settings", R.drawable.ic_defualt_dial));
        settingBeans.add(new SettingBean("2", "Ringtone", "Phone Settings", R.drawable.ic_ringtone));

        settingBeans.add(new SettingBean("3", "Allow Notifications", "App Settings", R.drawable.ic_notification));
        settingBeans.add(new SettingBean("4", "Working Hours", "App Settings", R.drawable.ic_working_hours));
        settingBeans.add(new SettingBean("5", "Change Password", "App Settings", R.drawable.ic_change_pwd));
        settingBeans.add(new SettingBean("6", "Logout", "App Settings", R.drawable.ic_icon_logout));


        settingBeans.add(new SettingBean("7", "Buy New Number", "Users", R.drawable.ic_buy_number));
        settingBeans.add(new SettingBean("8", "Buy Other Services", "Users", R.drawable.ic_info));
        settingBeans.add(new SettingBean("9", "Refer Friend Earn Credit", "Users", R.drawable.ic_refer));

        settingBeans.add(new SettingBean("10", "About Us", "About iCommunicate", R.drawable.ic_info));
        settingBeans.add(new SettingBean("11", "Terms And Conditions", "About iCommunicate", R.drawable.ic_terms_and_conditions));
        settingBeans.add(new SettingBean("12", "Privacy Policy", "About iCommunicate", R.drawable.ic_privacy));
        settingBeans.add(new SettingBean("13", "GDPR Policy", "About iCommunicate", R.drawable.ic_info));
        settingBeans.add(new SettingBean("14", "Contact Us", "About iCommunicate", R.drawable.ic_contact_us));
        SetUpSettingAdapter(settingBeans);

    }

    private void SetUpSettingAdapter(List<SettingBean> settingBeans) {
        if (settingBeans != null && settingBeans.size() > 0) {

            settingsAdepter = new SettingsAdepter(getActivity(), getChatHistoryWithHeaders(settingBeans), new SettingListner() {

                @Override
                public void settingViewClick(SettingBean bean) {
                    switch (bean.getName()) {
                        case "Listen to Recorded Calls":
                            navigation.showNewCounterFragment(FragmentSettingListenToRecordCall.newInstance(getActivity(), "Listen to Recorded Calls", counter + 1));
                            break;
                        case "Default Dial Numbers":
                            navigation.showNewCounterFragment(FragmentSettingDefultDialNumber.newInstance(getActivity(), "Default Dial Numbers", counter + 1));
                            break;
                        case "Ringtone":
                            startActivityForResult(new Intent(Settings.ACTION_SOUND_SETTINGS), 0);
                            break;
                        case "Allow Notifications":
                            break;
                        case "Working Hours":
                            navigation.showNewCounterFragment(FragmentSettingWorkingHours.newInstance(getActivity(), "Working Hours", counter + 1));
                            break;
                        case "Change Password":
                            navigation.showNewCounterFragment(FragmentChangePassword.newInstance(getActivity(), "Change Password", counter + 1));
                            break;
                        case "Logout":
                            openLogoutDialog();
                            break;
                        case "Buy New Number":
                            openLink(ApiConstant.BASE_URL + "/icoomunicate/pages/buy_new_number");
                            break;
                        case "Buy Other Services":
                            openLink(ApiConstant.BASE_URL + "/icoomunicate/pages/buy_other_service");
                            break;
                        case "Refer Friend Earn Credit":
                            referToFriend();
                            break;
                        case "About Us":
                            openLink(ApiConstant.BASE_URL + "/icoomunicate/pages/about_us");
                            break;
                        case "Terms And Conditions":
                            openLink(ApiConstant.BASE_URL + "/icoomunicate/pages/term_condition");
                            break;
                        case "Privacy Policy":
                            openLink(ApiConstant.BASE_URL + "/icoomunicate/pages/privacy_policy");
                            break;
                        case "GDPR Policy":
                            openLink(ApiConstant.BASE_URL + "/icoomunicate/pages/com_req_policy");
                            break;
                        case "Contact Us":
                            openEmail();
                            break;
                    }

                }
            });
            StickyLayoutManager layoutManager = new StickyLayoutManager(getActivity(), settingsAdepter);
            layoutManager.elevateHeaders(true);
            recyclerviewSetting.setLayoutManager(layoutManager);
            recyclerviewSetting.setItemAnimator(new DefaultItemAnimator());
            recyclerviewSetting.setNestedScrollingEnabled(false);

            recyclerviewSetting.setAdapter(settingsAdepter);

        }
    }

    private void referToFriend() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        String shareBody = "To Refer our website " + getResources().getString(R.string.website_link);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent, "Refer To Friend"));
    }


    private void openEmail() {


        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:iCommunicate@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ICommunicate");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "This is our website show more detail hear." + getActivity().getResources().getString(R.string.website_link));

        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void openLink(String websiteLink) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteLink));
        startActivity(intent);
    }

    public ArrayList<SettingBean> getChatHistoryWithHeaders(List<SettingBean> datas) {
        ArrayList<String> HeadersData = new ArrayList<>();
        ListMultimap<String, SettingBean> multimap = ArrayListMultimap.create();
        for (int i = 0; i < datas.size(); i++) {
            HeadersData.add(datas.get(i).getHeaderTitle());
            multimap.put(datas.get(i).getHeaderTitle(), datas.get(i));
        }

        ArrayList<SettingBean> dataComplete = new ArrayList<>();
        ArrayList<String> hearders = new ArrayList<String>(new LinkedHashSet<String>(HeadersData));
        for (int i = 0; i < hearders.size(); i++) {
            dataComplete.add(getSettingASHeader(hearders.get(i)));
            dataComplete.addAll(multimap.get(hearders.get(i)));

        }
        return dataComplete;
    }

    public SettingHeaderItem getSettingASHeader(String date) {
        return new SettingHeaderItem("100022", null, date);
    }


    private void openLogoutDialog() {
        ConfirmationDialog.show(getContext().getResources().getString(R.string.txt_logout), getFragmentManager(), "Cancel", "Confirm", new ConfirmationDialog.ConfirmationDialogCallback() {
            @Override
            public void onYes() {
                PreferenceUtil.isUserLoggedIn().set(false);
                IntentUtils.redirectWithFinishTo(getContext(), LoginActivity.class);
            }

            @Override
            public void onNo() {

            }
        });
    }


}

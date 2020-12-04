package com.icommunicate.fragment.subFragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.brandongogetap.stickyheaders.StickyLayoutManager;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icommunicate.R;
import com.icommunicate.activity.ContactDetailActivity;
import com.icommunicate.activity.MessageActivity;
import com.icommunicate.adapter.ContactAdepter;
import com.icommunicate.adapterActions.ContactListner;
import com.icommunicate.apiCall.ApiConstant;
import com.icommunicate.apiCall.IResult;
import com.icommunicate.apiCall.requestCall.ApiDefultNumber;
import com.icommunicate.apiCall.requestModels.DefultNumberRequest;
import com.icommunicate.apiCall.responseModels.defult_number.DefultNumberData;
import com.icommunicate.apiCall.responseModels.defult_number.DefultNumberResponse;
import com.icommunicate.bean.ContactBean;
import com.icommunicate.bean.ContactHeaderItem;
import com.icommunicate.common.CommonMethods;
import com.icommunicate.common.FetchContacts;
import com.icommunicate.common.IntentUtils;
import com.icommunicate.common.preferences.PreferenceUtil;
import com.icommunicate.common.swipeRecyclerview.SwipeHelper;
import com.icommunicate.fragment.BaseFragment;
import com.icommunicate.twillio.VoiceActivityDuplicate;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import segmented_control.widget.custom.android.com.segmentedcontrol.SegmentedControl;
import segmented_control.widget.custom.android.com.segmentedcontrol.item_row_column.SegmentViewHolder;
import segmented_control.widget.custom.android.com.segmentedcontrol.listeners.OnSegmentClickListener;

import static android.app.Activity.RESULT_OK;

public class FragmentContacts extends BaseFragment {

    //https://gist.github.com/srayhunter/47ab2816b01f0b00b79150150feb2eb2
    //https://github.com/SimpleMobileTools/Simple-Contacts
    final public static String TAG = FragmentContacts.class.getName();
    protected View root;
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.action_bar_title)
    AppCompatTextView actionBarTitle;
    ContactAdepter contactAdepter;
    @BindView(R.id.custom_search_edttext)
    AppCompatEditText edtSerch;
    String searchString = "";
    List<ContactBean> contactBeans;
    @BindView(R.id.recyclerview_contact)
    IndexFastScrollRecyclerView recyclerviewContact;
    @BindView(R.id.segmented_control)
    SegmentedControl segmentedControl;
    @BindView(R.id.btn_add_contact)
    AppCompatImageView btnAddContact;
    @BindView(R.id.txtNoData)
    AppCompatTextView txtNoData;

    public FragmentContacts() {
    }

    public static Fragment newInstance(Context context, String page_name, int count) {
        FragmentContacts f = new FragmentContacts();
        Bundle bundle = new Bundle();
        bundle.putString("page_name", page_name);
        bundle.putInt("count", count);
        f.setArguments(bundle);

        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, root);
        getBundleArguments();
        setUpSearch();
        swipe_refresh = (SwipeRefreshLayout)root.findViewById(R.id.swipe_container);



        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Loading more data..
                setUpAllContact();
            }
        });

        segmentedControl.addOnSegmentClickListener(new OnSegmentClickListener() {
            @Override
            public void onSegmentClick(SegmentViewHolder segmentViewHolder) {
                if (segmentViewHolder.getColumn() == 0) {
                    setUpAllContact();
                } else if (segmentViewHolder.getColumn() == 1) {
                    getFavoriteContacts();
                }
            }
        });
        segmentedControl.setSelectedSegment(0);
        return root;
    }

    private void setUpSearch() {
        edtSerch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchString = s.toString();
                /*if (isFilterAvailable) {
                    // SearchInLocal();
                    adapter.getFilter().filter(s.toString());
                } else {
                    if (s.toString().length() > 2) {
                        setSearchStatus(true);
                    } else {
                        setSearchStatus(false);
                    }
                }*/
                if (contactAdepter != null && contactAdepter.getFilter() != null) {
                    contactAdepter.getFilter().filter(s.toString());
                }
            }
        });
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
                    actionBarTitle.setText(bundle.getString("page_name"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setUpAllContact() {
        Dexter.withActivity(getActivity()).withPermissions(
                Manifest.permission.READ_CONTACTS
                , Manifest.permission.WRITE_CONTACTS
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    seuUpContactView();
                } else {
                    Toast.makeText(getActivity(), "Missing Permission", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
            }
        }).check();
    }

    private void seuUpContactView() {
        contactBeans = new ArrayList<>();


       /* contactBeans.add(new ContactBean("Adamas", ""));
        contactBeans.add(new ContactBean("Brman", ""));
        contactBeans.add(new ContactBean("Cbdul", ""));
        contactBeans.add(new ContactBean("Dak", ""));
        contactBeans.add(new ContactBean("Eadshah", ""));
        contactBeans.add(new ContactBean("Fddy", ""));
        contactBeans.add(new ContactBean("Gddy", ""));
        contactBeans.add(new ContactBean("Hddy", ""));
        contactBeans.add(new ContactBean("Iddy", ""));
        contactBeans.add(new ContactBean("Jddy", ""));
        contactBeans.add(new ContactBean("Kddy", ""));
        contactBeans.add(new ContactBean("Lddy", ""));
        contactBeans.add(new ContactBean("Mddy", ""));
        contactBeans.add(new ContactBean("Nddy", ""));*/

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        ArrayList<ContactBean> contactList = new Gson().fromJson(sharedpreferences.getString("Contact", null), new TypeToken<ArrayList<ContactBean>>() {
        }.getType());
        if (contactList != null) {
            contactBeans.addAll(contactList);
            Collections.sort(contactBeans, new Comparator<ContactBean>() {
                @Override
                public int compare(ContactBean s1, ContactBean s2) {
                    return s1.getName().compareToIgnoreCase(s2.getName());
                }
            });
            swipe_refresh.setRefreshing(false);
            setContactAdapter(contactBeans);


        } else {
            final ProgressDialog dialog = CommonMethods.showProgressBar(getActivity());
            dialog.show();
            new FetchContacts(getActivity(), new FetchContacts.OnContactFetchListener() {
                @Override
                public void onContactFetch(List<ContactBean> contacts) {
                    if (contacts != null) {
                        Collections.sort(contacts, new Comparator<ContactBean>() {
                            @Override
                            public int compare(ContactBean o1, ContactBean o2) {
                                return o1.getName().compareToIgnoreCase(o2.getName()); // To compare string values
                            }
                        });
                        contactBeans.addAll(contacts);
                    }

                    dialog.dismiss();
                    setContactAdapter(contactBeans);
                }
            }).execute();

        }
    }


    private void setContactAdapter(List<ContactBean> contactBeans) {
        if (contactBeans != null && contactBeans.size() > 0) {
            recyclerviewContact.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);

            contactAdepter = new ContactAdepter(getActivity(), getChatHistoryWithHeaders(contactBeans), new ContactListner() {

                @Override
                public void contactViewClick(ContactBean bean) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ApiConstant.CONTACT, bean);
                    IntentUtils.redirectToWithBundle(getContext(), ContactDetailActivity.class, bundle);
                }

                @Override
                public void haderClick(View v, int position) {

                }
            });
            StickyLayoutManager layoutManager = new StickyLayoutManager(getActivity(), contactAdepter);
            layoutManager.elevateHeaders(true);
            recyclerviewContact.setLayoutManager(layoutManager);
            recyclerviewContact.setItemAnimator(new DefaultItemAnimator());
            recyclerviewContact.setNestedScrollingEnabled(false);
            recyclerviewContact.setIndexBarTextColor(R.color.orange);
            recyclerviewContact.setIndexBarStrokeVisibility(false);
            recyclerviewContact.setIndexBarTransparentValue((float) 0.0);

            recyclerviewContact.setAdapter(contactAdepter);
            setUpSwipeFunctionality(getChatHistoryWithHeaders(contactBeans));

        } else {
            recyclerviewContact.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }
        callApiDefultDialNumber();
    }

    private void setUpSwipeFunctionality(ArrayList<ContactBean> chatHistoryWithHeaders) {
        SwipeHelper swipeHelper = new SwipeHelper(getActivity()) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                if (viewHolder.getItemViewType() == 2) {
                    underlayButtons.add(new UnderlayButton(
                            "Call",
                            0,
                            Color.parseColor("#FF9502"),
                            new UnderlayButtonClickListener() {
                                @Override
                                public void onClick(int pos) {
                                    Intent calling = new Intent(getContext(), VoiceActivityDuplicate.class);
                                    calling.putExtra("Name", chatHistoryWithHeaders.get(pos).getName());
                                    calling.putExtra("phoneNumber", chatHistoryWithHeaders.get(pos).getPhone_number());
                                    startActivity(calling);
                                }
                            }
                    ));
                    underlayButtons.add(new UnderlayButton(
                            "Message",
                            0,
                            Color.parseColor("#C7C7CB"),
                            new UnderlayButtonClickListener() {
                                @Override
                                public void onClick(int pos) {
                                    Intent intent = new Intent(getContext(), MessageActivity.class);
                                    intent.putExtra("Name", chatHistoryWithHeaders.get(pos).getName());
                                    intent.putExtra("phoneNumber", chatHistoryWithHeaders.get(pos).getPhone_number());
                                    startActivity(intent);
                                    getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                }
                            }
                    ));
                }

            }
        };
        swipeHelper.attachToRecyclerView(recyclerviewContact);
    }

    private void callApiDefultDialNumber() {
        DefultNumberRequest defultNumberRequest = new DefultNumberRequest();
        defultNumberRequest.setUserId(PreferenceUtil.loginUserData().get().getId());
        ApiDefultNumber apiDefultNumber = new ApiDefultNumber(getActivity(), new IResult() {
            @Override
            public void notifySuccess(String requestType, Object response) {
                DefultNumberResponse defaultResponse = (DefultNumberResponse) response;
                if (defaultResponse.isError()) {
                    Toast.makeText(getActivity(), "" + defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    if (defaultResponse.getData() != null && defaultResponse.getData().size() > 0) {
                        List<DefultNumberData> defultNumberData = new ArrayList<>();
                        defaultResponse.getData().forEach(data -> data.setSelected(false));
                        for (int i = 0; i < defaultResponse.getData().size(); i++) {
                            if (defaultResponse.getData().get(i).getJsonMemberDefault().equalsIgnoreCase("1") || defaultResponse.getData().get(i).getNumber().startsWith("+44")) {
                                defaultResponse.getData().get(i).setSelected(true);
                                PreferenceUtil.byDefultDailNumber().set(defaultResponse.getData().get(i).getNumber());
                            }
                            defultNumberData.add(defaultResponse.getData().get(i));
                        }
                        PreferenceUtil.defultDailNumber().set(new Gson().toJson(defaultResponse));
                    }
                }
            }

            @Override
            public void notifyNetworkSuccess(String requestType) {

            }
        });
        apiDefultNumber.execute(defultNumberRequest);
    }

    public ArrayList<ContactBean> getChatHistoryWithHeaders(List<ContactBean> datas) {
        ArrayList<String> HeadersData = new ArrayList<>();
        ListMultimap<String, ContactBean> multimap = ArrayListMultimap.create();
        for (int i = 0; i < datas.size(); i++) {
            HeadersData.add(datas.get(i).getName().charAt(0) + "");
            multimap.put(datas.get(i).getName().charAt(0) + "", datas.get(i));
        }

        ArrayList<ContactBean> dataComplete = new ArrayList<>();
        ArrayList<String> hearders = new ArrayList<String>(new LinkedHashSet<String>(HeadersData));
        for (int i = 0; i < hearders.size(); i++) {
            dataComplete.add(getChatASHeader(hearders.get(i)));
            dataComplete.addAll(multimap.get(hearders.get(i)));

        }
        return dataComplete;
    }

    public ContactHeaderItem getChatASHeader(String date) {
        return new ContactHeaderItem("100022", null, date);
    }

    @OnClick(R.id.btn_add_contact)
    public void onViewClicked() {
        openContactForm();
    }

    private void openContactForm() {


        if (segmentedControl.getLastSelectedAbsolutePosition() == 0) {
            Intent i = new Intent(Intent.ACTION_INSERT, Uri.parse("content://com.android.contacts/contacts"));
            i.setType(ContactsContract.Contacts.CONTENT_TYPE);
            if (Build.VERSION.SDK_INT > 14)
                i.putExtra("finishActivityOnSaveCompleted", true); // Fix for 4.0.3 +
            startActivityForResult(i, 1);
        } else if (segmentedControl.getLastSelectedAbsolutePosition() == 1) {
            segmentedControl.setSelectedSegment(0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                ContentResolver cr = getActivity().getContentResolver();
                Cursor cursor = cr.query(Objects.requireNonNull(data.getData()), null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {//Has phoneNumber
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                        while (pCur != null && pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Log.v("SteveMoretz", "NAME : " + name + " phoneNo : " + phoneNo);
                        }
                        if (pCur != null) {
                            pCur.close();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "User canceled adding contacts", Toast.LENGTH_SHORT).show();
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void getFavoriteContacts() {
        Dexter.withActivity(getActivity()).withPermissions(
                Manifest.permission.READ_CONTACTS
                , Manifest.permission.WRITE_CONTACTS
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    setUpFavrate();
                } else {
                    Toast.makeText(getActivity(), "Missing Permission", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
            }
        }).check();
    }

    private void setUpFavrate() {
        contactBeans = new ArrayList<>();
        ContentResolver cr = getActivity().getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, "starred=?",
                new String[]{"1"}, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    int dummyId = 0;
                    while (pCur.moveToNext()) {
                        String contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactNumber = contactNumber.replace(" ", "");
                        contactNumber = contactNumber.replace("-", "");
                        contactNumber = contactNumber.replace("(", "");
                        contactNumber = contactNumber.replace(")", "");
                        dummyId++;
                        String contactId = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                        String lookupId = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY));


                        Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                        List<String> emailList = new ArrayList<>();
                        while (emailCur.moveToNext()) {
                            emailList.add(emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                        }
                        emailCur.close();


                        contactBeans.add(new ContactBean(dummyId + "", contactName, contactNumber, "1", emailList, contactId, lookupId));
                        break;
                    }
                    pCur.close();
                }

            } while (cursor.moveToNext());
        }
        setContactAdapter(contactBeans);
    }


}

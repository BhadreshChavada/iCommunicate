package com.icommunicate.activity;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.icommunicate.R;
import com.icommunicate.apiCall.ApiConstant;
import com.icommunicate.bean.ContactBean;
import com.icommunicate.bean.ContactDetailBean;
import com.icommunicate.common.CommonMethods;
import com.icommunicate.common.IntentUtils;
import com.icommunicate.twillio.VoiceActivityDuplicate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactDetailActivity extends AppCompatActivity {

    @BindView(R.id.action_bar_title)
    AppCompatTextView actionBarTitle;
    @BindView(R.id.action_bar_back)
    LinearLayout actionBarBack;
    @BindView(R.id.contact_detail_lnr)
    LinearLayout contactDetailLnr;
    @BindView(R.id.email_detail_lnr)
    LinearLayout emailDetailLnr;
    @BindView(R.id.address_detail_lnr)
    LinearLayout addressDetailLnr;
    @BindView(R.id.action_bar_edit)
    LinearLayout actionBarEdit;
    @BindView(R.id.work_place_detail_lnr)
    LinearLayout workPlaceDetailLnr;
    @BindView(R.id.btn_share_contact)
    AppCompatButton btnShareContact;
    @BindView(R.id.btn_favorite_contact)
    AppCompatButton favoriteContact;
    ContactBean bean;
    @BindView(R.id.contactName)
    AppCompatTextView contactName;
    @BindView(R.id.txtCall)
    TextView call;
    @BindView(R.id.txtMessage)
    TextView message;
    @BindView(R.id.txtEmail)
    TextView email;
    @BindView(R.id.txtLocation)
    TextView location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contant_detail);
        ButterKnife.bind(this);
        setContactDetail();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBarEdit.setVisibility(View.GONE);
        actionBarTitle.setText("Contact Detail");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentUtils.finishActivity(ContactDetailActivity.this);
    }


    ContactBean contactBean;

    private void setContactDetail() {
        if (getIntent().getParcelableExtra(ApiConstant.CONTACT) != null) {
            contactBean = getIntent().getParcelableExtra(ApiConstant.CONTACT);
            contactName.setText(CommonMethods.getValue(contactBean.getName()));

            ArrayList<ContactDetailBean> contactDetailBeanArrayList = new ArrayList<>();
//            for (int i = 0; i < contactBean.getNumbers().size(); i++) {
            contactDetailBeanArrayList.add(new ContactDetailBean(contactBean.getPhone_number(), "WORK", R.drawable.ic_call_black, R.drawable.ic_message_black));
//            }

            ArrayList<ContactDetailBean> emailDetailBeanArrayList = new ArrayList<>();
            for (int i = 0; i < contactBean.getEmail().size(); i++) {
                email.setVisibility(View.VISIBLE);
                emailDetailBeanArrayList.add(new ContactDetailBean(contactBean.getEmail().get(i), "WORK", R.drawable.ic_email_black, 0));
            }
            ArrayList<ContactDetailBean> addDetailBeanArrayList = new ArrayList<>();
            addDetailBeanArrayList.add(new ContactDetailBean("Not Found", "WORK", R.drawable.ic_location_on_black, R.drawable.ic_directions_black));

            ArrayList<ContactDetailBean> companyBeanArrayList = new ArrayList<>();
            companyBeanArrayList.add(new ContactDetailBean("iCommunicate", "Not Found", R.drawable.ic_company_black, R.drawable.ic_directions_black));

            addressDetailLnr.setVisibility(View.GONE);
            workPlaceDetailLnr.setVisibility(View.GONE);

            for (int i = 0; i < contactDetailBeanArrayList.size(); i++) {
                contactDetailLnr.addView(dynamicAddLinearLayout(contactDetailBeanArrayList.get(i)));
            }


            for (int i = 0; i < emailDetailBeanArrayList.size(); i++) {
                emailDetailLnr.addView(dynamicAddLinearLayout(emailDetailBeanArrayList.get(i)));
            }

            for (int i = 0; i < addDetailBeanArrayList.size(); i++) {
                addressDetailLnr.addView(dynamicAddLinearLayout(addDetailBeanArrayList.get(i)));
            }

            for (int i = 0; i < companyBeanArrayList.size(); i++) {
                workPlaceDetailLnr.addView(dynamicAddLinearLayout(companyBeanArrayList.get(i)));
            }
        }

    }

    public View dynamicAddLinearLayout(ContactDetailBean contactDetailBean) {
        View dynamicView;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dynamicView = inflater.inflate(R.layout.row_default_detail_cell, null);

        AppCompatTextView row_detail_title = (AppCompatTextView) dynamicView.findViewById(R.id.row_detail_title);
        AppCompatTextView row_detail_value = (AppCompatTextView) dynamicView.findViewById(R.id.row_detail_value);
        ImageView field_icon_head = (ImageView) dynamicView.findViewById(R.id.field_icon_head);
        ImageView field_icon_tail = (ImageView) dynamicView.findViewById(R.id.field_icon_tail);

        row_detail_title.setText(contactDetailBean.getTitle());
        row_detail_value.setText(contactDetailBean.getValue());

        field_icon_head.setVisibility(View.GONE);
        field_icon_tail.setVisibility(View.GONE);

//        if (contactDetailBean.getHead_image() != 0) {
//            field_icon_head.setImageResource(contactDetailBean.getHead_image());
//        }
//        if (contactDetailBean.getTail_image() != 0) {
//            field_icon_tail.setImageResource(contactDetailBean.getTail_image());
//        }

        return dynamicView;
    }

    @OnClick(R.id.action_bar_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @OnClick(R.id.btn_share_contact)
    public void onShareClicked() {

        String lookupKey2 = contactBean.getLookupId();
        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey2);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(ContactsContract.Contacts.CONTENT_VCARD_TYPE);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_SUBJECT, contactBean.getName()); // put the name of the contact here
        startActivity(intent);
    }

    @OnClick(R.id.txtEmail)
    public void emailClicked() {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + contactBean.getEmail().get(0)));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Mail application not found", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.txtCall)
    public void callClicked() {

        Intent calling = new Intent(this, VoiceActivityDuplicate.class);
        calling.putExtra("phoneNumber", contactBean.getPhone_number());
        startActivity(calling);
    }

    @OnClick(R.id.txtMessage)
    public void messageClicked() {

        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("phoneNumber", contactBean.getPhone_number());

        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @OnClick(R.id.txtLocation)
    public void locationClicked() {
//        String map = "http://maps.google.co.in/maps?q=" + contactBean.get;
//
//        String uri = String.format(Locale.ENGLISH, map);
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//        startActivity(intent);
    }

    @OnClick(R.id.btn_favorite_contact)
    public void setFavoriteContact() {

        ContentValues values = new ContentValues();
        values.put(ContactsContract.Contacts.STARRED, 1);
        getContentResolver().update(ContactsContract.Contacts.CONTENT_URI, values, ContactsContract.Contacts.DISPLAY_NAME + "= ?", new String[]{contactBean.getName()});
        Toast.makeText(this, "Contact saved as Favourites", Toast.LENGTH_SHORT).show();
    }

}

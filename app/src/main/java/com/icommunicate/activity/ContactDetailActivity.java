package com.icommunicate.activity;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
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
import com.icommunicate.adapterActions.SelectNumberCallbackCallback;
import com.icommunicate.apiCall.ApiConstant;
import com.icommunicate.bean.ContactBean;
import com.icommunicate.bean.ContactDetailBean;
import com.icommunicate.common.CommonMethods;
import com.icommunicate.common.IntentUtils;
import com.icommunicate.common.dailog.SelectContactNumberDailog;
import com.icommunicate.twillio.VoiceActivityDuplicate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
    @BindView(R.id.userPic)
    ImageView profilePic;

    String address = "";

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
            address = getAddress(contactBean.getLookupId());
            getUserPic(contactBean.getLookupId());
            HashMap<String, String> numbaerMap = getPhoneNumber(contactBean.getLookupId());
            HashMap<String, String> emailMap = getEmail(contactBean.getLookupId());

            List<String> contactList = new ArrayList<String>(numbaerMap.keySet());
            contactBean.setNumbers(contactList);


            List<String> emailList = new ArrayList<String>(emailMap.keySet());
            contactBean.setEmail(emailList);


            ArrayList<ContactDetailBean> contactDetailBeanArrayList = new ArrayList<>();
            for (int i = 0; i < contactBean.getNumbers().size(); i++) {
                contactDetailBeanArrayList.add(new ContactDetailBean(contactBean.getNumbers().get(i), numbaerMap.get(contactBean.getNumbers().get(i)), R.drawable.ic_call_black, R.drawable.ic_message_black));
            }

            ArrayList<ContactDetailBean> emailDetailBeanArrayList = new ArrayList<>();
            for (int i = 0; i < contactBean.getEmail().size(); i++) {
                email.setVisibility(View.VISIBLE);
                emailDetailBeanArrayList.add(new ContactDetailBean(contactBean.getEmail().get(i), emailMap.get(contactBean.getEmail().get(i)), R.drawable.ic_email_black, 0));
            }

            ArrayList<ContactDetailBean> companyBeanArrayList = new ArrayList<>();
            companyBeanArrayList.add(new ContactDetailBean("Location", address, R.drawable.ic_company_black, R.drawable.ic_directions_black));

            addressDetailLnr.setVisibility(View.GONE);
            if (address.trim().length() > 0) {
                addressDetailLnr.setVisibility(View.VISIBLE);
                location.setVisibility(View.VISIBLE);
            }

            workPlaceDetailLnr.setVisibility(View.GONE);


            for (int i = 0; i < contactDetailBeanArrayList.size(); i++) {
                contactDetailLnr.addView(dynamicAddLinearLayout(contactDetailBeanArrayList.get(i)));
            }


            for (int i = 0; i < emailDetailBeanArrayList.size(); i++) {
                emailDetailLnr.addView(dynamicAddLinearLayout(emailDetailBeanArrayList.get(i)));
            }


            for (int i = 0; i < companyBeanArrayList.size(); i++) {
                addressDetailLnr.addView(dynamicAddLinearLayout(companyBeanArrayList.get(i)));
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

        if (contactBean.getEmail().size() == 1) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + contactBean.getEmail().get(0)));
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Mail application not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            SelectContactNumberDailog.show("Select Email", contactBean.getEmail(), getSupportFragmentManager(), new SelectNumberCallbackCallback() {
                @Override
                public void onClick(View view, int position) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + contactBean.getEmail().get(position)));
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(ContactDetailActivity.this, "Mail application not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNo() {

                }
            });
        }

    }

    @OnClick(R.id.txtCall)
    public void callClicked() {

        if (contactBean.getNumbers().size() == 1) {

            Intent calling = new Intent(this, VoiceActivityDuplicate.class);
            calling.putExtra("phoneNumber", contactBean.getNumbers().get(0));
            calling.putExtra("Name", contactBean.getName());
            startActivity(calling);
        } else {
            SelectContactNumberDailog.show("Select Number", contactBean.getNumbers(), getSupportFragmentManager(), new SelectNumberCallbackCallback() {
                @Override
                public void onClick(View view, int position) {
                    Intent calling = new Intent(ContactDetailActivity.this, VoiceActivityDuplicate.class);
                    calling.putExtra("phoneNumber", contactBean.getNumbers().get(position));
                    calling.putExtra("Name", contactBean.getName());
                    startActivity(calling);
                }

                @Override
                public void onNo() {

                }
            });
        }
    }


    @OnClick(R.id.txtMessage)
    public void messageClicked() {

        if (contactBean.getNumbers().size() == 1) {
            Intent intent = new Intent(ContactDetailActivity.this, MessageActivity.class);
            intent.putExtra("phoneNumber", contactBean.getNumbers().get(0));
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        } else {
            SelectContactNumberDailog.show("Select Number", contactBean.getNumbers(), getSupportFragmentManager(), new SelectNumberCallbackCallback() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(ContactDetailActivity.this, MessageActivity.class);
                    intent.putExtra("phoneNumber", contactBean.getNumbers().get(position));
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                }

                @Override
                public void onNo() {

                }
            });
        }
    }

    @OnClick(R.id.txtLocation)
    public void locationClicked() {
        String map = "http://maps.google.co.in/maps?q=" + address;

        String uri = String.format(Locale.ENGLISH, map);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    @OnClick(R.id.btn_favorite_contact)
    public void setFavoriteContact() {

        ContentValues values = new ContentValues();
        values.put(ContactsContract.Contacts.STARRED, 1);
        getContentResolver().update(ContactsContract.Contacts.CONTENT_URI, values, ContactsContract.Contacts.DISPLAY_NAME + "= ?", new String[]{contactBean.getName()});
        Toast.makeText(this, "Contact saved as Favourites", Toast.LENGTH_SHORT).show();
    }


    String getAddress(String id) {

        String tempAddress = "";
        Uri URI_ADDRESS = ContactsContract.Data.CONTENT_URI;
        String SELECTION_ADDRESS = ContactsContract.Data.LOOKUP_KEY
                + " = ? AND " + ContactsContract.Data.MIMETYPE
                + " = ?";
        String[] SELECTION_ARRAY_ADDRESS = new String[]{
                id,
                ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};

        Cursor currAddr = this.getContentResolver().query(URI_ADDRESS, null, SELECTION_ADDRESS, SELECTION_ARRAY_ADDRESS, null);
        int indexAddType = currAddr
                .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE);
        int indexStreet = currAddr
                .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET);
        int indexPOBox = currAddr
                .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX);
        int indexNeighbor = currAddr
                .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD);
        int indexCity = currAddr
                .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY);
        int indexRegion = currAddr
                .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION);
        int indexPostCode = currAddr
                .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE);
        int indexCountry = currAddr
                .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY);

        if (currAddr.getCount() > 0) {

            while (currAddr.moveToNext()) {


                if (currAddr.getString(indexStreet) != null) {
                    tempAddress = currAddr.getString(indexStreet);
                }

                if (currAddr.getString(indexPOBox) != null) {
                    tempAddress += " " + currAddr.getString(indexPOBox);
                }

                if (currAddr.getString(indexCity) != null) {
                    tempAddress += " " + currAddr.getString(indexCity);
                }

                if (currAddr.getString(indexRegion) != null) {
                    tempAddress += " " + currAddr.getString(indexRegion);
                }

                if (currAddr.getString(indexCountry) != null) {
                    tempAddress += " " + currAddr.getString(indexCountry);
                }


                Log.d("Address", tempAddress);
            }


        }
        currAddr.close();
        return tempAddress.trim();
    }

    void getUserPic(String id) {
        Uri URI_PHOTO = ContactsContract.Data.CONTENT_URI;
        String SELECTION_PHOTO = ContactsContract.Data.LOOKUP_KEY
                + " = ? AND " + ContactsContract.Data.MIMETYPE
                + " = ?";
        String[] SELECTION_ARRAY_PHOTO = new String[]{
                id,
                ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE};

        Cursor currPhoto = this.getContentResolver().query(URI_PHOTO, null, SELECTION_PHOTO, SELECTION_ARRAY_PHOTO, null);
        int indexPhoto = currPhoto.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO);

        while (currPhoto.moveToNext()) {

            byte[] photoByte = currPhoto.getBlob(indexPhoto);

            if (photoByte != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);

                // Getting Caching directory
                File cacheDirectory = this.getCacheDir();

                // Temporary file to store the contact image
                // File tmpFile = new File(cacheDirectory.getPath()
                // + "/image_"+id+".png");
                File tmpFile = new File(cacheDirectory.getPath() + "/image_.png");

                // The FileOutputStream to the temporary file
                try {
                    FileOutputStream fOutStream = new FileOutputStream(tmpFile);

                    // Writing the bitmap to the temporary file as png file
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOutStream);

                    // Flush the FileOutputStream
                    fOutStream.flush();

                    // Close the FileOutputStream
                    fOutStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                String photoPath = tmpFile.getPath();
                contactBean.setProfile(photoPath);
                profilePic.setImageBitmap(bitmap);
            }
        }
        currPhoto.close();
    }

    HashMap<String, String> getPhoneNumber(String id) {
        // Get Phone Number....(HashMap<Integer, String>phones)
        Uri URI_PHONE = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String SELECTION_PHONE = ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY + " = ?";
        String[] SELECTION_ARRAY_PHONE = new String[]{id};

        Cursor currPhone = this.getContentResolver().query(URI_PHONE, null, SELECTION_PHONE, SELECTION_ARRAY_PHONE, null);
        int indexPhoneNo = currPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int indexPhoneType = currPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);

        HashMap<String, String> phoneMap = new HashMap<String, String>();
        if (currPhone.getCount() > 0) {

            while (currPhone.moveToNext()) {
                String phoneNoStr = currPhone.getString(indexPhoneNo);
                int phoneTypeStr = currPhone.getInt(indexPhoneType);
                String type = "Home";


                switch (phoneTypeStr) {
                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                        type = "Home";
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                        type = "Mobile";
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                        type = "Work";
                        break;
                }


                phoneNoStr = phoneNoStr.replace(" ", "");
                phoneMap.put(phoneNoStr, type);
            }

        }
        currPhone.close();
        return phoneMap;

    }

    HashMap<String, String> getEmail(String id) {
        Uri URI_EMAIL = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String SELECTION_EMAIL = ContactsContract.CommonDataKinds.Email.LOOKUP_KEY + " = ?";
        String[] SELECTION_ARRAY_EMAIL = new String[]{id};

        Cursor emailCur = this.getContentResolver().query(URI_EMAIL, null, SELECTION_EMAIL, SELECTION_ARRAY_EMAIL, null);
        int indexEmail = emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
        int indexEmailType = emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE);
        HashMap<String, String> emailMap = new HashMap<String, String>();
        if (emailCur.getCount() > 0) {
            while (emailCur.moveToNext()) {
                // This would allow you get several email addresses,
                // if the email addresses were stored in an array
                String emailStr = emailCur.getString(indexEmail);
                Integer emailTypeStr = emailCur.getInt(indexEmailType);
                String type = "Other";
                switch (emailTypeStr) {
                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                        type = "Home";
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                        type = "Work";
                        break;
                }


                emailMap.put(emailStr, type);
            }


        }
        emailCur.close();
        return emailMap;
    }
}

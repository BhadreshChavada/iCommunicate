package com.icommunicate.common;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.icommunicate.bean.ContactBean;

import java.util.ArrayList;
import java.util.List;

public class FetchContacts extends AsyncTask<Void, Void, List<ContactBean>> {
    private Context activity;
    private OnContactFetchListener listener;

    public FetchContacts(Context context, OnContactFetchListener listener) {
        activity = context;
        this.listener = listener;
    }

    @Override
    protected List doInBackground(Void... params) {
        String contactId, lookupId;
        List<ContactBean> contactList = new ArrayList<>();
        ContentResolver cr = activity.getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor phoneCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    int dummyId = 0;
                    while (phoneCursor.moveToNext()) {
                        String contactName = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                        String contactNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactNumber = contactNumber.replace(" ", "");
                        contactNumber = contactNumber.replace("-", "");
                        contactNumber = contactNumber.replace("(", "");
                        contactNumber = contactNumber.replace(")", "");
                        dummyId++;

                        contactId = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                        lookupId = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY));


                        Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                        List<String> emailList = new ArrayList<>();
                        while (emailCur.moveToNext()) {
                            emailList.add(emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                        }

                        emailCur.close();

                        contactList.add(new ContactBean(dummyId + "", contactName, contactNumber, "1", emailList, contactId, lookupId));
                        break;
                    }
                    phoneCursor.close();


                }

            } while (cursor.moveToNext());
        }


        SharedPreferences sharedpreferences = activity.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("Contact", new Gson().toJson(contactList));
        editor.commit();


        return contactList;
    }

    @Override
    protected void onPostExecute(List<ContactBean> list) {
        super.onPostExecute(list);
        if (listener != null) {
            if (list != null) {
                listener.onContactFetch(list);
            }
        }
    }

    public interface OnContactFetchListener {
        void onContactFetch(List<ContactBean> list);
    }
}
package com.icommunicate.adapterActions;

import android.view.View;

import com.icommunicate.bean.ContactBean;


public interface ContactListner {

    void contactViewClick(ContactBean bean);

    void haderClick(View v, int position);
}

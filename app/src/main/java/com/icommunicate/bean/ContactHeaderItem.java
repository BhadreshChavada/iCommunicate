package com.icommunicate.bean;

import com.brandongogetap.stickyheaders.exposed.StickyHeader;

public final class ContactHeaderItem extends ContactBean implements StickyHeader {


    public ContactHeaderItem(String senderUid, String senderName, String date) {
        super(senderUid, senderName, date);
    }
}

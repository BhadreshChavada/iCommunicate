package com.icommunicate.bean;

import com.brandongogetap.stickyheaders.exposed.StickyHeader;

public final class SettingHeaderItem extends SettingBean implements StickyHeader {


    public SettingHeaderItem(String senderUid, String senderName, String date) {
        super(senderUid, senderName, date);
    }
}

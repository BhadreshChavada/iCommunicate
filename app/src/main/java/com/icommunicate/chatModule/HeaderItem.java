package com.icommunicate.chatModule;

import com.brandongogetap.stickyheaders.exposed.StickyHeader;
import com.icommunicate.adapter.FcmChat;

public final class HeaderItem extends FcmChat implements StickyHeader {


    public HeaderItem(int type,String to, String from, String senderName, String message, int timestamp) {
        super(type,to, from, senderName, message, timestamp);
    }
}

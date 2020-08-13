package com.icommunicate.adapterActions;

import android.view.View;

import com.icommunicate.bean.MessageItem;

/**
 * Created by Ashish on 1/18/2018.
 */

public interface MessageAdapterListener {

    void selected(MessageItem messageItem, int position);


}
